package com.example.personalfinancemanager.routing

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.base.Consumer
import com.example.personalfinancemanager.components.history.HistoryComponent
import com.example.personalfinancemanager.components.main.MainComponent
import com.example.personalfinancemanager.components.newOperation.NewOperationComponent
import com.example.personalfinancemanager.database.AppRepository
import com.example.personalfinancemanager.utils.Consumer
import kotlinx.serialization.Serializable

class RootRouter(
    private val componentContext: ComponentContext,
    private val mainComponent: (
        ComponentContext,
        Consumer<MainComponent.Output>
    ) -> MainComponent,
    private val newOperationComponent: (
        ComponentContext,
        Consumer<NewOperationComponent.Output>,
        Long?
    ) -> NewOperationComponent,
    private val historyComponent: (
        ComponentContext,
        Consumer<HistoryComponent.Output>
    ) -> HistoryComponent
) : ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
        database: AppRepository
    ) : this(
        componentContext = componentContext,
        mainComponent = { context, output ->
            MainComponent(
                componentContext = context,
                storeFactory = storeFactory,
                output = output,
                database = database
            )
        },
        newOperationComponent = { context, output, categoryId ->
            NewOperationComponent(
                componentContext = context,
                storeFactory = storeFactory,
                output = output,
                database = database,
                categoryId = categoryId
            )
        },
        historyComponent = { context, output ->
            HistoryComponent(
                componentContext = context,
                storeFactory = storeFactory,
                output = output,
                database = database
            )
        }
    )

    private val router = StackNavigation<ScreenConfig>()

    private val stack =
        childStack(
            source = router,
            initialConfiguration = ScreenConfig.Main,
            handleBackButton = true,
            childFactory = ::createChild,
            serializer = ScreenConfig.serializer()
        )

    val childStack: Value<ChildStack<*, Child>> = stack

    private fun createChild(
        screenConfig: ScreenConfig,
        componentContext: ComponentContext
    ): Child =
        when (screenConfig) {
            is ScreenConfig.Main -> Child.Main(
                mainComponent(
                    componentContext,
                    Consumer(::onMainOutput)
                )
            )
            is ScreenConfig.NewOperation -> Child.NewOperation(
                newOperationComponent(
                    componentContext,
                    Consumer(::onNewOperationOutput),
                    screenConfig.categoryId
                )
            )
            is ScreenConfig.History -> Child.History(
                historyComponent(
                    componentContext,
                    Consumer(::onHistoryOutput)
                )
            )
        }

    private fun onMainOutput(output: MainComponent.Output): Unit =
        when (output) {
            is MainComponent.Output.NewOperationTransit -> router.push(
                ScreenConfig.NewOperation(output.categoryId)
            )
            is MainComponent.Output.HistoryTransit -> router.push(ScreenConfig.History)
            is MainComponent.Output.ChartTransit -> router.push(ScreenConfig.Main)
        }

    private fun onNewOperationOutput(output: NewOperationComponent.Output): Unit =
        when (output) {
            NewOperationComponent.Output.MainTransit -> router.pop()
        }

    private fun onHistoryOutput(output: HistoryComponent.Output): Unit =
        when (output) {
            HistoryComponent.Output.MainTransit -> router.pop()
        }

    sealed class Child {
        data class Main(val component: MainComponent) : Child()
        data class NewOperation(val component: NewOperationComponent) : Child()
        data class History(val component: HistoryComponent) : Child()
    }

    @Serializable
    private sealed class ScreenConfig {
        @Serializable
        data object Main : ScreenConfig()

        @Serializable
        data class NewOperation(val categoryId: Long?) : ScreenConfig()

        @Serializable
        data object History : ScreenConfig()
    }
}
