package com.zekierciyas.jetpackcomposesamples.ui.samples.expand_image

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zekierciyas.jetpackcomposesamples.R

@Composable
fun ExpandableImageScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ExpandableImage1(drawable = R.drawable.image_pikachu)
        ExpandableImage2(drawable = R.drawable.image_bulbasaur)
    }
}


/**
 * Expands the image standard way of re-composition
 * @param sizeAfterExpand : Size before clicked the image as percentage
 * @param sizeBeforeExpand : Size before clicked the image as percentage
 */
@Composable
fun ExpandableImage1(
    sizeBeforeExpand: Float = 0.2F,
    sizeAfterExpand: Float = 0.6F,
    @DrawableRes drawable: Int
) {
    var isExpanded by remember { mutableStateOf(false) }

    val image = painterResource(drawable)
    val imageSize = if (isExpanded) sizeAfterExpand else sizeBeforeExpand

    Image(
        painter = image,
        contentDescription = "My Image",
        modifier = Modifier
            .width((LocalConfiguration.current.screenWidthDp * imageSize).dp)
            .height((LocalConfiguration.current.screenHeightDp * imageSize).dp)
            .clickable { isExpanded = !isExpanded }
    )
}

/**
 *  Expands the image without re-composition the image
 *   @param sizeAfterExpand : Size before clicked the image as percentage
 *   @param sizeBeforeExpand : Size before clicked the image as percentage
 */
@Composable
fun ExpandableImage2(
    sizeBeforeExpand: Float = 0.2F,
    sizeAfterExpand: Float = 0.6F,
    @DrawableRes drawable: Int
) {
    var isExpanded by remember { mutableStateOf(false) }
    var scale by remember { mutableStateOf(1f) }
    var translation by remember { mutableStateOf(Offset.Zero) }

    val image = painterResource(drawable)

    val initialWidth = (LocalConfiguration.current.screenWidthDp * sizeBeforeExpand).dp
    val initialHeight = (LocalConfiguration.current.screenHeightDp * sizeBeforeExpand).dp
    val expandedWidth = (LocalConfiguration.current.screenWidthDp * sizeAfterExpand).dp
    val expandedHeight = (LocalConfiguration.current.screenHeightDp * sizeAfterExpand).dp

    val imageWidth = animateDpAsState(if (isExpanded) expandedWidth else initialWidth)
    val imageHeight = animateDpAsState(if (isExpanded) expandedHeight else initialHeight)

    Image(
        painter = image,
        contentDescription = "My Image",
        modifier = Modifier
            .width(imageWidth.value)
            .height(imageHeight.value)
            .scale(scale)
            .clickable { isExpanded = !isExpanded }
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale *= zoom
                    translation += pan * scale

                    if (scale < 1f) {
                        scale = 1f
                        translation = Offset.Zero
                    }
                    if (scale > 2f) {
                        scale = 2f
                    }
                }
            }
    )
}


