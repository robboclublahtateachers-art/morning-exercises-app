package com.example.morningexercises.ui.screen.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.example.morningexercises.ui.screen.ExerciseSettings
import kotlinx.coroutines.delay

@Composable
fun ExerciseScreen(
    settings: ExerciseSettings,
    exercises: List<Exercise>,
    onBackClick: () -> Unit
) {
    var currentExerciseIndex by remember { mutableStateOf(0) }
    var currentSet by remember { mutableStateOf(1) }
    var currentRep by remember { mutableStateOf(1) }
    var isResting by remember { mutableStateOf(false) }
    var timeRemaining by remember { mutableStateOf(0) }
    var workoutComplete by remember { mutableStateOf(false) }

    val exercisesToShow = exercises.take(settings.numExercises)
    val currentExercise = if (currentExerciseIndex < exercisesToShow.size) {
        exercisesToShow[currentExerciseIndex]
    } else {
        null
    }

    // Timer logic
    LaunchedEffect(timeRemaining, isResting) {
        if (isResting && timeRemaining > 0) {
            delay(1000)
            val newTime = timeRemaining - 1
            if (newTime == 0) {
                isResting = false
                currentRep += 1
                if (currentRep > settings.repsPerSet) {
                    currentRep = 1
                    currentSet += 1
                    if (currentSet > settings.numSets) {
                        currentSet = 1
                        currentExerciseIndex += 1
                        if (currentExerciseIndex >= exercisesToShow.size) {
                            workoutComplete = true
                        }
                    } else {
                        isResting = true
                        timeRemaining.coerceAtLeast(settings.restDuration)
                    }
                }
            } else {
                timeRemaining = newTime
            }
        }
    }

    if (workoutComplete) {
        CompletionScreen(onBackClick)
    } else if (currentExercise != null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    if (isResting) Color(0xFFFF6B9D) else Color(0xFF4ECDC4)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Progress
                Text(
                    text = "Упражнение ${currentExerciseIndex + 1}/${exercisesToShow.size}",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                // Exercise name
                Text(
                    text = currentExercise.name,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                // Large emoji
                Text(
                    text = currentExercise.emoji,
                    fontSize = 120.sp,
                    modifier = Modifier.padding(vertical = 32.dp)
                )

                // Description
                Text(
                    text = currentExercise.description,
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Status
                if (isResting) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "☕ Отдыхаем",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = timeRemaining.toString() + " сек",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Подход ${currentSet}/${settings.numSets}",
                            fontSize = 24.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Повтор ${currentRep}/${settings.repsPerSet}",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Next button
                Button(
                    onClick = {
                        if (isResting) {
                            isResting = false
                            timeRemaining = 0
                        } else {
                            isResting = true
                            timeRemaining = settings.restDuration
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = if (isResting) "Начать упражнение!" else "Отдыхать →",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isResting) Color(0xFFFF6B9D) else Color(0xFF4ECDC4)
                    )
                }

                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE53935)
                    )
                ) {
                    Text(
                        text = "⏹ Остановить",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun CompletionScreen(onBackClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "🎉",
                fontSize = 120.sp
            )

            Text(
                text = "Отлично!",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = "Тренировка завершена!",
                fontSize = 24.sp,
                color = Color.White
            )

            Text(
                text = "Ты молодец! 💪",
                fontSize = 20.sp,
                color = Color.White
            )

            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                )
            ) {
                Text(
                    text = "Главное меню",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}
