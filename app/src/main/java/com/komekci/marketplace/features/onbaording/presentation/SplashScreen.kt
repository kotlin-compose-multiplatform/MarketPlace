package com.komekci.marketplace.features.onbaording.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.auth.presentation.state.CountryState
import com.komekci.marketplace.features.auth.presentation.ui.TreeSelect
import com.komekci.marketplace.features.auth.presentation.viewmodel.CreateAccountViewModel
import com.komekci.marketplace.features.auth.presentation.viewmodel.UserViewModel
import com.komekci.marketplace.features.home.presentation.state.LocationStata
import com.komekci.marketplace.features.home.presentation.viewmodel.LocationViewModel
import com.komekci.marketplace.state.LocalGuestId
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.theme.darkGreen
import com.komekci.marketplace.ui.theme.tekoFontFamily
import io.github.alexzhirkevich.cupertino.CupertinoActivityIndicator
import io.github.alexzhirkevich.cupertino.ExperimentalCupertinoApi
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onSkipClick: () -> Unit
) {
    val strings = LocalStrings.current
    Box(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.align(Alignment.Center)) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_full),
                    contentDescription = null,
                    modifier = Modifier.size(162.dp, 44.dp)
                )
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(0.9f)
                    .padding(bottom = 22.dp)
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
                    .padding(22.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onLoginClick, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                    ),
                    border = BorderStroke(1.dp, Color(0x66008A0C)),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = strings.signIn,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Button(
                    onClick = onRegisterClick, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = strings.createAccount,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = strings.skipAuth,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable {
                        onSkipClick()
                    }
                )

                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSelectSheet(
    open: Boolean = false,
    locations: CountryState,
    onClose: (String?, String?)-> Unit
) {
    if(open) {
        val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(0.dp),
            sheetState = state,
            onDismissRequest = {
                onClose(null, null)
            }
        ) {
            Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = LocalStrings.current.countries,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                if(locations.isLoading) {
                    AppLoading(Modifier.fillMaxSize())
                } else if(locations.error.isNullOrEmpty().not()) {
                    AppError(Modifier.fillMaxSize()) {
                        onClose(null, null)
                    }
                } else {
                    if(locations.countries.isNotEmpty()) {
                        val selectedCountry = remember {
                            mutableStateOf("0")
                        }
                        val selectedRegion = remember {
                            mutableStateOf("0")
                        }
                        TreeSelect(
                            countries = locations.countries,
                            onCountrySelect = { c->
                                selectedCountry.value = c.id.toString()
                            },
                            onRegionSelect = { r->
                                selectedRegion.value = r.id.toString()
                                onClose(selectedCountry.value, r.id.toString())
                            }
                        )
                    } else {
                        onClose(null, null)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCupertinoApi::class)
@Composable
fun SplashScreen(
    onDone: () -> Unit
) {
    val userViewModel: UserViewModel = hiltViewModel()
    val locationViewModel: CreateAccountViewModel = hiltViewModel()
    val locations = locationViewModel.countryState
    val open = remember {
        mutableStateOf(false)
    }
    LocationSelectSheet(
        open = open.value,
        locations = locations.value,
        onClose = { c, r ->
            open.value = false
            if(c.isNullOrEmpty().not() && r.isNullOrEmpty().not()) {
                userViewModel.saveLocation(c.toString(), r.toString())
                onDone()
            } else {
                onDone()
            }
        }
    )

    LaunchedEffect(true) {
        val isSelected = userViewModel.isLocationSelected()
        if(isSelected) {
            delay(4000L)
            onDone()
        } else {
            locationViewModel.getAllCountries()
            open.value = true
        }
    }
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.spacedBy(47.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(painter = painterResource(id = R.drawable.splash), contentDescription = null)
                Image(
                    painter = painterResource(id = R.drawable.logo_full),
                    contentDescription = null,
                    modifier = Modifier
                        .width(162.dp)
                        .height(44.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                verticalArrangement = Arrangement.spacedBy(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CupertinoActivityIndicator()
                Text(
                    text = LocalStrings.current.allStoreHere,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W300,
                        fontFamily = tekoFontFamily
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
}

@Composable
fun Onboarding(
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        TopShapes()
        Box(modifier = Modifier.align(Alignment.Center)) {
            content()
        }
        BottomShapes(modifier = Modifier.align(Alignment.BottomStart))
    }
}


@Composable
fun TopShapes() {
    Box(
        modifier = Modifier
            .size(355.dp)
            .offset(x = (-136).dp, y = (-241).dp)
            .rotate(20f)
            .background(Color(0xFF338E4C), RoundedCornerShape(10.dp))
    ) {

    }
    Box(
        modifier = Modifier
            .size(355.dp)
            .offset(x = (-221).dp, y = (-241).dp)
            .rotate(20f)
            .background(darkGreen, RoundedCornerShape(10.dp))
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
    ) {

    }
}

@Composable
fun BottomShapes(modifier: Modifier) {

    Box(
        modifier
            .width(355.dp)
            .height(355.dp)
            .offset(y = 230.dp, x = (-140).dp)
            .rotate(40f)
            .background(darkGreen, RoundedCornerShape(10.dp))
            .background(Color.Black.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
    ) {

    }


    Box(
        modifier
            .fillMaxWidth()
            .height(355.dp)
            .offset(y = 230.dp)
            .rotate(40f)
            .background(Color(0xFF338E4C), RoundedCornerShape(10.dp))
    ) {

    }
}