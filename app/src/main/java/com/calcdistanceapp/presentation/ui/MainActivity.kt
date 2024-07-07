package com.calcdistanceapp.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calcdistanceapp.data.event.KoleoEvent
import com.calcdistanceapp.presentation.theme.CalcDistanceAppTheme
import com.calcdistanceapp.presentation.ui.search.SearchScreenComposable
import com.calcdistanceapp.presentation.ui.viewmodel.KoleoViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalcDistanceAppTheme {
                val koleoViewModel: KoleoViewModel = hiltViewModel()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopBarComposable() }
                ) { paddingValues ->
                    val dataState by koleoViewModel.dataState.collectAsStateWithLifecycle()

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        when {
                            dataState.isLoading -> {
                                LoadingScreenComposable()
                            }

                            dataState.error.isNotEmpty() -> {
                                ErrorScreenComposable(
                                    error = dataState.error,
                                    onRetryClick = { koleoViewModel.onEvent(KoleoEvent.FetchEvent) }
                                )
                            }

                            else -> {
                                SearchScreenComposable(
                                    resultText = koleoViewModel.resultText,
                                    dataState = dataState,
                                    onEvent = { event -> koleoViewModel.onEvent(event = event)}
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

