package com.komekci.marketplace.features.profile.presentation.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.features.home.presentation.ui.SelectLanguage
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.ui.app.ImageLoader
import com.komekci.marketplace.ui.navigation.Routes
import com.primex.core.plus
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffold
import io.github.alexzhirkevich.cupertino.CupertinoBottomSheetScaffoldDefaults
import io.github.alexzhirkevich.cupertino.CupertinoIcon
import io.github.alexzhirkevich.cupertino.CupertinoIconButton
import io.github.alexzhirkevich.cupertino.CupertinoNavigationTitle
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextField
import io.github.alexzhirkevich.cupertino.CupertinoSearchTextFieldDefaults
import io.github.alexzhirkevich.cupertino.CupertinoText
import io.github.alexzhirkevich.cupertino.CupertinoTopAppBar
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import io.github.alexzhirkevich.cupertino.PresentationDetent
import io.github.alexzhirkevich.cupertino.PresentationStyle
import io.github.alexzhirkevich.cupertino.icons.CupertinoIcons
import io.github.alexzhirkevich.cupertino.icons.outlined.Mic
import io.github.alexzhirkevich.cupertino.rememberCupertinoBottomSheetScaffoldState
import io.github.alexzhirkevich.cupertino.rememberCupertinoSearchTextFieldState
import io.github.alexzhirkevich.cupertino.rememberCupertinoSheetState
import io.github.alexzhirkevich.cupertino.section.ProvideSectionStyle
import io.github.alexzhirkevich.cupertino.section.SectionStyle

@OptIn(ExperimentalCupertinoApi::class)
@Preview(showSystemUi = true)
@Composable
fun ProfileScreen(
    navHostController: NavHostController = rememberNavController(),
    userViewModel: ProfileViewModel = hiltViewModel()
) {
    val strings = LocalStrings.current
    val openLanguage = remember {
        mutableStateOf(false)
    }
    val localUserState = userViewModel.profile.collectAsState()

    val isLogout = remember {
        mutableStateOf(localUserState.value == null || localUserState.value?.token.isNullOrEmpty())
    }

    val globalRoute = LocalRouteState.current

    LaunchedEffect(localUserState.value) {
        isLogout.value = localUserState.value == null || localUserState.value?.token.isNullOrEmpty()
    }

    val storeViewModel: StoreViewModel = hiltViewModel()


    val storeState = storeViewModel.myStore.collectAsState()

    LaunchedEffect(true) {
        storeViewModel.getMyStore()
        userViewModel.initProfile()
    }

    SelectLanguage(open = openLanguage.value) {
        openLanguage.value = false
    }


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
                    CupertinoText(strings.profile)
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
                        strings.profile, style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.W700,
                            fontSize = 34.sp
                        ),
                        color = Color(0xFF0F1E3C)
                    )
                }
                var searchValue by remember {
                    mutableStateOf("")
                }
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
                    onValueChange = {
                        searchValue = it
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
                    state = searchState,
                    paddingValues = CupertinoSearchTextFieldDefaults.PaddingValues.plus(
                        PaddingValues(bottom = 12.dp)
                    )
                )

                Spacer(Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {


                    if (isLogout.value) {
                        NoProfile(Modifier.fillMaxWidth().clickable {
                            globalRoute.value = globalRoute.value.copy(
                                mainRoute = Routes.WelcomeScreen
                            )
                        })
                    } else {
                        AvatarItem(
                            viewModel = userViewModel,
                            urlImage = localUserState.value?.image.orEmpty(),
                            username = localUserState.value?.username.orEmpty(),
                            phone = localUserState.value?.phone.orEmpty()
                        )
                    }


                   if(isLogout.value.not()) {
                       Column(
                           Modifier
                               .fillMaxWidth()
                               .background(
                                   Color(0xFF767680).copy(alpha = 0.12f),
                                   RoundedCornerShape(14.dp)
                               )
                               .padding(horizontal = 16.dp, vertical = 8.dp),
                           verticalArrangement = Arrangement.spacedBy(16.dp)
                       ) {
                           if(storeState.value.data!=null) {
                               ProfileItem(
                                   title = strings.myStore,
                                   icon = R.drawable.new_store,
                                   color = Color(0xFFFF9500)
                               ) {
                                   navHostController.navigate(Routes.MyStore.replace("{start}", Routes.Promocode))
                               }
                           } else {
                               ProfileItem(
                                   title = strings.createShop,
                                   icon = R.drawable.new_store,
                                   color = Color(0xFFFF9500)
                               ) {
                                   navHostController.navigate(Routes.MyStore.replace("{start}", Routes.SelectPaymentType))
                               }
                           }



                           ProfileItem(
                               title = strings.favorites,
                               icon = R.drawable.new_favorite,
                               color = Color(0xFFC62A2F)
                           ) {
                               navHostController.navigate(Routes.Favs)
                           }


                           ProfileItem(
                               title = strings.myPayments,
                               icon = R.drawable.new_payment,
                               color = Color(0xFF34C759)
                           ) {
                               navHostController.navigate(Routes.Payments)
                           }
                           ProfileItem(
                               title = strings.myAddress,
                               icon = R.drawable.new_address,
                               color = Color(0xFF005DC3)
                           ) {
                               navHostController.navigate(Routes.Address)
                           }
                       }
                   }

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .background(
                                Color(0xFF767680).copy(alpha = 0.12f),
                                RoundedCornerShape(14.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ProfileItem(
                            title = strings.myOrders,
                            icon = R.drawable.new_order,
                            color = Color(0xFF007AFF)
                        ) {
                            navHostController.navigate(Routes.Orders)
                        }

                        ProfileItem(
                            title = strings.termsOfUse,
                            icon = R.drawable.new_about,
                            color = Color(0xFF007AFF)
                        ) {
                            navHostController.navigate(Routes.TermsOfUse)
                        }



                        ProfileItem(
                            title = strings.language,
                            icon = R.drawable.new_language,
                            color = Color(0xFF34C759)
                        ) {
                            openLanguage.value = true
                        }

                       if(isLogout.value.not()) {
                           ProfileItem(
                               title = strings.logout,
                               icon = R.drawable.new_logout,
                               color = Color(0xFFFF3B30)
                           ) {
                               userViewModel.logout()
                           }
                       }

                    }


                    Spacer(Modifier.height(22.dp))


                }
            }
        }
    }


}

