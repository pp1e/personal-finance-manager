package com.example.personalfinancemanager.components.main

import com.arkivanov.mvikotlin.core.store.Store
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity

internal interface MainStore : Store<MainStore.Intent, MainStore.State, Nothing> {
    sealed class Intent

    data class State(
        val frequentCategories: List<OperationCategoryDbEntity> = emptyList(),
        val balanceRubles: Float = -1f,
        val balanceDollars: Float = -1f
    )
}
