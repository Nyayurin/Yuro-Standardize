package cn.nyayurn.yuro.standardize

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cn.nyayurn.yuro.standardize.theme.Theme

@Composable
internal fun App() {
    Theme {
        BoxWithConstraints {
            val viewModel = viewModel { YuroViewModel() }
            remember(maxWidth, maxHeight) { viewModel.screen = Screen(maxWidth, maxHeight) }
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
}

class YuroViewModel : ViewModel() {
    var screen: Screen by mutableStateOf(Screen(0.dp, 0.dp))
}

data class Screen(val width: Dp, val height: Dp) {
    val size: Pair<ScreenSize, ScreenSize> = when {
        width < 600.dp -> ScreenSize.Compact
        width < 840.dp -> ScreenSize.Medium
        else -> ScreenSize.Expanded
    } to when {
        height < 480.dp -> ScreenSize.Compact
        height < 900.dp -> ScreenSize.Medium
        else -> ScreenSize.Expanded
    }
}