package com.example.personalfinancemanager.components.chart

internal val stateToModel: (ChartStore.State) -> ChartComponent.Model =
    { state ->
        ChartComponent.Model(
            balanceHistory = state.balanceHistory
        )
    }
