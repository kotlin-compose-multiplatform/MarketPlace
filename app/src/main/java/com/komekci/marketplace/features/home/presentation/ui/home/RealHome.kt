package com.komekci.marketplace.features.home.presentation.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.home.presentation.ui.category.CategoryScreen
import com.komekci.marketplace.features.home.presentation.ui.location.SelectLocation
import com.komekci.marketplace.features.home.presentation.ui.shops.Shops
import com.komekci.marketplace.ui.app.CupertinoOverscrollEffect
import com.komekci.marketplace.ui.navigation.Routes
import com.primex.core.plus
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldDefaults
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoIconButton
import io.github.alexzhirkevich.cupertino.CupertinoNavigationTitle
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextField
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextFieldDefaults
import io.github.alexzhirkevich.cupertino.CupertinoSegmentedControl
import io.github.alexzhirkevich.cupertino.CupertinoSegmentedControlDefaults
import io.github.alexzhirkevich.cupertino.CupertinoSegmentedControlTab
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.PresentationDetent
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Bell
import io.github.alexzhirkevich.cupertino.icons.outlined.Mic
import io.github.alexzhirkevich.cupertino.rememberCupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.rememberCupertinoSearchTextFieldState
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import io.github.alexzhirkevich.cupertino.section.ProvideSectionStyle
import io.github.alexzhirkevich.cupertino.section.SectionStyle
import io.github.alexzhirkevich.cupertino.section.sectionContainerBackground
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalCupertinoApi::class, ExperimentalFoundationApi::class)
@Composable
fun RealHome(navHostController: NavHostController = rememberNavController()) {

    val strings = LocalStrings.current

    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val searchState = rememberCupertinoSearchTextFieldState(
        scrollableState = scrollState,
        blockScrollWhenFocusedAndEmpty = true
    )

    val density = LocalDensity.current

    val scaffoldState = rememberCupertinoBottomSheetScaffoldState(
        rememberCupertinoSheetState(
            presentationStyle = PresentationStyle.Modal(
                detents = setOf(
                    PresentationDetent.Fraction(.6f),
                ),
            )
//            presentationStyle = PresentationStyle.Fullscreen
        )
    )

    val isTransparent by remember(scrollState, density) {
        derivedStateOf {
            // top bar is collapsing only on mobile
//            if (IsIos) {
            scrollState.value < density.run { 20.dp.toPx() }
//            } else {
//                !scrollState.canScrollBackward
//            }

        }
    }

    CupertinoBottomSheetScaffold(
        appBarsBlurAlpha = 20f,
        appBarsBlurRadius = 20.dp,
        hasNavigationTitle = true,
        colors = CupertinoBottomSheetScaffoldDefaults.colors(
            sheetContainerColor = Color.White,
        ),
        sheetContent = {

        },
        scaffoldState = scaffoldState,
        topBar = {
            CupertinoTopAppBar(
                actions = {
                    CupertinoIconButton(
                        onClick = {
                            navHostController.navigate(Routes.Notifications)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.bell_ios),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    CupertinoText(strings.home)
                }
            )
        },
        bottomBar = {

        }
    ) { pv ->
        ProvideSectionStyle(
            SectionStyle.Sidebar
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .nestedScroll(searchState.nestedScrollConnection)
                    .verticalScroll(scrollState)
                    .padding(pv)
                    .padding(top = 10.dp)
            ) {

                CupertinoNavigationTitle {
                    Text(
                        strings.home, style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.W700,
                            fontSize = 34.sp
                        ),
                        color = Color(0xFF0F1E3C)
                    )
                }
                var searchValue by rememberSaveable {
                    mutableStateOf("")
                }
                CupertinoSearchTextField(
                    value = searchValue,
                    colors = CupertinoSearchTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFEFEFEF),
                        unfocusedContainerColor = Color(0xFFEFEFEF),
                    ),
                    trailingIcon = {
                        CupertinoIcon(
                            CupertinoIcons.Default.Mic,
                            tint = Color(0xFF64748B),
                            contentDescription = "record"
                        )
                    },
                    placeholder = {
                        CupertinoText(strings.searchFromApp)
                    },
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            navHostController.navigate(Routes.SearchRoute.replace("{text}", searchValue))
                        }
                    ),
                    onValueChange = {
                        searchValue = it
                    },
                    state = searchState,
                    paddingValues = CupertinoSearchTextFieldDefaults.PaddingValues.plus(
                        PaddingValues(bottom = 12.dp)
                    )
                )

                val pageState = rememberPagerState { 3 }

                CupertinoSegmentedControl(
                    paddingValues = PaddingValues(16.dp),
                    selectedTabIndex = pageState.currentPage,
                    colors = CupertinoSegmentedControlDefaults.colors(
                        containerColor = Color(0xFFEFEFEF)
                    )
                ) {
                    CupertinoSegmentedControlTab(
                        isSelected = pageState.currentPage == 0,
                        onClick = {
                            coroutineScope.launch {
                                pageState.scrollToPage(0)
                            }
                        }
                    ) {
                        CupertinoText(
                            strings.shops,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W600
                            ),
                            color = Color(0xFF0F1E3C)
                        )
                    }

                    CupertinoSegmentedControlTab(
                        isSelected = pageState.currentPage == 1,
                        onClick = {
                            coroutineScope.launch {
                                pageState.scrollToPage(1)
                            }
                        }
                    ) {
                        CupertinoText(
                            strings.categories,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W600
                            ),
                            color = Color(0xFF0F1E3C)
                        )
                    }

                    CupertinoSegmentedControlTab(
                        isSelected = pageState.currentPage == 2,
                        onClick = {
                            coroutineScope.launch {
                                pageState.scrollToPage(2)
                            }
                        }
                    ) {
                        CupertinoText(
                            strings.countries,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.W600
                            ),
                            color = Color(0xFF0F1E3C)
                        )
                    }

                }

                HorizontalPager(
                    pageState,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.wrapContentHeight()
                        .fillMaxWidth()
                ) { index ->
                    when (index) {
                        1 -> {
                            CategoryScreen(navHostController)
                        }

                        2 -> {
                            SelectLocation(navHostController)
                        }

                        else -> {
                            Shops(
                                navHostController = navHostController
                            )
                        }
                    }
                }
            }
        }

    }


}