package com.zekierciyas.jetpackcomposesamples.ui.samples.slider_effect

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.*
import com.zekierciyas.jetpackcomposesamples.R
import kotlin.math.abs

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(images: List<Int>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(
        pageCount = images.size,
        initialPage = images.size / 2
    )

    Box(modifier = modifier) {
        HorizontalPager(
            modifier = modifier,
            state = pagerState) { page ->
            val index = page % images.size
            val scale = 1 - abs(pagerState.currentPageOffset)
            val translationX = (if (pagerState.currentPageOffset > 0) -1 else 1) *
                    ((1 - scale) * 800)
            Image(
                painter = painterResource(id = images[index]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        this.transformOrigin = TransformOrigin.Center
                        this.translationX = translationX
                        this.scaleX = scale
                        this.scaleY = scale
                    }
            )
        }
        PagerIndicator(pagerState = pagerState, modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerIndicator(pagerState: PagerState, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Row(horizontalArrangement = Arrangement.Center) {
            for (page in 0 until pagerState.pageCount ) {
                val selected = page == pagerState.currentPage
                val color = if (selected) Color.LightGray else Color.DarkGray
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .padding(4.dp)
                        .background(color, CircleShape)
                )
            }
        }
    }
}

@Composable
fun SliderEffectScreen() {
    val imageList = listOf(
        R.drawable.image_bulbasaur,
        R.drawable.image_venusaur,
        R.drawable.image_pikachu,
        R.drawable.image_venusaur,
        R.drawable.image_pikachu
    )
    
    ImageSlider(
        images = imageList,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
}



