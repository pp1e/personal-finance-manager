package com.example.personalfinancemanager.ui.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.personalfinancemanager.ui.constants.UiConstants

@Composable
fun ColumnScope.BottomMenu(
    modifier: Modifier = Modifier,
    onNewOperationClicked: () -> Unit,
    onChartClicked: () -> Unit,
    onHistoryClicked: () -> Unit
) {
    Column(
        Modifier.then(modifier)
            .padding(UiConstants.DefaultPadding)
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
            .height(UiConstants.MenuHeight)
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(UiConstants.DividerSize),
            color = MaterialTheme.colorScheme.secondary
        )

        Row {
            val buttonModifier = Modifier
                .weight(1f)

            MenuButton(
                icon = Icons.Default.PostAdd,
                placeholder = "New operation",
                modifier = buttonModifier,
                onClicked = onNewOperationClicked
            )

            MenuButton(
                icon = Icons.Default.History,
                placeholder = "History",
                modifier = buttonModifier,
                onClicked = onHistoryClicked
            )

            MenuButton(
                icon = Icons.AutoMirrored.Filled.ShowChart,
                placeholder = "Chart",
                modifier = buttonModifier,
                onClicked = onChartClicked
            )
        }
    }
}
