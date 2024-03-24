package com.example.personalfinancemanager.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.personalfinancemanager.components.history.HistoryComponent
import com.example.personalfinancemanager.ui.constants.UiConstants
import com.example.personalfinancemanager.ui.elements.HistoryItem

@Composable
fun HistoryScreen(component: HistoryComponent) {
    val model by component.model.subscribeAsState()

    LazyColumn {
        val modifier = Modifier.padding(
            start = UiConstants.DefaultPadding,
            end = UiConstants.DefaultPadding,
            top = UiConstants.DefaultPadding
        )
        items(model.operations) { operation ->
            HistoryItem(
                operation = operation,
                modifier = modifier
            )
        }
    }
}
