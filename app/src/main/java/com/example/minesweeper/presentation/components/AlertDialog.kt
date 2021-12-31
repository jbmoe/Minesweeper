package com.example.minesweeper.presentation.components

import androidx.annotation.DrawableRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.minesweeper.R

@Composable
fun DialogImpl(
    confirmButtonText: String = "",
    dismissButtonText: String = "",
    heroIconVector: ImageVector? = null,
    @DrawableRes heroIconDrawable: Int? = null,
    title: String,
    content: @Composable () -> Unit,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { /*EMPTY*/ },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    openDialog.value = false
                }) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                    openDialog.value = false
                }) {
                    Text(text = dismissButtonText)
                }
            },
            icon = {
                if (heroIconVector != null) {
                    Icon(heroIconVector, contentDescription = null)
                } else if (heroIconDrawable != null) {
                    Icon(painterResource(id = heroIconDrawable), contentDescription = null)
                }
            },
            title = {
                Text(text = title)
            },
            text = content
        )
    }
}