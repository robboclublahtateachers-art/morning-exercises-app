package com.example.morningexercises.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.morningexercises.data.Exercise
import com.example.morningexercises.ui.screen.screens.ExerciseScreen
import com.example.morningexercises.ui.screen.screens.SettingsScreen
import com.example.morningexercises.ui.screen.screens.WelcomeScreen

@Composable
fun MainScreen() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Welcome) }
    var settings by remember { mutableStateOf(ExerciseSettings()) }
    var exercises by remember { mutableStateOf(defaultExercises) }

    when (currentScreen) {
        Screen.Welcome -> {
            WelcomeScreen(
                onStartClick = { currentScreen = Screen.Exercise },
                onSettingsClick = { currentScreen = Screen.Settings }
            )
        }
        Screen.Settings -> {
            SettingsScreen(
                settings = settings,
                onSettingsChange = { settings = it },
                onBackClick = { currentScreen = Screen.Welcome },
                exercises = exercises,
                onExercisesChange = { exercises = it }
            )
        }
        Screen.Exercise -> {
            ExerciseScreen(
                settings = settings,
                exercises = exercises,
                onBackClick = { currentScreen = Screen.Welcome }
            )
        }
    }
}

enum class Screen {
    Welcome, Settings, Exercise
}

data class ExerciseSettings(
    val numExercises: Int = 3,
    val restDuration: Int = 10,
    val numSets: Int = 3,
    val repsPerSet: Int = 5
)

val defaultExercises = listOf(
    Exercise(
        id = 1,
        name = "Приседания",
        description = "Опусти попу, подними руки",
        emoji = "🦵"
    ),
    Exercise(
        id = 2,
        name = "Прыжки",
        description = "Прыгай высоко!",
        emoji = "🤸"
    ),
    Exercise(
        id = 3,
        name = "Отжимания",
        description = "Согни руки в локтях",
        emoji = "💪"
    ),
    Exercise(
        id = 4,
        name = "Наклоны",
        description = "Тронь пальцами пол",
        emoji = "🧘"
    ),
    Exercise(
        id = 5,
        name = "Бег на месте",
        description = "Беги как быстро можешь",
        emoji = "🏃"
    )
)
