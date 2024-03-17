package com.example.personalfinancemanager.components.main

internal val stateToModel: (MainStore.State) -> MainComponent.Model =
    { state ->
        MainComponent.Model(
            frequentCategories = state.frequentCategories,
            balanceDollars = state.balanceDollars,
            balanceRubles = state.balanceRubles
        )
    }
