package com.zekierciyas.jetpackcomposesamples.ui.samples.music_player

import android.media.MediaPlayer
import android.widget.ScrollView
import android.widget.Space
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zekierciyas.jetpackcomposesamples.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val songUrl = "https://mp3indirdurum.com/mp3dosyalari/cw==/ZQ==/sertab-erener-ask.mp3"

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview("MusicPlayerScreen")
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

    BottomSheetScaffold(
        sheetContent = {

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp)
            ) {
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

                        MusicSlider(currentPosition = currentPosition, duration = duration) {
                            mediaPlayer.seekTo(it.toInt())
                            currentPosition = it.toInt()
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 32.dp, bottom = 16.dp)
                        ) {

                            PlayPauseButton(isPlaying = isPlaying) {
                                if (isPlaying) {
                                    mediaPlayer.pause()
                                    isPlaying = false
                                } else {
                                    mediaPlayer.start()
                                    isPlaying = true
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        ImageBanner()

                        Spacer(modifier = Modifier.height(20.dp))

                        LyricsColumn(lyrics = stringResource(id = R.string.song_lyrics))

                    }
                }
            }


        },
        sheetPeekHeight = 200.dp,
    ) {
        it.calculateBottomPadding()
    }
}

@Composable
fun MusicSlider(currentPosition: Int, duration: Int, onValueChange: (value: Float) -> Unit) {
    Slider(
        value = currentPosition.toFloat(),
        onValueChange = {
            onValueChange.invoke(it)
        },
        valueRange = 0f..duration.toFloat(),
        modifier = Modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = Color.Black,
            activeTrackColor = Color.Black,
            inactiveTrackColor = Color.Black
        )
    )
}
@Composable
fun PlayPauseButton(isPlaying: Boolean, onClick: () -> Unit) {
    var playState by remember { mutableStateOf(PlayPauseState.Play) }
    val transition = updateTransition(targetState = playState, label = "playPauseAnimation")

    val playIconRotation by transition.animateFloat(
        label = "playIconRotation",
        transitionSpec = {
            tween(durationMillis = 300, easing = FastOutSlowInEasing)
        }
    ) {
        if (it == PlayPauseState.Play) 0f else 180f
    }

    val pauseIconRotation by transition.animateFloat(
        label = "pauseIconRotation",
        transitionSpec = {
            tween(durationMillis = 300, easing = FastOutSlowInEasing)
        }
    ) {
        if (it == PlayPauseState.Pause) 0f else -180f

    }

    IconButton(onClick = {
        onClick()
        playState = if (isPlaying) PlayPauseState.Play else PlayPauseState.Pause
    }) {
        if (playState == PlayPauseState.Pause) {
            Icon(
                imageVector = Icons.Filled.PauseCircle,
                contentDescription = "Pause",
                modifier = Modifier
                    .height(80.dp)
                    .width(80.dp)
                    .rotate(pauseIconRotation),
                tint = Color.Black
            )
        } else {
            Box {
                Icon(
                    imageVector = Icons.Filled.PlayCircle,
                    contentDescription = "Play",
                    modifier = Modifier
                        .height(80.dp)
                        .width(80.dp)
                        .rotate(playIconRotation),
                    tint = Color.Black
                )
            }
        }
    }
}

enum class PlayPauseState { Play, Pause }

@Composable
fun ImageBanner() {
    Surface(
        color = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.song_banner),
            contentDescription = "Cool image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.aspectRatio(1.5f)
        )
    }
}

fun Int.toFormattedTimeString(): String {
    val minutes = this / 1000 / 60
    val seconds = this / 1000 % 60
    return String.format("%d:%02d", minutes, seconds)
}

@Composable
fun LyricsColumn(lyrics: String) {
    Text(
        text = lyrics,
        style = MaterialTheme.typography.body1,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

