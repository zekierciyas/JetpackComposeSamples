package com.zekierciyas.jetpackcomposesamples.ui.samples.music_player

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val songUrl = "https://mp3indirdurum.com/mp3dosyalari/cw==/ZQ==/sertab-erener-ask.mp3"

@Composable
fun MusicPlayer(url: String = songUrl) {
    val mediaPlayer by remember { mutableStateOf(MediaPlayer()) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentPosition by remember { mutableStateOf(0) }
    var duration by remember { mutableStateOf(0) }

    mediaPlayer.setOnPreparedListener {
        duration = it.duration
    }

    mediaPlayer.setOnCompletionListener {
        isPlaying = false
    }

    //Launching new scope for fetching source as async
    LaunchedEffect(url) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
    }

    //Launching scope for observing the current position of the song to reflect it to ui
    LaunchedEffect(Unit) {
        launch {
            while(true) {
                currentPosition = mediaPlayer.currentPosition
                delay(1000)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = currentPosition.toFormattedTimeString(),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = duration.toFormattedTimeString(),
                    fontWeight = FontWeight.Bold
                )
            }

            Slider(
                value = currentPosition.toFloat(),
                onValueChange = {
                    mediaPlayer.seekTo(it.toInt())
                    currentPosition = it.toInt()
                },
                valueRange = 0f..duration.toFloat(),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp, bottom = 16.dp)
            ) {
                IconButton(
                    onClick = {
                        if (isPlaying) {
                            mediaPlayer.pause()
                            isPlaying = false
                        } else {
                            mediaPlayer.start()
                            isPlaying = true
                        }
                    }
                ) {
                    if (isPlaying) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Pause",
                            tint = Color.Gray
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Play",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

fun Int.toFormattedTimeString(): String {
    val minutes = this / 1000 / 60
    val seconds = this / 1000 % 60
    return String.format("%d:%02d", minutes, seconds)
}
