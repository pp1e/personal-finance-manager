package com.example.personalfinancemanager.routing

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.example.personalfinancemanager.ui.screens.HistoryScreen
import com.example.personalfinancemanager.ui.screens.MainScreen
import com.example.personalfinancemanager.ui.screens.NewOperationScreen

@Composable
fun RootContent(router: RootRouter) {
    Children(
        stack = router.childStack,
        animation = stackAnimation(animator = slide())
    ) {
        when (val child = it.instance) {
            is RootRouter.Child.Main -> MainScreen(child.component)
            is RootRouter.Child.NewOperation -> NewOperationScreen(child.component)
            is RootRouter.Child.History -> HistoryScreen(child.component)
        }
    }
}
