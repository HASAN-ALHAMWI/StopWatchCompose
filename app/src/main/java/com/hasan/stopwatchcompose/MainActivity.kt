package com.hasan.stopwatchcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hasan.stopwatchcompose.ui.theme.StopWatchComposeTheme

@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopWatchComposeTheme {
                val viewModel = viewModel<MainViewModel>()
                Surface {
                    Scaffold { it ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Row {
                                val numberTransitionSpec: AnimatedContentScope<String>.() -> ContentTransform =
                                    {
                                        slideInVertically(initialOffsetY = { it }) + fadeIn() with slideOutVertically(
                                            targetOffsetY = { -it }) + fadeOut() using SizeTransform(
                                            false
                                        )
                                    }
                                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.h3) {
                                    AnimatedContent(
                                        targetState = viewModel.hours,
                                        transitionSpec = numberTransitionSpec
                                    ) { hours ->
                                        Text(text = hours)
                                    }
                                    Text(text = ":")
                                    AnimatedContent(
                                        targetState = viewModel.minutes,
                                        transitionSpec = numberTransitionSpec
                                    ) { minutes ->
                                        Text(text = minutes)
                                    }
                                    Text(text = ":")
                                    AnimatedContent(
                                        targetState = viewModel.seconds,
                                        transitionSpec = numberTransitionSpec
                                    ) { seconds ->
                                        Text(text = seconds)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                AnimatedContent(targetState = viewModel.isPlaying) { isPlaying ->
                                    if (isPlaying) {
                                        IconButton(onClick = {
                                            viewModel.pause()
                                        }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_pause),
                                                contentDescription = "pause"
                                            )
                                        }
                                    } else {
                                        IconButton(onClick = {
                                            viewModel.start()
                                        }) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.ic_play),
                                                contentDescription = "play"
                                            )
                                        }
                                    }
                                }
                                IconButton(onClick = {
                                    viewModel.stop()
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_stop),
                                        contentDescription = "stop"
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }
        }
    }
}