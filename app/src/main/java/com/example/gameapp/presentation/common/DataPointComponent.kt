package com.example.gameapp.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.gameapp.ui.theme.GameAction
import com.example.gameapp.ui.theme.GameTextPrimary

data class DataPoint(
    val title: String,
    val description: String
)

@Composable
fun DataPointComponent(dataPoint: DataPoint) {
    Column {
        Text(
            text = dataPoint.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = GameAction
        )
        Text(
            text = dataPoint.description,
            fontSize = 18.sp,
            color = GameTextPrimary
        )
    }
}