package com.komekci.marketplace.features.create_store.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.features.profile.presentation.ui.ProfileItem
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.ImageLoader
import com.komekci.marketplace.ui.navigation.Routes

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CreateStore(
    storeViewModel: StoreViewModel,
    profileNavHostController: NavHostController = rememberNavController(),
    navHostController: NavHostController = rememberNavController(),
    required: String = "0"
) {
    val strings = LocalStrings.current
    LaunchedEffect(true) {
        storeViewModel.initMyStore()
    }
    val globalRoute = LocalRouteState.current
    val state = storeViewModel.myStore.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color(0xFF1A1A1A),
                modifier = Modifier
                    .size(34.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .clickable { profileNavHostController.navigateUp() }
            )

            Text(
                text = strings.createStore, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W600
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imeNestedScroll()
        ) {
            if (state.value.loading) {
                AppLoading(modifier = Modifier.fillMaxSize())
            } else if (state.value.error.isNullOrEmpty()
                    .not() || state.value.messages.isNullOrEmpty().not()
            ) {
                CreateStoreWithKey(
                    storeViewModel = storeViewModel,
                    onBack = { profileNavHostController.navigateUp() })
            } else if (state.value.data == null) {
                CreateStoreWithKey(
                    storeViewModel = storeViewModel,
                    onBack = { profileNavHostController.navigateUp() })
            } else {
                state.value.data?.let { store ->
                    if (state.value.isPayed().not() && required == "1") {
                        CreateStoreWithKey(
                            storeViewModel = storeViewModel,
                            onBack = { profileNavHostController.navigateUp() })
                    } else {

                        if(state.value.isPayed().not()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                                    .background(Color(0x0FFF0101), RoundedCornerShape(12.dp))
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.info),
                                    contentDescription = null,
                                    tint = Color(
                                        0xD4BB0007
                                    ),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = strings.payForStoreCreate,
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                                    color = Color(0xD4BB0007)
                                )

                                Button(
                                    onClick = {
                                        navHostController.navigate(Routes.SelectPaymentType2) {
                                            popUpTo(Routes.Promocode) {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(4.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFE5484D)
                                    )
                                ) {
                                    Text(
                                        text = strings.pay,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                                        color = Color.White
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        StoreAvatar(
                            image = store.image?:"",
                            name = store.name?:"",
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            navHostController.navigate(Routes.AboutStore)
                        }

                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(top = 12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            ProfileItem(
                                title = strings.myProducts,
                                icon = R.drawable.outline_shopping_bag_24,
                                color = Color(0xFF007AFF)
                            ) {
                                navHostController.navigate(Routes.MyProducts)
                            }
                            ProfileItem(
                                title = strings.myAddress,
                                icon = R.drawable.files_bold_svgrepo_com,
                                color = Color(0xFF005DC3)
                            ) {
                                navHostController.navigate(Routes.Address)
                            }
                            ProfileItem(
                                title = strings.myPayments,
                                icon = R.drawable.wallet_svgrepo_com,
                                color = Color(0xFFC62A2F)
                            ) {
                                navHostController.navigate(Routes.Payments)
                            }
                            ProfileItem(title = strings.myOrders, icon = R.drawable.briefcasemetal, color =  Color(0xFF34C759)) {
                                navHostController.navigate(Routes.Orders)
                            }
                            ProfileItem(
                                title = strings.chats,
                                icon = R.drawable.chatteardropdots,
                                color = Color(0xFFFF3B30)
                            ) {
                                navHostController.navigate(Routes.StoreChats)
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                        }

                    }


                }
            }
        }
    }
}

@Composable
private fun StoreAvatar(
    image: String,
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val strings = LocalStrings.current

    Row(
        modifier
            .fillMaxWidth()
            .background(Color(0xFFF6F6F6), RoundedCornerShape(6.dp))
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ImageLoader(
            url = image,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp, fontWeight = FontWeight.W600
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = Color(0xFF1A1A1A)
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .border(1.dp, Color(0x2B01062F), RoundedCornerShape(4.dp))
                    .background(Color.White, RoundedCornerShape(4.dp))
                    .clickable { }
                    .padding(9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color(0xFFD93D42),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = strings.likeCount,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                    color = Color(
                        0x4700082F
                    )
                )
            }
        }

        IconButton(onClick = onClick) {
            Icon(painter = painterResource(id = R.drawable.pencilsimple), contentDescription = null)
        }
    }
}

