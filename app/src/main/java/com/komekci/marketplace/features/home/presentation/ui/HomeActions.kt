package com.komekci.marketplace.features.home.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.main.presentation.ui.AppDrawer
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun HomeActions(
    navHostController: NavHostController,
    mainNavHostController: NavHostController,
    showActions: Boolean = true,
    isProfile: Boolean = false,
    onFavoriteClick: () -> Unit = {},
    onSearch: (String) -> Unit = {}
) {
    val strings = LocalStrings.current
    val languageOpen = remember {
        mutableStateOf(false)
    }
    val drawerOpen = remember {
        mutableStateOf(false)
    }
    SelectLanguage(open = languageOpen.value) {
        languageOpen.value = false
    }
    fun onBottomChanged(path: String) {
        mainNavHostController.navigate(path) {
            popUpTo(mainNavHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }
    AppDrawer(
        open = drawerOpen.value,
        navHostController = navHostController,
        onBottomNavigationChange = {
           onBottomChanged(it)
        }) {
        drawerOpen.value = false
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {


            SearchInput(modifier = Modifier.weight(1f), initialValue = "") {
                onSearch(it)
            }

            Box(
                modifier = Modifier
                    .border(
                        1.dp,
                        Color(0xFFD3D4DB),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        if (isProfile) {
                            onFavoriteClick()
                        } else {
                            languageOpen.value = true
                        }
                    }
                    .padding(14.dp)
                    .height(25.dp),
                contentAlignment = Alignment.Center
            ) {
                if(isProfile) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = Color(0xFF60646C)
                    )
                } else {
                    Text(
                        text = "TM",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF1C2024)
                    )
                }

            }

            Box(
                modifier = Modifier
                    .border(
                        1.dp,
                        Color(0xFFD3D4DB),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        navHostController.navigate(Routes.Notifications) {
                            launchSingleTop = true
                        }
                        onBottomChanged(Routes.Home)
                    }
                    .padding(14.dp)
                    .height(25.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.notification),
                    contentDescription = null,
                    tint = Color(0xFF60646C)
                )
            }


            Box(
                modifier = Modifier
                    .border(
                        1.dp,
                        Color(0xFFD3D4DB),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        drawerOpen.value = true
                    }
                    .padding(14.dp)
                    .height(25.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = null,
                    tint = Color(0xFF60646C)
                )
            }
        }

        if (showActions) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color(0xFFD3D4DB),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable {
                            navHostController.navigate(Routes.Location) {
                                launchSingleTop = true
                            }
                        }
                        .padding(8.dp)
                        .weight(1f)
                        .height(25.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = null,
                        tint = Color(0xFF1C2024)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Aşgabat",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF1C2024)
                    )
                }

                Row(
                    modifier = Modifier
                        .border(
                            1.dp,
                            Color(0xFFD3D4DB),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable {
                            navHostController.navigate(Routes.Category) {
                                launchSingleTop = true
                            }
                        }
                        .padding(8.dp)
                        .weight(1f)
                        .height(25.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.category),
                        contentDescription = null,
                        tint = Color(0xFF1C2024)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = strings.categories,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF1C2024)
                    )
                }
            }
        }
    }
}

@Composable
fun SearchInput(modifier: Modifier = Modifier, initialValue: String = "", onSearch: (String) -> Unit) {
    val strings = LocalStrings.current
    val text = rememberSaveable {
        mutableStateOf(initialValue)
    }

    Row(
        modifier = modifier.clip(RoundedCornerShape(6.dp)).background(
            color = Color(0xFFF2F2F5),
            shape = RoundedCornerShape(6.dp)
        ).clickable {
            Log.e("Search", "clicked")
            onSearch("")
        }.padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo_only),
            contentDescription = null, tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = strings.searchShop,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFF8B8D98),
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500)
        )
    }
}

@Composable
fun SearchInput2(modifier: Modifier = Modifier, initialValue: String = "", onSearch: (String) -> Unit) {
    val strings = LocalStrings.current
    val text = rememberSaveable {
        mutableStateOf(initialValue)
    }

    TextField(
        value = text.value,
        onValueChange = { text.value = it },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFF2F2F5),
            focusedContainerColor = Color(0xFFF2F2F5),
            disabledContainerColor = Color(0xFFF2F2F5),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(6.dp),
        maxLines = 1,
        placeholder = {
            Text(
                text = strings.searchShop,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color(0xFF8B8D98),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500)
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if(text.value.isNotEmpty()) {
                    onSearch(text.value)
                }
            }
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.logo_only),
                contentDescription = null, tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
        },
        modifier = modifier
    )
}