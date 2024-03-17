package com.example.personalfinancemanager.routing

import MainScreen
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation

@Composable
fun RootContent(router: RootRouter) {
    Children(
        stack = router.childStack,
        animation = stackAnimation(animator = slide())
    ) {
        when (val child = it.instance) {
            is RootRouter.Child.Main -> MainScreen(child.component)
        }
    }
}