@Composable
fun AvatarItem(
    modifier: Modifier = Modifier,
    urlImage: String,
    username: String,
    phone: String,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    Log.e("IMAGE", urlImage)
    val openEdit = remember {
        mutableStateOf(false)
    }
    EditProfileDialog(
        viewModel = viewModel,
        open = openEdit.value,
        onClose = {
            openEdit.value = false
        }
    )
    Row(
        modifier
            .fillMaxWidth()
            .background(Color(0xFF767680).copy(alpha = 0.12f), RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ImageLoader(
            url = urlImage,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = username,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 22.sp, fontWeight = FontWeight.W400
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = Color(0xFF0F1E3C)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "+993 ${phone}", style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.W400,
                    fontSize = 13.sp
                ), overflow = TextOverflow.Ellipsis, maxLines = 1, color = Color(0xFF64748B)
            )
        }

        IconButton(onClick = { openEdit.value = true }) {
            Icon(painter = painterResource(id = R.drawable.pencilsimple), contentDescription = null)
        }
    }
}

@Composable
fun NoProfile(
    modifier: Modifier = Modifier
) {
    val strings = LocalStrings.current
    Row(
        modifier
            .fillMaxWidth()
            .background(Color(0xFF767680).copy(alpha = 0.12f), RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.account_placeholder),
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape),
            contentDescription = "account"
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = strings.createNewAccount,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 22.sp, fontWeight = FontWeight.W400
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = Color(0xFF0F1E3C)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = strings.address, style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.W400,
                    fontSize = 13.sp
                ), overflow = TextOverflow.Ellipsis, maxLines = 1, color = Color(0xFF64748B)
            )
        }
    }
}

@Composable
fun ProfileItem(
    modifier: Modifier = Modifier,
    title: String,
    icon: Int,
    color: Color = Color.Blue,
    onClick: () -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .clickable { onClick() }, verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                Modifier
                    .size(30.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(7.dp)
                    ), contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 17.sp,
                    fontWeight = FontWeight.W400
                ),
                color = Color(0xFF0F1E3C),
                modifier = Modifier.weight(1f)
            )


        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.width(36.dp))
            HorizontalDivider(
                color = Color(0xFFAEAEB1)
            )
        }

    }
}