package com.example.minesweeper.presentation.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.VisualTransformation
import com.example.minesweeper.presentation.ui.theme.outlinedTextFieldColors

@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun OutlinedTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    isError: Boolean = false,
    errorText: String = "",
    labelText: String = "",
    placeholderText: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    val onBackground = MaterialTheme.colorScheme.onBackground
    val keyboardController = LocalSoftwareKeyboardController.current

    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            isError = isError,
            visualTransformation = visualTransformation,
            textStyle = TextStyle(onBackground),
            label = textOrNull(labelText),
            singleLine = singleLine,
            maxLines = maxLines,
            readOnly = readOnly,
            placeholder = textOrNull(placeholderText),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIconOrError(trailingIcon = trailingIcon, isError = isError),
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
            }),
            colors = outlinedTextFieldColors()
        )
        if (isError and errorText.isNotEmpty()) {
            Text(text = errorText, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
private fun textOrNull(text: String): @Composable (() -> Unit)? {
    return if (text.isNotBlank()) {
        { Text(text = text) }
    } else {
        null
    }
}

@Composable
fun trailingIconOrError(
    trailingIcon: @Composable (() -> Unit)?,
    isError: Boolean
): @Composable (() -> Unit)? {
    return if (isError) {
        { Icon(Icons.Default.Warning, "error", tint = MaterialTheme.colorScheme.error) }
    } else {
        trailingIcon
    }
}
