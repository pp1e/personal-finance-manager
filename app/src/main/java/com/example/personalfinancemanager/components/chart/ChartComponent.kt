package com.example.personalfinancemanager.components.chart

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.base.Consumer
import com.example.personalfinancemanager.database.AppRepository
import com.example.personalfinancemanager.database.entities.BalanceDbEntity
import com.example.personalfinancemanager.utils.asValue

class ChartComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    database: AppRepository,
    private val output: Consumer<Output>
) : ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            ChartStoreProvider(
                storeFactory = storeFactory,
                database = database
            ).provide()
        }

    val model: Value<Model> = store.asValue().map(stateToModel)

    data class Model(
        val balanceHistory: List<BalanceDbEntity>
    )

    sealed class Output {
        data object MainTransit : Output()
    }
}
