package com.example.personalfinancemanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.personalfinancemanager.components.newOperation.NewOperationComponent
import com.example.personalfinancemanager.database.OperationType
import com.example.personalfinancemanager.ui.constants.UiConstants
import com.example.personalfinancemanager.ui.elements.ComboBox
import com.example.personalfinancemanager.ui.elements.RadioChooser
import com.example.personalfinancemanager.ui.elements.Spinner

@Composable
fun NewOperationScreen(component: NewOperationComponent) {
    val model by component.model.subscribeAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)

    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val elementModifier = Modifier.padding(
                bottom = UiConstants.DefaultPadding
            )

            RadioChooser(
                currentSelection = model.type,
                selections = mapOf(
                    "Refill" to OperationType.REFILL,
                    "Withdrawal" to OperationType.WITHDRAWAL
                ),
                onSelectionChanged = component::onTypeChanged,
                label = "Operation type",
                modifier = elementModifier
            )

            ComboBox(
                value = model.category,
                onValueChanged = component::onCategoryChanged,
                choices = model.existCategories.map { it.name },
                label = "Category",
                modifier = elementModifier
            )

            OutlinedTextField(
                value = model.message,
                onValueChange = component::onMessageChanged,
                label = { Text("Message") },
                placeholder = { Text("Input message (optional)") },
                modifier = elementModifier
            )

            Spinner(
                value = model.amount,
                onValueChanged = component::onAmountChanged,
                label = "Amount",
                modifier = elementModifier
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(UiConstants.OperationButtonHeight)
                .align(Alignment.BottomCenter),
            onClick = component::onAddClicked,
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Text(
                text = "Add operation",
                fontSize = UiConstants.BigButtonFontSize
            )
        }
    }
}
