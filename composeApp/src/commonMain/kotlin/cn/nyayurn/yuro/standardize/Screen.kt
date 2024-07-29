package cn.nyayurn.yuro.standardize

import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cn.nyayurn.yuro.standardize.componments.Body
import cn.nyayurn.yuro.standardize.componments.Description
import cn.nyayurn.yuro.standardize.componments.SideNavigation
import cn.nyayurn.yuro.standardize.componments.Start
import cn.nyayurn.yuro.standardize.componments.Title
import kotlinx.coroutines.launch

object HomeScreen : Screen {
    @Composable
    override fun Content() {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(32.dp)
        ) {
            val screenSize by remember(maxWidth) {
                mutableStateOf(
                    when {
                        maxWidth < 600.dp -> ScreenSize.Compact
                        maxWidth < 840.dp -> ScreenSize.Medium
                        else -> ScreenSize.Expanded
                    }
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Title(screenSize = screenSize)
                Description(screenSize = screenSize)
                Start()
            }
        }
    }
}

object DocumentScreen : Screen {
    @Composable
    override fun Content() {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val screenSize by remember(maxWidth) {
                mutableStateOf(
                    when {
                        maxWidth < 600.dp -> ScreenSize.Compact
                        maxWidth < 840.dp -> ScreenSize.Medium
                        else -> ScreenSize.Expanded
                    }
                )
            }
            var page by rememberSaveable { mutableStateOf(DocumentPage.Introduction) }
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet {
                        SideNavigation(
                            page = page,
                            onChange = { page = it },
                            modifier = Modifier.width(300.dp)
                        )
                    }
                }
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        animateDpAsState(
                            when (screenSize) {
                                ScreenSize.Expanded -> 32.dp
                                else -> 0.dp
                            },
                            TweenSpec(600)
                        ).value
                    ),
                    modifier = Modifier.fillMaxSize()
                ) {
                    SideNavigation(
                        page = page,
                        onChange = { page = it },
                        modifier = Modifier.width(
                            animateDpAsState(
                                when (screenSize) {
                                    ScreenSize.Expanded -> 300.dp
                                    else -> 0.dp
                                },
                                TweenSpec(600)
                            ).value
                        )
                    )
                    Column {
                        if (screenSize != ScreenSize.Expanded) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = null
                                )
                            }
                        }
                        Body(
                            screenSize = screenSize,
                            page = page
                        )
                    }
                }
            }
        }
    }
}

fun stringLengthConverter(value: String) = TwoWayConverter<String, AnimationVector1D>(
    convertToVector = {
        AnimationVector(it.length.toFloat())
    },
    convertFromVector = {
        value.substring(0..<it.value.toInt())
    }
)

enum class DocumentPage {
    Introduction, Overview, Action, Event, Encoding, Element
}

enum class ScreenSize {
    Compact, Medium, Expanded
}