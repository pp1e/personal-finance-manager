package com.example.personalfinancemanager.components.newOperation

internal val stateToModel: (NewOperationStore.State) -> NewOperationComponent.Model =
    { state ->
        NewOperationComponent.Model(
            existCategories = state.existCategories,
            amount = state.amount,
            message = state.message,
            category = state.category,
            type = state.type
        )
    }
