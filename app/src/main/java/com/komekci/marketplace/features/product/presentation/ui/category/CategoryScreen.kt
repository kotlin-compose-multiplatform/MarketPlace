package com.komekci.marketplace.features.product.presentation.ui.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.home.domain.model.ShopEntity
import com.komekci.marketplace.features.home.presentation.ui.SearchInput
import com.komekci.marketplace.features.main.presentation.ui.AppDrawer
import com.komekci.marketplace.features.main.presentation.ui.commonRoutes
import com.komekci.marketplace.features.product.presentation.ui.details.DetailsScreen
import com.komekci.marketplace.features.product.presentation.ui.filter.FilterDialog
import com.komekci.marketplace.features.product.presentation.ui.product.OnCategoryScreen
import com.komekci.marketplace.features.product.presentation.viewmodel.CategoryViewModel
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.navigation.Routes
import com.primex.core.plus
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextField
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextFieldDefaults
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Mic
import io.github.alexzhirkevich.cupertino.rememberCupertinoSearchTextFieldState
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun CategoryScreen(
    navHostController: NavHostController,
    shopEntity: ShopEntity? = null,
    defaultRoute: String = Routes.onShop
) {
    val drawerOpen = remember {
        mutableStateOf(false)
    }

    val state = rememberCollapsingToolbarScaffoldState()
    val strings = LocalStrings.current
    val viewmodel: CategoryViewModel = hiltViewModel()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination



    val localFilter = LocalProductFilter.current
    val filterOpen = rememberSaveable {
        mutableStateOf(false)
    }
    val filters = viewmodel.filter.collectAsState()
    val categoryOpen = rememberSaveable {
        mutableStateOf(false)
    }
    val searchState = rememberCupertinoSearchTextFieldState(
        blockScrollWhenFocusedAndEmpty = true
    )
    val globalRoute = LocalRouteState.current
    LaunchedEffect(true) {
        viewmodel.initCategories(
            shopEntity?.id ?: "",
            region = localFilter.value.region,
            district = localFilter.value.district
        )
        viewmodel.initFilters()
    }
    val dataState = viewmodel.state.collectAsState()
    AppDrawer(
        open = drawerOpen.value,
        navHostController = navHostController,
        onBottomNavigationChange = {}) {
        drawerOpen.value = false
    }
    FilterDialog(open = filterOpen.value, data = filters.value.data) {
        filterOpen.value = false
    }
    CategoryDialog(open = categoryOpen.value) {
        categoryOpen.value = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize(), verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        if(currentDestination?.route == null || currentDestination.route!!.contains("details").not()) {
            Row(
                modifier = Modifier
                    .zIndex(2f)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                            navHostController.navigateUp()
                        }
                        .padding(4.dp)
                        .height(25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    strings.products,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600
                    ),
                    color = Color(0xFF0F1E3C)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                            filterOpen.value = true
                        }
                        .padding(4.dp)
                        .height(25.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.filter_button),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }


        if (dataState.value.loading) {
            AppLoading(modifier = Modifier.fillMaxSize())
        } else {
            CollapsingToolbarScaffold(modifier = Modifier
                .fillMaxSize()
                .zIndex(1f),
                state = state,
                scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed, toolbar = {
                    var searchValue by rememberSaveable {
                        mutableStateOf("")
                    }
                    if(currentDestination?.route == null || currentDestination.route!!.contains("details").not()) {
                        CupertinoSearchTextField(
                            value = searchValue,
                            trailingIcon = {
                                CupertinoIcon(
                                    CupertinoIcons.Default.Mic,
                                    tint = Color(0xFF64748B),
                                    contentDescription = "record"
                                )
                            },
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    navHostController.navigate(
                                        Routes.SearchRoute.replace(
                                            "{text}",
                                            searchValue
                                        )
                                    )
                                }
                            ),
                            placeholder = {
                                CupertinoText(strings.searchFromApp)
                            },
                            onValueChange = {
                                searchValue = it
                            },
                            state = searchState,
                            paddingValues = CupertinoSearchTextFieldDefaults.PaddingValues.plus(
                                PaddingValues(bottom = 12.dp)
                            )
                        )
                    }

                }) {
                NavHost(navController = navController, startDestination = defaultRoute) {
                    composable(Routes.onShop) {
                        OnShopScreen(
                            shopEntity = shopEntity,
                            state = dataState.value,
                            navHostController = navController,
                            homeNavHostController = navHostController
                        )
                    }

                    composable(Routes.onCategory) {
                        OnCategoryScreen(navHostController, dataState.value)
                    }

                    commonRoutes(navHostController)
                }
            }
        }
    }
}