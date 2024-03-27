package com.example.personalfinancemanager.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import com.example.personalfinancemanager.ui.constants.UiConstants

@Composable
fun Spinner(
    value: Float,
    onValueChanged: (Float) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    label: String,
    placeholder: String = "",
    maxValue: Float = Float.MAX_VALUE,
    minValue: Float = -Float.MAX_VALUE,
    enabled: Boolean = true
) {
    var fieldValue by remember { mutableStateOf("") }
    val onFieldValueChanged = { newValue: String -> fieldValue = newValue }

    Row(
        modifier = Modifier.then(modifier)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .height(UiConstants.SpinnerHeight)
                .width(UiConstants.SpinnerWidth)
                .onFocusChanged { focus ->
                    if (!focus.isFocused) {
                        changeValue(
                            currentValue = value,
                            fieldValue = fieldValue,
                            onValueChanged = onValueChanged,
                            onFieldValueChanged = onFieldValueChanged,
                            maxValue = maxValue,
                            minValue = minValue,
                        )
                    }
                },
            value = fieldValue,
            onValueChange = onFieldValueChanged,
            textStyle = TextStyle(fontSize = UiConstants.SpinnerFontSize),
            singleLine = true,
            isError = isError,
            enabled = enabled,
            label = {
                Text(text = label)
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                changeValue(
                    currentValue = value,
                    fieldValue = fieldValue,
                    onValueChanged = onValueChanged,
                    onFieldValueChanged = onFieldValueChanged,
                    maxValue = maxValue,
                    minValue = minValue,
                )
            }),
            placeholder = { Text(text = placeholder) },
        )
        Column {
            Button(
                modifier = Modifier
                    .height(UiConstants.SpinnerHeight / 2)
                    .width(UiConstants.SpinnerWidth / 2)
                    .focusProperties { canFocus = false },
                onClick = { increaseValue(value, onValueChanged, onFieldValueChanged, maxValue) },
                shape = MaterialTheme.shapes.extraSmall,
                enabled = enabled
            ) {
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    contentDescription = "IncreaseIcon"
                )
            }
            Button(
                modifier = Modifier
                    .height(UiConstants.SpinnerHeight / 2)
                    .width(UiConstants.SpinnerWidth / 2)
                    .focusProperties { canFocus = false },
                onClick = { decreaseValue(value, onValueChanged, onFieldValueChanged, minValue) },
                shape = MaterialTheme.shapes.small,
                enabled = enabled
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "Decrease Icon"
                )
            }
        }
    }
}

fun increaseValue(
    currentValue: Float,
    onValueChanged: (Float) -> Unit,
    onFieldValueChanged: (String) -> Unit,
    maxValue: Float
) {
    if (currentValue < maxValue) {
        val newValue = currentValue + 1
        onValueChanged(newValue)
        onFieldValueChanged(newValue.toString())
    }
}

fun decreaseValue(
    currentValue: Float,
    onValueChanged: (Float) -> Unit,
    onFieldValueChanged: (String) -> Unit,
    minValue: Float
) {
    if (currentValue > minValue) {
        val newValue = currentValue - 1
        onValueChanged(newValue)
        onFieldValueChanged(newValue.toString())
    }
}

fun changeValue(
    currentValue: Float,
    fieldValue: String,
    onValueChanged: (Float) -> Unit,
    onFieldValueChanged: (String) -> Unit,
    maxValue: Float,
    minValue: Float,
) {
    val newValue = fieldValue.toFloatOrNull()
    if ((newValue == null) || ((newValue > maxValue) or (newValue < minValue))) {
        onFieldValueChanged(currentValue.toString())
    } else {
        onValueChanged(newValue)
        onFieldValueChanged(newValue.toString())
    }
}
