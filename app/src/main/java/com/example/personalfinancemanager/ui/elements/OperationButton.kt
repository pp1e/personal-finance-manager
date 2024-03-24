package com.example.personalfinancemanager.ui.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.personalfinancemanager.database.entities.OperationCategoryDbEntity
import com.example.personalfinancemanager.ui.constants.UiConstants

@Composable
fun CustomCategoryButton(
    category: OperationCategoryDbEntity,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = Modifier.then(modifier)
            .fillMaxWidth()
            .height(UiConstants.OperationButtonHeight),
        onClick = { onClick(category.id) }
    ) {
        Text(
            text = category.name,
            textAlign = TextAlign.Center,
            fontSize = UiConstants.OperationButtonFontSize
        )
    }
}
