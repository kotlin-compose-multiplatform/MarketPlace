package com.komekci.marketplace.features.main.presentation.ui

import androidx.annotation.Keep
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.ui.navigation.Routes

@Keep
data class DrawerData(
    val title: String,
    val icon: Int,
    val onClick: () -> Unit
)


@Composable
fun AppDrawer(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    open: Boolean = false,
    onBottomNavigationChange: (String)-> Unit,
    onDismiss: () -> Unit
) {
    val globalRoute = LocalRouteState.current
    val strings = LocalStrings.current
    val items = listOf(
        DrawerData(title = strings.categories, icon = R.drawable.category) {
            onBottomNavigationChange(Routes.Home)
            onDismiss()
            navHostController.navigate(Routes.Category) {
                launchSingleTop = true
            }
        },
        DrawerData(title = strings.discount, icon = R.drawable.percent) {
            onBottomNavigationChange(Routes.Home)
            onDismiss()
            navHostController.navigate(Routes.Discount) {
                launchSingleTop = true
            }
        },
//        DrawerData(title = strings.shops, icon = R.drawable.warehouse) {
//            onBottomNavigationChange(Routes.Home)
//            onDismiss()
//            navHostController.navigate(Routes.Home) {
//                launchSingleTop = true
//            }
//        },
        DrawerData(title = strings.createShop, icon = R.drawable.storefront) {
            globalRoute.value = globalRoute.value.copy(
                profileRoute = Routes.SelectPaymentType,
                homeRoute = Routes.Profile
            )
            onDismiss()
        },
        DrawerData(title = strings.payments, icon = R.drawable.money) {
            globalRoute.value = globalRoute.value.copy(
                profileRoute = Routes.Payments,
                homeRoute = Routes.Profile
            )
            onDismiss()
        },
        DrawerData(title = strings.helper, icon = R.drawable.personsimplerun) {
            onBottomNavigationChange(Routes.Home)
            onDismiss()
            navHostController.navigate(Routes.Help) {
                launchSingleTop = true
            }
        },
        DrawerData(title = strings.aboutUs, icon = R.drawable.plussquare) {
            onBottomNavigationChange(Routes.Home)
            onDismiss()
            navHostController.navigate(Routes.AboutUs) {
                launchSingleTop = true
            }
        },
        DrawerData(title = strings.faq, icon = R.drawable.sealquestion) {
            onBottomNavigationChange(Routes.Home)
            onDismiss()
            navHostController.navigate(Routes.Faq) {
                launchSingleTop = true
            }
        }
    )
    if (open) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false),
        ) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .navigationBarsPadding()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 100.dp)
                    .background(
                        Color.White.copy(0.8f),
                        shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
                    )
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Spacer(modifier = Modifier.height(22.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = null,
                            tint = Color(0xFF343330),
                            modifier = Modifier.size(35.dp)
                        )
                    }
                }

                items.forEachIndexed { index, _ ->
                    DrawerItem(title = items[index].title, icon = items[index].icon) {
                        items[index].onClick()
                    }
                }
            }
        }

    }

}

@Composable
fun DrawerItem(
    title: String,
    icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(16.dp)
            .clip(RoundedCornerShape(6.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = title,
            tint = Color(0xFF1C2024),
            modifier = Modifier.size(25.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
            color = Color(0xFF1C2024)
        )
    }
}