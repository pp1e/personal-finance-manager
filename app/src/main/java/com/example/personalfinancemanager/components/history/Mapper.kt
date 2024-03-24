package com.example.personalfinancemanager.components.history

internal val stateToModel: (HistoryStore.State) -> HistoryComponent.Model =
    { state ->
        HistoryComponent.Model(
            operations = state.operations
        )
    }
