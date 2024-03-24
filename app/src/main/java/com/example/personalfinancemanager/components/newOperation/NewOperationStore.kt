package com.example.personalfinancemanager.components.newOperation

import com.arkivanov.mvikotlin.core.store.Store
import com.example.personalfinancemanager.components.newOperation.NewOperationStore.Intent
import com.example.personalfinancemanager.components.newOperation.NewOperationStore.State
import com.example.personalfinancemanager.database.OperationType
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity

internal interface NewOperationStore : Store<Intent, State, Nothing> {
    sealed class Intent {
        data class ChangeCategory(val category: String) : Intent()
        data class ChangeAmount(val amount: Float) : Intent()
        data class ChangeMessage(val message: String) : Intent()
        data class ChangeType(val type: OperationType) : Intent()
        data object SaveOperation : Intent()
    }

    data class State(
        val existCategories: List<OperationCategoryDbEntity> = emptyList(),
        val category: String = "",
        val amount: Float = 0f,
        val message: String = "",
        val type: OperationType = OperationType.WITHDRAWAL
    )
}
