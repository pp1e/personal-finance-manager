package com.example.personalfinancemanager.components.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.base.Consumer
import com.badoo.reaktive.base.invoke
import com.example.personalfinancemanager.database.AppRepository
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity
import com.example.personalfinancemanager.utils.asValue

class MainComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    database: AppRepository,
    private val output: Consumer<Output>
) : ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            MainStoreProvider(
                storeFactory = storeFactory,
                database = database
            ).provide()
        }

    val model: Value<Model> = store.asValue().map(stateToModel)

    fun onNewOperationClicked(operationId: Long? = null) {
        output(Output.NewOperationTransit(operationId))
    }

    fun onHistoryClicked() {
        output(Output.HistoryTransit)
    }

    fun onChartClicked() {
        output(Output.ChartTransit)
    }

    data class Model(
        val frequentCategories: List<OperationCategoryDbEntity>,
        val balanceRubles: Float,
        val balanceDollars: Float
    )

    sealed class Output {
        data class NewOperationTransit(val categoryId: Long? = null) : Output()
        data object HistoryTransit : Output()
        data object ChartTransit : Output()
    }
}
