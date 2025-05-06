package com.komekci.marketplace.features.product.presentation.ui.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.chat.presentation.viewmodel.ChatsViewModel
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.product.domain.model.ProductsEntity
import com.komekci.marketplace.features.product.presentation.viewmodel.CategoryViewModel
import com.komekci.marketplace.state.LocalFavSettings
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.CheckAuthScreen
import com.komekci.marketplace.ui.app.FavoriteButton
import com.komekci.marketplace.ui.app.ImageLoader
import com.komekci.marketplace.ui.app.LoadingButton
import com.komekci.marketplace.ui.app.LoadingOutlinedButton
import com.komekci.marketplace.ui.navigation.Routes
import kotlinx.coroutines.launch
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DetailsScreen(
    id: String? = null,
    productsEntity: ProductsEntity? = null,
    navHostController: NavHostController = rememberNavController()
) {
    val viewModel: CategoryViewModel = hiltViewModel()
    val state = viewModel.singleProduct.collectAsState()
    val chatViewModel: ChatsViewModel = hiltViewModel()
    val createChatState = chatViewModel.createState.collectAsState()
    val globalRoute = LocalRouteState.current
    val appSettingsState = LocalSettings.current
    val favSettings = LocalFavSettings.current
    LaunchedEffect(id) {
        println(id)
        viewModel.initSingleProduct(id ?: "")
    }

    if (state.value.loading || state.value.error.isNullOrEmpty().not()) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0x3DD6D6D6))
                    .size(40.dp)
                    .clickable {
                        navHostController.navigateUp()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null,
                    tint = Color(0xFF1A1A1A),
                    modifier = Modifier.size(25.dp)
                )
            }

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0x3DD6D6D6))
                    .size(40.dp)
                    .clickable {
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = Color(0xFF1A1A1A),
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }

    if (state.value.loading) {
        AppLoading(modifier = Modifier.fillMaxSize())
    } else if (state.value.error.isNullOrEmpty().not()) {
        AppError(modifier = Modifier.fillMaxSize()) {
            viewModel.getProductById(id ?: "")
        }
    } else {
        state.value.products?.let { product ->

            val pagerState = rememberPagerState(pageCount = {
                product.image?.size ?: 0
            })
            val listState = rememberLazyListState()
            val coroutineScope = rememberCoroutineScope()
            val strings = LocalStrings.current
            val openPhotoView = remember {
                mutableStateOf(false)
            }



            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect { page ->
                    listState.animateScrollToItem(page)
                }
            }
            PhotoViewDialog(
                open = openPhotoView.value,
                onClose = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(it)
                    }
                    openPhotoView.value = false
                },
                images = product.image ?: emptyList(),
                selectedIndex = pagerState.currentPage
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White
                    ),
                    title = {
                        Text(
                            text = translateValue(instance = product, property = "name"),
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
                                navHostController.navigateUp()
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

                Box(Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(80f)
                                .height(310.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFF182E5C).copy(0.07f),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .padding(8.dp)
                        ) {
                            HorizontalPager(state = pagerState, contentPadding = PaddingValues(16.dp)) {
                                ImageLoader(
                                    url = product.image?.get(it) ?: "",
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clickable {
                                            openPhotoView.value = true
                                        }
                                        .zoomable(rememberZoomState())
                                )
                            }


                                CheckAuthScreen(
                                    successContent = {
                                        FavoriteButton(
                                            id = product.id,
                                            size = 40.dp,
                                            shape = RoundedCornerShape(6.dp),
                                            type = FavoriteType.PRODUCT,
                                            modifier = Modifier.align(Alignment.TopEnd).background(
                                                Color.Black.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(6.dp)
                                            )
                                        ) {
                                            println("LIKE-0: ${product.id}")
                                            favSettings.value.likeProduct(
                                                product.id,
                                                FavoriteType.PRODUCT
                                            )
                                        }
                                    },
                                    errorContent = {

                                    }
                                )


                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Box(
                                    Modifier
                                        .width(28.dp)
                                        .height(68.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 8.dp,
                                                bottomStart = 8.dp
                                            )
                                        )
                                        .background(
                                            shape = RoundedCornerShape(
                                                topStart = 8.dp,
                                                bottomStart = 8.dp
                                            ),
                                            color = Color.Black.copy(alpha = 0.3f)
                                        )
                                        .clickable {
                                            coroutineScope.launch {
                                                if(pagerState.currentPage>0) {
                                                    pagerState.animateScrollToPage(pagerState.currentPage-1)
                                                }
                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Default.KeyboardArrowLeft,
                                        contentDescription = "prev",
                                        tint = Color.White
                                    )
                                }

                                Box(
                                    Modifier
                                        .width(28.dp)
                                        .height(68.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topEnd = 8.dp,
                                                bottomEnd = 8.dp
                                            )
                                        )
                                        .background(
                                            shape = RoundedCornerShape(
                                                topEnd = 8.dp,
                                                bottomEnd = 8.dp
                                            ),
                                            color = Color.Black.copy(alpha = 0.3f)
                                        )
                                        .clickable {
                                            coroutineScope.launch {
                                                    pagerState.animateScrollToPage(pagerState.currentPage+1)

                                            }
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Default.KeyboardArrowRight,
                                        contentDescription = "next",
                                        tint = Color.White
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .defaultMinSize(minWidth = 60.dp)
                                    .background(Color(0xFFDE2622), RoundedCornerShape(6.dp))
                                    .padding(vertical = 15.dp, horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${product.discount}%",
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                                    color = Color.White,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        LazyColumn(
                            state = listState,
                            modifier = Modifier.weight(20f).height(310.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(product.image?.size ?: 0) {
                                SmallPhoto(
                                    modifier = Modifier.width(86.dp).height(103.dp),
                                    url = product.image?.get(it) ?: "",
                                    selected = pagerState.currentPage == it
                                ) {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(it)
                                    }
                                }
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF2F2F7),
                            shape = RoundedCornerShape(10.dp)
                        ).padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = translateValue(instance = product, property = "name"),
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700, fontSize = 18.sp),
                        color = Color(0xFF0F1E3C)
                    )
                    Text(
                        text = translateValue(instance = product, property = "description"),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF475467)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFFF2F2F7),
                            shape = RoundedCornerShape(10.dp)
                        ).padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    ProductMainItem(text = strings.brand, value = "${product.brandName}")
                    ProductMainItem(text = "${strings.shop}:", value = "${product.shopName}")
                    ProductMainItem(text = strings.productCode, value = "@${product.code}")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.defaultMinSize(minWidth = 120.dp),
                            text = strings.productPrice,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
                            color = Color(0xFF475467)
                        )

                        Spacer(Modifier.width(40.dp))

                        Text(
                            text = "${product.price} TMT",
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                            color = Color(0xFF101828)
                        )
                        Spacer(Modifier.width(8.dp))
                        if(product.discount>0) {
                            Text(
                                text = "${product.oldPrice} TMT",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                                color = Color(0xFFDE2622),
                                modifier = Modifier,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }



                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                   CheckAuthScreen(
                       successContent = {
                           LoadingOutlinedButton(
                               loading = createChatState.value.loading,
                               onClick = {
                                   chatViewModel.createChat(product.shopId ?: 0) {
                                       globalRoute.value = globalRoute.value.copy(
                                           homeRoute = Routes.Chats,
                                           chatRoomId = it
                                       )
                                   }
                               },
                               shape = MaterialTheme.shapes.small,
                               border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary),
                               modifier = Modifier
                                   .height(60.dp)
                                   .weight(1f)
                           ) {
                               Icon(
                                   painter = painterResource(id = R.drawable.chat_second),
                                   contentDescription = null,
                                   tint = MaterialTheme.colorScheme.primary
                               )
                               Spacer(modifier = Modifier.width(8.dp))
                               Text(
                                   text = strings.writeMessage,
                                   style = MaterialTheme.typography.bodyLarge.copy(
                                       fontWeight = FontWeight.W500
                                   ),
                                   maxLines = 1,
                                   overflow = TextOverflow.Ellipsis,
                                   color = MaterialTheme.colorScheme.primary
                               )
                           }
                       },
                       errorContent = {

                       }
                   )


                    BasketButton(
                        text = strings.addToBasket,
                        modifier = Modifier.weight(1f),
                        initialCount = 0,
                        productsEntity = product,
                        viewModel = viewModel
                    )



                }

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}

