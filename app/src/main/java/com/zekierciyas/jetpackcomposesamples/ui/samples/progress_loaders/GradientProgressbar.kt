package com.zekierciyas.jetpackcomposesamples.ui.samples.progress_loaders

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
@see <a href="https://gist.github.com/fvilarino/a370525b02af592554d6b033a5fdfaf5#file-progress_indicator_final-kt">link</a> */
@Composable
fun GradientProgressbar(
    viewModel: MyViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    indicatorHeight: Dp = 24.dp,
    backgroundIndicatorColor: Color = Color.LightGray.copy(alpha = 0.3f),
    indicatorPadding: Dp = 24.dp,
    gradientColors: List<Color> = listOf(
        Color(0xFF271716),
        Color(0xFFE76140),
        Color(0xFFA0671D),
        Color(0xFFFFB500)
    ),
    numberStyle: TextStyle = TextStyle(
        fontSize = 32.sp
    ),
    animationDuration: Int = 1000,
    animationDelay: Int = 0
) {
    val downloadedPercentage by viewModel.downloadedPercentage.collectAsState(initial = 0F)

    val animateNumber = animateFloatAsState(
        targetValue = downloadedPercentage,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    LaunchedEffect(Unit) {
        viewModel.startThreadGradient()
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(indicatorHeight)
            .padding(start = indicatorPadding, end = indicatorPadding)
    ) {

        // Background indicator
        drawLine(
            color = backgroundIndicatorColor,
            cap = StrokeCap.Round,
            strokeWidth = size.height,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = 0f)
        )

        // Convert the downloaded percentage into progress (width of foreground indicator)
        val progress =
            (animateNumber.value / 100) * size.width // size.width returns the width of the canvas

        // Foreground indicator
        drawLine(
            brush = Brush.linearGradient(
                colors = gradientColors
            ),
            cap = StrokeCap.Round,
            strokeWidth = size.height,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = progress, y = 0f)
        )

    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center),
        text = downloadedPercentage.toInt().toString() + "%",
        style = numberStyle
    )

}

class MyViewModel : ViewModel() {

    var downloadedPercentage = MutableStateFlow<Float>(0F)

    fun startThreadGradient() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {

                val totalDownloadSize = 1024f
                var downloadedSize = 0f

                while (true) {

                    downloadedSize += ((1..100).random().toFloat())

                    if (downloadedSize < totalDownloadSize) {
                        withContext(Dispatchers.Main) {
                            downloadedPercentage.value =
                                ((downloadedSize / totalDownloadSize) * 100)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            downloadedPercentage.value = 100f
                        }
                        break
                    }

                    delay(1000)
                }

            }
        }
    }
}