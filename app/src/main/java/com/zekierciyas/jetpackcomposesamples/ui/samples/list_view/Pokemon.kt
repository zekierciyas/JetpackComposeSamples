package com.zekierciyas.jetpackcomposesamples.ui.samples.list_view

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

data class Pokemon (
    val name: String,
    val description: String,
    val type: String,
    @DrawableRes val imageResourceId: Int
)
