package com.komekci.marketplace.features.home.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.home.data.entity.country.CountryRequest
import com.komekci.marketplace.features.home.data.entity.country.toProductsEntity
import com.komekci.marketplace.features.home.domain.model.ShopEntity
import com.komekci.marketplace.features.home.presentation.ui.category.CategoryItem
import com.komekci.marketplace.features.home.presentation.ui.shops.ShopItem
import com.komekci.marketplace.features.home.presentation.viewmodel.LocationViewModel
import com.komekci.marketplace.features.product.domain.useCase.CategoryUseCase
import com.komekci.marketplace.features.product.presentation.ui.product.ProductComponent
import com.komekci.marketplace.features.product.presentation.viewmodel.CategoryViewModel
import com.komekci.marketplace.state.LocalFavSettings
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.navigation.Routes
import com.komekci.marketplace.ui.theme.MarketPlaceTheme
import com.primex.core.VerticalGrid
import com.primex.core.plus
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldDefaults
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoIconButton
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextField
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextFieldDefaults
import io.github.alexzhirkevich.cupertino.CupertinoSegmentedControl
import io.github.alexzhirkevich.cupertino.CupertinoSegmentedControlColors
import io.github.alexzhirkevich.cupertino.CupertinoSegmentedControlDefaults
import io.github.alexzhirkevich.cupertino.CupertinoSegmentedControlIndicator
import io.github.alexzhirkevich.cupertino.CupertinoSegmentedControlTab
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.PresentationDetent
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.TabPosition
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Mic
import io.github.alexzhirkevich.cupertino.rememberCupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.rememberCupertinoSearchTextFieldState
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import io.github.alexzhirkevich.cupertino.section.ProvideSectionStyle
import io.github.alexzhirkevich.cupertino.section.SectionStyle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCupertinoApi::class)
@Composable
fun HomeProducts(
    modifier: Modifier = Modifier,
    country: String = "Turkmenistan",
    countryId: String = "",
    regionId: String = "",
    navController: NavController = rememberNavController(),
    onBack: () -> Unit = {}
) {
    val strings = LocalStrings.current
    val categoryViewModel: com.komekci.marketplace.features.home.presentation.viewmodel.CategoryViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val density = LocalDensity.current
    val locationViewModel: LocationViewModel = hiltViewModel()
    val countries = locationViewModel.state.collectAsState()
    val searchState = rememberCupertinoSearchTextFieldState(
        scrollableState = scrollState,
        blockScrollWhenFocusedAndEmpty = true
    )


    val selectedRegion = rememberSaveable {
        mutableStateOf("")
    }

    fun getData() {
        categoryViewModel.getHomeProducts(
            request = CountryRequest(
                countryId = countryId,
                regionId = selectedRegion.value
            )
        )
    }

    LaunchedEffect(selectedRegion.value) {
        getData()
    }

    val appSettingsState = LocalFavSettings.current

    val state = categoryViewModel.homeProductState.collectAsState()

    LaunchedEffect(true) {

        locationViewModel.initLocations()
    }

    val productFilter = LocalProductFilter.current

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
    CupertinoBottomSheetScaffold(
        appBarsBlurAlpha = 20f,
        appBarsBlurRadius = 20.dp,
        hasNavigationTitle = false,
        colors = CupertinoBottomSheetScaffoldDefaults.colors(
            sheetContainerColor = Color.White,
        ),
        sheetContent = {

        },
        topBar = {
            Column(Modifier.fillMaxWidth()) {
                var searchValue by rememberSaveable {
                    mutableStateOf("")
                }

                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White
                    ),
                    title = {
                        Text(
                            text = country,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.W600,
                                fontSize = 17.sp
                            ),
                            color = Color(0xFF0F1E3C)
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                onBack()
                            }
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "back",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                )

                CupertinoSearchTextField(
                    value = searchValue,
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
            }
        },
        scaffoldState = scaffoldState
    ) { pv ->
        if(state.value.loading) {
            AppLoading(Modifier.fillMaxSize())
        } else if(state.value.error) {
            AppError(Modifier.fillMaxSize()) {
                getData()
            }
        } else {
            if (state.value.data!=null) {
                val sections = state.value.data!!.products
                val stores = state.value.data!!.stores
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
                      countries.value.locations?.let { locations->
                          locations.find { it.id == countryId }?.let { c->
                              if(c.districts.isNotEmpty()) {
                                  val selectedIndex = c.districts.indexOfFirst { o-> o.id == selectedRegion.value }
                                  CupertinoScrollableSegmentedControl(
                                      paddingValues = PaddingValues(16.dp),
                                      selectedTabIndex = if(selectedIndex!=-1) selectedIndex else 0,
                                  ) {
                                      repeat(c.districts.count()) { index->
                                          val region = c.districts[index]
                                          CupertinoSegmentedControlTab(
                                              isSelected = selectedRegion.value == region.id,
                                              onClick = {
                                                  selectedRegion.value = region.id
                                              }
                                          ) {
                                              CupertinoText(
                                                  translateValue(region, "name"),
                                                  style = MaterialTheme.typography.bodySmall.copy(
                                                      fontWeight = FontWeight.W600
                                                  ),
                                                  color = Color(0xFF0F1E3C)
                                              )
                                          }


                                      }
                                  }
                              }
                          }

                      }




                       stores?.let {
                           HomeProductSectionTitle(
                               modifier = Modifier.fillMaxWidth(),
                               title = strings.shops,
                               onAllClick = {}
                           )

                           LazyRow(
                               Modifier.fillMaxWidth(),
                               contentPadding = PaddingValues(horizontal = 16.dp),
                               horizontalArrangement = Arrangement.spacedBy(12.dp)
                           ) {
                               items(stores.count()) { index->
                                   ShopItem(
                                       item = stores[index].toUiEntity(), modifier = Modifier
                                           .width(172.dp),
                                       onFavoriteClick = {
                                       }
                                   ) {
                                       productFilter.value = productFilter.value.copy(
                                           storeId = listOf(stores[index].id),
                                           categoryId = emptyList(),
                                           brandId = emptyList(),
                                           catalogId = emptyList(),
                                       )
                                       navController.navigate(stores[index].toUiEntity()) {
                                           launchSingleTop = true
                                       }
                                   }
                               }
                           }
                       }


                        sections?.let {
                            sections.filter { it.products.isNullOrEmpty() }.let { categories->
                                if(categories.isNotEmpty()) {
                                    HomeProductSectionTitle(
                                        modifier = Modifier.fillMaxWidth(),
                                        title = strings.categories,
                                        onAllClick = {}
                                    )

                                    LazyRow(
                                        Modifier.fillMaxWidth(),
                                        contentPadding = PaddingValues(horizontal = 16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        items(categories.count()) { index->
                                            CategoryItem(
                                                item = categories[index].toCategory(),
                                                modifier = Modifier
                                                    .width(103.dp).clickable {
                                                        navController.navigate(Routes.onCategory)
                                                        productFilter.value = productFilter.value.copy(
                                                            storeId = emptyList(),
                                                            categoryId = listOf(it[index].id.toString()),
                                                            catalogId = emptyList(),
                                                            brandId = emptyList()
                                                        )
                                                    },
                                                isPreview = false
                                            )
                                        }
                                    }
                                }
                            }

                            sections.filter { it.products.isNullOrEmpty().not() }.let { productSections->
                                repeat(productSections.count()) { s_index->
                                    val section = productSections[s_index]
                                    section.products?.let { products->
                                        HomeProductSectionTitle(
                                            modifier = Modifier.fillMaxWidth(),
                                            title = translateValue(section.name!!, "", ""),
                                            onAllClick = {}
                                        )

                                        LazyRow(
                                            Modifier.fillMaxWidth(),
                                            contentPadding = PaddingValues(horizontal = 16.dp),
                                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                                        ) {
                                            items(products.count()) { index->
                                                val product = products[index]
                                                ProductComponent(
                                                    item = product.toProductsEntity(),
                                                    isPreview = false,
                                                    modifier = Modifier.width(172.dp),
                                                    onFavoriteClick = {
                                                        appSettingsState.value.likeProduct(
                                                            product.id.toString(),
                                                            FavoriteType.PRODUCT
                                                        )
                                                    },
                                                    onClick = {
                                                        navController.navigate(Routes.Details.replace("{id}", product.id.toString()))
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }




                    }
                }
            } else {
                AppError(Modifier.fillMaxSize()) {
                    getData()
                }
            }
        }
    }

}


 @Preview(showSystemUi = true)
@Composable
fun HomeProductsPreview(modifier: Modifier = Modifier) {
    MarketPlaceTheme {
        HomeProducts()
    }
}

@Composable
fun HomeProductSectionTitle(
    modifier: Modifier = Modifier,
    title: String,
    onAllClick: () -> Unit
) {
    Row(
        modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.W400,
                fontSize = 16.sp
            ),
            color = Color(0xFF0F1E3C)
        )

        TextButton(
            onClick = onAllClick
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "See all",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF0F1E3C)
                )

                Icon(
                    painter = painterResource(R.drawable.outword),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = "arrow"
                )
            }
        }
    }
}


@Composable
@ExperimentalCupertinoApi
fun CupertinoScrollableSegmentedControl(
    selectedTabIndex: Int,
    modifier: Modifier = Modifier,
    colors: CupertinoSegmentedControlColors = CupertinoSegmentedControlDefaults.colors(),
    shape: Shape = CupertinoSegmentedControlDefaults.shape,
    paddingValues: PaddingValues = CupertinoSegmentedControlDefaults.PaddingValues,
    indicator: @Composable (tabPositions: List<androidx.compose.material3.TabPosition>) -> Unit = @Composable { tabPositions ->
        TabRowDefaults.SecondaryIndicator(
            Modifier
                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                .offset(y = (-4).dp)
                .clip(
                    RoundedCornerShape(5.dp)
                )
                .zIndex(-1f),
            height = 25.dp,
            color = Color.White
        )
    },
    tabs: @Composable () -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = modifier
            .padding(paddingValues)
            .clip(shape),
        containerColor = colors.containerColor,
        contentColor = colors.contentColor,
        tabs = tabs,
        indicator = indicator
    )

}

