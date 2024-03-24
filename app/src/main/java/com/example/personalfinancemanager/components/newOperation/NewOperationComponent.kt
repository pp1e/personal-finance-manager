package com.example.personalfinancemanager.components.newOperation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.base.Consumer
import com.badoo.reaktive.base.invoke
import com.example.personalfinancemanager.components.newOperation.NewOperationStore.Intent
import com.example.personalfinancemanager.database.AppRepository
import com.example.personalfinancemanager.database.OperationType
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity
import com.example.personalfinancemanager.utils.asValue

class NewOperationComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    database: AppRepository,
    categoryId: Long?,
    private val output: Consumer<Output>
) : ComponentContext by componentContext {

    private val store =
        instanceKeeper.getStore {
            NewOperationStoreProvider(
                storeFactory = storeFactory,
                database = database,
                categoryId = categoryId
            ).provide()
        }

    val model: Value<Model> = store.asValue().map(stateToModel)

    fun onAddClicked() {
        store.accept(Intent.SaveOperation)
        output(Output.MainTransit)
    }

    fun onAmountChanged(amount: Float) {
        store.accept(Intent.ChangeAmount(amount))
    }

    fun onMessageChanged(message: String) {
        store.accept(Intent.ChangeMessage(message))
    }

    fun onCategoryChanged(category: String) {
        store.accept(Intent.ChangeCategory(category))
    }

    fun onTypeChanged(type: OperationType) {
        store.accept(Intent.ChangeType(type))
    }

    data class Model(
        val existCategories: List<OperationCategoryDbEntity>,
        val message: String,
        val amount: Float,
        val category: String,
        val type: OperationType
    )

    sealed class Output {
        data object MainTransit : Output()
    }
}
