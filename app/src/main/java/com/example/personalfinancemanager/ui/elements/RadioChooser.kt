package com.example.personalfinancemanager.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.personalfinancemanager.ui.constants.UiConstants

@Composable
fun <T : Enum<T>> RadioChooser(
    currentSelection: T,
    selections: Map<String, T>,
    onSelectionChanged: (T) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    enabled: Boolean = true
) {
    Row(
        Modifier.then(modifier)
            .selectableGroup()
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        VerticalDivider(
            Modifier
                .padding(
                    start = UiConstants.DefaultPadding,
                    end = UiConstants.DefaultPadding
                )
                .height(UiConstants.RadioDividerHeight)
        )

        Column {
            for ((methodName, method) in selections) {
                Row {
                    RadioButton(
                        selected = (currentSelection == method),
                        onClick = { onSelectionChanged(method) },
                        enabled = enabled
                    )
                    Text(
                        text = methodName,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
    }
}
