package com.zekierciyas.jetpackcomposesamples.ui.samples.progress_loaders

import androidx.compose.animation.core.*
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ProgressLoaders() {
    Column(
        modifier = Modifier
            .verticalScroll(
                enabled = true,
                state = ScrollState(0)
            )
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        DottedProgressbar()
        VerticalDivider()

        CircularIndeterminateProgressBar()
        VerticalDivider()

        LinearIndeterminateProgressBar()
        VerticalDivider()

        CustomLinearProgressBar()
        VerticalDivider()

        CustomCircularProgressBar()
        VerticalDivider()

        CustomCircularProgressBar2()
        VerticalDivider()

        GradientProgressbar()
        VerticalDivider()
    }
}

@Composable
fun CircularIndeterminateProgressBar() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center),
        strokeWidth = 4.dp
    )
}

@Composable
fun LinearIndeterminateProgressBar() {
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center),
        color = Color.Blue
    )
}

@Composable
fun CustomLinearProgressBar() {
    val infiniteTransition = rememberInfiniteTransition()
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )
    LinearProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center),
        color = Color.Black,
        progress = progress
    )
}

@Composable
fun CustomCircularProgressBar() {
    val infiniteTransition = rememberInfiniteTransition()
    val sweep by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center),
        strokeWidth = 12.dp,
        progress = sweep / 360f
    )
}

@Composable
fun VerticalDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray
) {
    TabRowDefaults.Divider(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp),
        color = color
    )
}





