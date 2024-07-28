package cn.nyayurn.yuro.standardize

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cn.nyayurn.yuro.standardize.theme.Theme

@Composable
internal fun App() {
    Theme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Navigator(HomeScreen) { navigator ->
                FadeTransition(navigator)
            }
        }
    }
}