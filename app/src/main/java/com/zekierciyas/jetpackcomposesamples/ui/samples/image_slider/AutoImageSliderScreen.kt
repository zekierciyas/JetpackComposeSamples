package com.zekierciyas.jetpackcomposesamples.ui.samples.image_slider

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.painter.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.zekierciyas.jetpackcomposesamples.R
import kotlinx.coroutines.delay


@Preview("ImageSlider")
@Composable
fun AutoImageSliderScreen() {
    val images = listOf(
        ImageBitmap.imageResource(R.drawable.image_pikachu),
        ImageBitmap.imageResource(R.drawable.image_bulbasaur),
        ImageBitmap.imageResource(R.drawable.image_charizard),
        ImageBitmap.imageResource(R.drawable.image_squirtle),
        ImageBitmap.imageResource(R.drawable.image_venusaur)
    )
    Surface(Modifier.fillMaxSize()) {
        AutoSliderScreen(images)
    }
}

@Composable
fun AutoSliderScreen(images: List<ImageBitmap>, autoSlideInterval: Long = 1800L) {
    val currentIndex = remember { mutableStateOf(0) }
    val numIcons = images.size

    LaunchedEffect(Unit) {
        while (true) {
            delay(autoSlideInterval)
            val nextIndex = (currentIndex.value + 1) % images.size
            currentIndex.value = nextIndex
        }
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .wrapContentWidth(Alignment.CenterHorizontally)
        .wrapContentHeight(CenterVertically)) {
        Image(
            bitmap = images[currentIndex.value],
            contentDescription = null,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            repeat(numIcons) { index ->
                val isSelected = index == currentIndex.value
                Icon(
                    imageVector = Icons.Rounded.Star ,
                    contentDescription = null,
                    tint = if (isSelected) Color.DarkGray else Color.Gray,
                    modifier = Modifier
                        .size(if (isSelected) 42.dp else 28.dp)
                        .clickable { currentIndex.value = index }
                )
            }
        }
    }
}


