package com.example.personalfinancemanager.components.history

import com.arkivanov.mvikotlin.core.store.Store
import com.example.personalfinancemanager.database.tuples.FinanceOperationTuple

internal interface HistoryStore : Store<HistoryStore.Intent, HistoryStore.State, Nothing> {
    sealed class Intent

    data class State(
        val operations: List<FinanceOperationTuple> = emptyList()
    )
}
