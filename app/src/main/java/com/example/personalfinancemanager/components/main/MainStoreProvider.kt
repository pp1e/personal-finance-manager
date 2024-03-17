package com.example.personalfinancemanager.components.main

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler
import com.example.personalfinancemanager.components.main.MainStore.Intent
import com.example.personalfinancemanager.components.main.MainStore.State
import com.example.personalfinancemanager.database.AppRepository
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity

internal class MainStoreProvider(
    private val storeFactory: StoreFactory,
    private val database: AppRepository
) {

    fun provide(): MainStore =
        object :
            MainStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = "MainStore",
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data class FrequentCategoriesLoaded(
            val frequentCategories: List<OperationCategoryDbEntity>
        ) : Msg()
        data class BalanceLoaded(val balance: Float) : Msg()
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            database
                .getOperationCategories(10)
                .observeOn(mainScheduler)
                .map(Msg::FrequentCategoriesLoaded)
                .subscribeScoped(onNext = ::dispatch)

            database
                .getActualBalance()
                .observeOn(mainScheduler)
                .map { Msg.BalanceLoaded(it.amount) }
                .subscribeScoped(onNext = ::dispatch)
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.FrequentCategoriesLoaded -> copy(
                    frequentCategories = msg.frequentCategories
                )
                is Msg.BalanceLoaded -> copy(
                    balanceRubles = msg.balance,
                    balanceDollars = -1f
                )
            }
    }
}
