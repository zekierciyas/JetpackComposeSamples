package com.zekierciyas.jetpackcomposesamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.zekierciyas.jetpackcomposesamples.ui.samples.progress_loaders.ProgressLoaders
import com.zekierciyas.jetpackcomposesamples.ui.theme.JetpackComposeSamplesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeSamplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.onBackground
                ) {
                    //PokemonListScreen()
                    //ExpandableImageScreen()
                    //AutoImageSliderScreen()
                    ProgressLoaders()
                }
            }
        }
    }
}

