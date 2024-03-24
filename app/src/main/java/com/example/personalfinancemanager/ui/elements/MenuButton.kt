package com.example.personalfinancemanager.ui.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import com.example.personalfinancemanager.ui.constants.UiConstants

@Composable
fun MenuButton(
    icon: ImageVector,
    placeholder: String,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    Column(
        Modifier.then(modifier)
            .fillMaxSize()
            .clickable { onClicked() }
    ) {
        Icon(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            imageVector = icon,
            contentDescription = "Menu Item Icon"
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = placeholder,
            fontSize = UiConstants.MenuFontSize,
            textAlign = TextAlign.Center
        )
    }
}
