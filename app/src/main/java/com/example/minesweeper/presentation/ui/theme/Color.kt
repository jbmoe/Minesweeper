package com.example.minesweeper.presentation.ui.theme

import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

@Composable
fun outlinedTextFieldColors(
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    disabledTextColor: Color = textColor.copy(ContentAlpha.disabled),
    backgroundColor: Color = Color.Transparent,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    errorCursorColor: Color = MaterialTheme.colorScheme.error,
    focusedBorderColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = ContentAlpha.high),
    unfocusedBorderColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
    disabledBorderColor: Color = unfocusedBorderColor.copy(alpha = ContentAlpha.disabled),
    errorBorderColor: Color = MaterialTheme.colorScheme.error,
    leadingIconColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
    disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = ContentAlpha.disabled),
    errorLeadingIconColor: Color = leadingIconColor,
    trailingIconColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
    disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = ContentAlpha.disabled),
    errorTrailingIconColor: Color = MaterialTheme.colorScheme.error,
    focusedLabelColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = ContentAlpha.high),
    unfocusedLabelColor: Color = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.medium),
    disabledLabelColor: Color = unfocusedLabelColor.copy(ContentAlpha.disabled),
    errorLabelColor: Color = MaterialTheme.colorScheme.error,
    placeholderColor: Color = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.medium),
    disabledPlaceholderColor: Color = placeholderColor.copy(ContentAlpha.disabled)
): TextFieldColors =
    MaterialThemeOutlinedTextFieldColors(
        textColor = textColor,
        disabledTextColor = disabledTextColor,
        cursorColor = cursorColor,
        errorCursorColor = errorCursorColor,
        focusedIndicatorColor = focusedBorderColor,
        unfocusedIndicatorColor = unfocusedBorderColor,
        errorIndicatorColor = errorBorderColor,
        disabledIndicatorColor = disabledBorderColor,
        leadingIconColor = leadingIconColor,
        disabledLeadingIconColor = disabledLeadingIconColor,
        errorLeadingIconColor = errorLeadingIconColor,
        trailingIconColor = trailingIconColor,
        disabledTrailingIconColor = disabledTrailingIconColor,
        errorTrailingIconColor = errorTrailingIconColor,
        backgroundColor = backgroundColor,
        focusedLabelColor = focusedLabelColor,
        unfocusedLabelColor = unfocusedLabelColor,
        disabledLabelColor = disabledLabelColor,
        errorLabelColor = errorLabelColor,
        placeholderColor = placeholderColor,
        disabledPlaceholderColor = disabledPlaceholderColor
    )

@Composable
fun sliderColors(
    thumbColor: Color = MaterialTheme.colorScheme.primary,
    disabledThumbColor: Color = MaterialTheme.colorScheme.onSurface
        .copy(alpha = ContentAlpha.disabled)
        .compositeOver(MaterialTheme.colorScheme.surface),
    activeTrackColor: Color = MaterialTheme.colorScheme.primary,
    inactiveTrackColor: Color = activeTrackColor.copy(alpha = SliderDefaults.InactiveTrackAlpha),
    disabledActiveTrackColor: Color =
        MaterialTheme.colorScheme.onSurface.copy(alpha = SliderDefaults.DisabledActiveTrackAlpha),
    disabledInactiveTrackColor: Color =
        disabledActiveTrackColor.copy(alpha = SliderDefaults.DisabledInactiveTrackAlpha),
    activeTickColor: Color = contentColorFor(activeTrackColor).copy(alpha = SliderDefaults.TickAlpha),
    inactiveTickColor: Color = activeTrackColor.copy(alpha = SliderDefaults.TickAlpha),
    disabledActiveTickColor: Color = activeTickColor.copy(alpha = SliderDefaults.DisabledTickAlpha),
    disabledInactiveTickColor: Color = disabledInactiveTrackColor
        .copy(alpha = SliderDefaults.DisabledTickAlpha)
): DefaultMaterialThemeSliderColors = DefaultMaterialThemeSliderColors(
    thumbColor = thumbColor,
    disabledThumbColor = disabledThumbColor,
    activeTrackColor = activeTrackColor,
    inactiveTrackColor = inactiveTrackColor,
    disabledActiveTrackColor = disabledActiveTrackColor,
    disabledInactiveTrackColor = disabledInactiveTrackColor,
    activeTickColor = activeTickColor,
    inactiveTickColor = inactiveTickColor,
    disabledActiveTickColor = disabledActiveTickColor,
    disabledInactiveTickColor = disabledInactiveTickColor
)