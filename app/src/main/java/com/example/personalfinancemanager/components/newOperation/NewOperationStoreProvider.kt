package com.example.personalfinancemanager.components.newOperation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.coroutinesinterop.singleFromCoroutine
import com.badoo.reaktive.observable.map
import com.badoo.reaktive.observable.observeOn
import com.badoo.reaktive.scheduler.ioScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.single.observeOn
import com.badoo.reaktive.single.subscribe
import com.example.personalfinancemanager.components.newOperation.NewOperationStore.Intent
import com.example.personalfinancemanager.components.newOperation.NewOperationStore.State
import com.example.personalfinancemanager.database.AppRepository
import com.example.personalfinancemanager.database.OperationType
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity

internal class NewOperationStoreProvider(
    private val storeFactory: StoreFactory,
    private val database: AppRepository,
    private val categoryId: Long?
) {

    fun provide(): NewOperationStore =
        object :
            NewOperationStore,
            Store<Intent, State, Nothing> by storeFactory.create(
                name = "NewOperationStore",
                initialState = State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data class ExistCategoriesLoaded(
            val existCategories: List<OperationCategoryDbEntity>,
            val categoryId: Long?
        ) : Msg()
        data class CategoryChanged(val category: String) : Msg()
        data class AmountChanged(val amount: Float) : Msg()
        data class MessageChanged(val message: String) : Msg()
        data class TypeChanged(val type: OperationType) : Msg()
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeAction(action: Unit, getState: () -> State) {
            database
                .getOperationCategories()
                .observeOn(mainScheduler)
                .map { Msg.ExistCategoriesLoaded(it, categoryId) }
                .subscribeScoped(onNext = ::dispatch)
        }

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                is Intent.ChangeAmount -> dispatch(Msg.AmountChanged(amount = intent.amount))
                is Intent.ChangeCategory -> dispatch(
                    Msg.CategoryChanged(
                        category = intent.category
                    )
                )
                is Intent.ChangeMessage -> dispatch(Msg.MessageChanged(message = intent.message))
                is Intent.ChangeType -> dispatch(Msg.TypeChanged(type = intent.type))
                is Intent.SaveOperation -> saveOperation(getState())
            }

        fun saveOperation(state: State) {
            singleFromCoroutine {
                database.insertFinanceOperation(
                    category = state.category,
                    message = state.message,
                    amount = state.amount,
                    type = state.type
                )
            }
                .observeOn(ioScheduler)
                .subscribe()
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ExistCategoriesLoaded -> copy(
                    existCategories = msg.existCategories,
                    category = msg.categoryId?.let { categoryId ->
                        msg.existCategories.find { it.id == categoryId }?.name
                    } ?: category
                )
                is Msg.CategoryChanged -> copy(category = msg.category)
                is Msg.TypeChanged -> copy(type = msg.type)
                is Msg.AmountChanged -> copy(amount = msg.amount)
                is Msg.MessageChanged -> copy(message = msg.message)
            }
    }
}
