package com.example.personalfinancemanager.routing

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.base.Consumer
import com.example.personalfinancemanager.components.main.MainComponent
import com.example.personalfinancemanager.database.AppRepository
import com.example.personalfinancemanager.utils.Consumer
import kotlinx.serialization.Serializable

class RootRouter(
    private val componentContext: ComponentContext,
    private val mainComponent: (
        ComponentContext,
        Consumer<MainComponent.Output>
    ) -> MainComponent
) : ComponentContext by componentContext {

    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory,
        database: AppRepository
    ) : this(
        componentContext = componentContext,
        mainComponent = { component, output ->
            MainComponent(
                componentContext = component,
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
        }

    private fun onMainOutput(output: MainComponent.Output): Unit =
        when (output) {
            is MainComponent.Output.NewOperationTransit -> router.push(ScreenConfig.Main)
            is MainComponent.Output.HistoryTransit -> router.push(ScreenConfig.Main)
            is MainComponent.Output.ChartTransit -> router.push(ScreenConfig.Main)
        }

    sealed class Child {
        data class Main(val component: MainComponent) : Child()
    }

    @Serializable
    private sealed class ScreenConfig {
        @Serializable
        data object Main : ScreenConfig()
    }
}