@Composable
fun ProductMainItem(
    modifier: Modifier = Modifier,
    text: String,
    value: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        Text(
            modifier = Modifier.defaultMinSize(minWidth = 120.dp),
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
            color = Color(0xFF475467)
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
            color = Color(0xFF101828)
        )
    }
}

@Composable
fun BasketButton(
    modifier: Modifier = Modifier,
    text: String,
    initialCount: Int = 0,
    productsEntity: ProductsEntity,
    viewModel: CategoryViewModel
) {
    val count = remember {
        mutableIntStateOf(initialCount)
    }

    LaunchedEffect(productsEntity.id) {
        viewModel.getBasketCount(productsEntity.id) {
            count.intValue = it
        }
    }

    fun change(count: Int) {
        viewModel.changeCountBasket(count, productsEntity, onChange = {

        })
    }

    if (count.intValue > 0) {
        Button(
            onClick = { /*TODO*/ },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF4F4F5)
            ),
            modifier = modifier
                .height(60.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Icon(
                    painterResource(id = R.drawable.baseline_remove_24),
                    contentDescription = null,
                    tint = Color(0xFFA1A1AA),
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable {
                            count.intValue = count.intValue.minus(1)
                            change(-1)
                        }
                )
                Text(
                    text = count.intValue.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.W500
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color(0xFF3F3F46)
                )
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = Color(0xD6006316),
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .clickable {
                            count.intValue = count.intValue.plus(1)
                            change(1)
                        }
                )
            }
        }
    } else {
        Button(
            onClick = {
                count.intValue = 1
                change(1)
            },
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            modifier = modifier
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.basket),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W500
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White
            )
        }
    }
}