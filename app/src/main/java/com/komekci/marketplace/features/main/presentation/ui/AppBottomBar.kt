package com.komekci.marketplace.features.main.presentation.ui

import androidx.annotation.Keep
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun AppBottomBar(
    rootNavController: NavHostController,
    badge: Int? = 0
) {
    val appSettingsState = LocalSettings.current

    val strings = LocalStrings.current
    val items = listOf(
        BottomNavigationItem(
            title = strings.home,
            route = Routes.Home,
            icon = R.drawable.home_ios
        ),
        BottomNavigationItem(
            title = strings.basket,
            route = Routes.Basket,
            icon = R.drawable.basket_ios
        ),
        BottomNavigationItem(
            title = strings.chat,
            route = Routes.Chats,
            icon = R.drawable.chat_ios,
            badge = badge ?: 0
        ),
        BottomNavigationItem(
            title = strings.settings,
            route = Routes.Profile,
            icon = R.drawable.profile_ios
        ),
    )
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()

    val globalRoute = LocalRouteState.current

    val isVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    AnimatedVisibility(
        visible = appSettingsState.hideBottomNavigation.not() && isVisible.not(),
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .navigationBarsPadding()
                .imePadding()
                .border(0.5.dp, Color(0xFFEBEBEF))
        ) {
            items.forEach { item ->
                val isSelected = item.route ==
                        navBackStackEntry?.destination?.route
                NavigationBarItem(
                    selected = isSelected,
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.background
                    ),
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                fontWeight = FontWeight.W600
                            ),
                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color(
                                0xFF333333
                            )
                        )
                    },
                    icon = {
                        BadgedBox(badge = {
                            if (item.badge > 0) {
                                Badge() {
                                    Text(text = "${item.badge}", color = Color.White)
                                }
                            }

                        }) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.title,
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color(
                                    0xFF999999
                                )
                            )
                        }
                    },
                    onClick = {
                        rootNavController.navigate(item.route) {
                            popUpTo(rootNavController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Keep
data class BottomNavigationItem(
    val title: String,
    val route: String,
    val icon: Int,
    val badge: Int = 0
)

