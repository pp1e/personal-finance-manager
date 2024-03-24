package com.example.personalfinancemanager.components.history

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.mainScheduler
import com.example.personalfinancemanager.components.history.HistoryStore.Intent
import com.example.personalfinancemanager.components.history.HistoryStore.State
import com.example.personalfinancemanager.database.AppRepository
import com.example.personalfinancemanager.database.tuples.FinanceOperationTuple

internal class HistoryStoreProvider(
    private val storeFactory: StoreFactory,
    private val database: AppRepository
) {
    fun provide(): HistoryStore =
        object :
            HistoryStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = "HistoryStore",
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data class OperationsLoaded(
            val operations: List<FinanceOperationTuple>
        ) : Msg()
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            database
                .getOperationsData()
                .observeOn(mainScheduler)
                .map(Msg::OperationsLoaded)
                .subscribeScoped(onNext = ::dispatch)
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.OperationsLoaded -> copy(operations = msg.operations)
            }
    }
}
