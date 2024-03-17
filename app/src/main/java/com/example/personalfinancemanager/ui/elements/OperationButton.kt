package com.example.personalfinancemanager.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.personalfinancemanager.ui.constants.UiConstants

@Composable
fun CustomOperationButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = Modifier.then(modifier)
            .fillMaxWidth()
            .height(UiConstants.OperationButtonHeight),
        onClick = onClick
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = UiConstants.OperationButtonFontSize
        )
    }
}
