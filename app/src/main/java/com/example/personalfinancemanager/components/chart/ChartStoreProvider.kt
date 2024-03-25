package com.example.personalfinancemanager.components.chart

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler
import com.example.personalfinancemanager.components.chart.ChartStore.Intent
import com.example.personalfinancemanager.components.chart.ChartStore.State
import com.example.personalfinancemanager.database.AppRepository
import com.example.personalfinancemanager.database.entities.BalanceDbEntity

internal class ChartStoreProvider(
    private val storeFactory: StoreFactory,
    private val database: AppRepository
) {
    fun provide(): ChartStore =
        object :
            ChartStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = "ChartStore",
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data class BalanceHistoryLoaded(
            val operations: List<BalanceDbEntity>
        ) : Msg()
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            database
                .getBalanceHistory()
                .observeOn(mainScheduler)
                .map(Msg::BalanceHistoryLoaded)
                .subscribeScoped(onNext = ::dispatch)
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.BalanceHistoryLoaded -> copy(balanceHistory = msg.operations)
            }
    }
}
