package com.example.morningexercises.ui.screen.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.morningexercises.data.Exercise
import com.example.morningexercises.ui.screen.ExerciseSettings

@Composable
fun SettingsScreen(
    settings: ExerciseSettings,
    onSettingsChange: (ExerciseSettings) -> Unit,
    onBackClick: () -> Unit,
    exercises: List<Exercise>,
    onExercisesChange: (List<Exercise>) -> Unit
) {
    var numExercises by remember { mutableStateOf(settings.numExercises.toFloat()) }
    var restDuration by remember { mutableStateOf(settings.restDuration.toFloat()) }
    var numSets by remember { mutableStateOf(settings.numSets.toFloat()) }
    var repsPerSet by remember { mutableStateOf(settings.repsPerSet.toFloat()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFC107))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "⚙️ Настройки тренировки",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Количество упражнений
            SettingSlider(
                label = "Количество упражнений",
                value = numExercises,
                onValueChange = { numExercises = it },
                range = 1f..5f,
                displayValue = numExercises.toInt().toString()
            )

            // Повторений в подходе
            SettingSlider(
                label = "Повторений в подходе",
                value = repsPerSet,
                onValueChange = { repsPerSet = it },
                range = 1f..20f,
                displayValue = repsPerSet.toInt().toString()
            )

            // Количество подходов
            SettingSlider(
                label = "Количество подходов",
                value = numSets,
                onValueChange = { numSets = it },
                range = 1f..5f,
                displayValue = numSets.toInt().toString()
            )

            // Время отдыха
            SettingSlider(
                label = "Время отдыха между подходами (сек)",
                value = restDuration,
                onValueChange = { restDuration = it },
                range = 5f..60f,
                displayValue = restDuration.toInt().toString()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onSettingsChange(
                        ExerciseSettings(
                            numExercises = numExercises.toInt(),
                            restDuration = restDuration.toInt(),
                            numSets = numSets.toInt(),
                            repsPerSet = repsPerSet.toInt()
                        )
                    )
                    onBackClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50)
                )
            ) {
                Text(
                    text = "✓ Сохранить",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935)
                )
            ) {
                Text(
                    text = "← Назад",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SettingSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    range: ClosedFloatingPointRange<Float>,
    displayValue: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = displayValue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = range,
            steps = range.endInclusive.toInt() - range.start.toInt() - 1
        )
    }
}
