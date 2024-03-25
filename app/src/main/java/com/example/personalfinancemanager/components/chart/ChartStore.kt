package com.example.personalfinancemanager.components.chart

import com.arkivanov.mvikotlin.core.store.Store
import com.example.personalfinancemanager.database.entities.BalanceDbEntity

internal interface ChartStore : Store<ChartStore.Intent, ChartStore.State, Nothing> {
    sealed class Intent

    data class State(
        val balanceHistory: List<BalanceDbEntity> = emptyList()
    )
}
