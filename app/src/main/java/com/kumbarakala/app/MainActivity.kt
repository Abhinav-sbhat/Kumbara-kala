package com.kumbarakala.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.kumbarakala.app.data.repository.AuthRepository
import com.kumbarakala.app.navigation.KumbaraNavGraph
import com.kumbarakala.app.ui.theme.KumbaraKalaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use dark icons on light status bar so phone/charge/network icons are visible
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        setContent {
            KumbaraKalaTheme {
                val isLoggedIn by authRepository.isLoggedIn()
                    .collectAsState(initial = false)

                KumbaraNavGraph(
                    context = this,
                    isLoggedIn = isLoggedIn,
                    onLogout = { /* Auth state change handled reactively */ }
                )
            }
        }
    }
}
