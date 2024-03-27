package com.example.personalfinancemanager.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.MoneyOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.personalfinancemanager.database.OperationType
import com.example.personalfinancemanager.database.tuples.FinanceOperationTuple
import com.example.personalfinancemanager.ui.constants.UiConstants
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HistoryItem(
    operation: FinanceOperationTuple,
    modifier: Modifier = Modifier
) {
    var openDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(UiConstants.HistoryItemHeight)
            .clickable {
                openDialog = true
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically),
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    modifier = Modifier
                        .size(UiConstants.HistoryItemHeight * 0.8f)
                        .align(Alignment.CenterVertically),
                    imageVector = when (
                        OperationType.byId(operation.operationTypeId)
                    ) {
                        OperationType.WITHDRAWAL -> Icons.Outlined.MoneyOff
                        OperationType.REFILL -> Icons.Outlined.AttachMoney
                    },
                    contentDescription = "Operation Type Icon"
                )

                VerticalDivider(
                    thickness = UiConstants.HistoryDividerSize,
                    color = MaterialTheme.colorScheme.background
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(
                            start = UiConstants.DefaultPadding / 2
                        ),
                    text = "${determineSing(operation)}${operation.amount}₽",
                    fontWeight = FontWeight.Bold,
                    fontSize = UiConstants.HistoryAmountFontSize
                )
            }

            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = UiConstants.DefaultPadding)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = operation.operationCategory,
                    fontWeight = FontWeight.Bold,
                    fontSize = UiConstants.HistoryCategoryFontSize,
                    textAlign = TextAlign.End
                )

                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = LocalDateTime.parse(operation.datetime).format(
                        DateTimeFormatter.ofPattern("d MMM uuuu, HH:mm")
                    )
                )
            }
        }
    }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                openDialog = false
            },
            title = {
                Text(text = "Message")
            },
            text = {
                Text(operation.message)
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}

fun determineSing(operation: FinanceOperationTuple) =
    when (
        OperationType.byId(operation.operationTypeId)
    ) {
        OperationType.WITHDRAWAL -> "-"
        OperationType.REFILL -> "+"
    }
