package com.example.personalfinancemanager.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.personalfinancemanager.ui.constants.UiConstants

@Composable
fun AccountStatus(moneyAmount: Float, currency: String, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .then(modifier)
            .height(UiConstants.AccountHeight)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.extraLarge
            )

    ) {
        Text(
            text = "$moneyAmount $currency",
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            fontSize = UiConstants.AccountFontSize,
            fontWeight = FontWeight.Bold
        )
    }
}
