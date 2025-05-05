package com.komekci.marketplace

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.lyricist.Lyricist
import com.komekci.marketplace.core.locale.Locales
import com.komekci.marketplace.core.locale.Strings
import com.komekci.marketplace.features.auth.data.local.AppLanguage
import com.komekci.marketplace.features.auth.data.local.UserDataStore
import com.komekci.marketplace.features.auth.presentation.viewmodel.UserViewModel
import com.komekci.marketplace.features.product.data.entity.ProductRequest
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel
import com.komekci.marketplace.state.AppSettingsState
import com.komekci.marketplace.state.FavSettings
import com.komekci.marketplace.state.LocalFavSettings
import com.komekci.marketplace.state.LocalGuestId
import com.komekci.marketplace.state.LocalProductFilter
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.state.LocalSettings
import com.komekci.marketplace.state.RouteGlobalState
import com.komekci.marketplace.state.SetAppSettings
import com.komekci.marketplace.ui.app.AppScreen
import com.komekci.marketplace.ui.theme.MarketPlaceTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var lyricist: Lyricist<Strings>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MarketPlaceTheme {
                val viewModel: UserDataStore = UserDataStore(this)
                val coroutine = rememberCoroutineScope()
                val language = remember {
                    mutableStateOf(Locales.TK)
                }

                LaunchedEffect(true) {
                    coroutine.launch {
                        language.value = viewModel.getLanguage()
                        Log.e("LANGUAGE", viewModel.getLanguage())
                    }
                }
                val settings = remember(language.value) {
                    mutableStateOf(AppSettingsState(languageTag = language.value))
                }
                LaunchedEffect(settings.value) {
                    Log.e("LANGUAGE TAG", settings.value.languageTag)
                    lyricist.languageTag = settings.value.languageTag
                    coroutine.launch {
                        viewModel.saveLanguage(settings.value.languageTag)
                    }
                }

                lyricist = rememberStrings(
                    defaultLanguageTag = language.value,
                    currentLanguageTag = language.value
                )

                val userViewModel: UserViewModel = hiltViewModel()
                val guestId = userViewModel.guestId


                ProvideStrings(lyricist) {
                    CompositionLocalProvider(
                        LocalSettings provides settings.value,
                        SetAppSettings provides {
                            settings.value = it
                        },
                        LocalRouteState provides remember {
                            mutableStateOf(RouteGlobalState())
                        },
                        LocalProductFilter provides remember {
                            mutableStateOf(ProductRequest())
                        },
                        LocalFavSettings provides remember {
                            mutableStateOf(FavSettings())
                        },
                        LocalGuestId provides remember(guestId.value) {
                            mutableStateOf(guestId.value)
                        }
                    ) {
                        Log.e("LANGUAGE 2", lyricist.languageTag)
                        AppScreen()
                    }
                }
            }
        }
    }
}

/*
1) Filter products -
3) Store orders
4) Order details
6) Home page clicks -
8) Store categories
9) Fix pagination
 */


/*
Display Large - Roboto 57/64 -0.25
Display Medium - Roboto 45/52 .  0
Display Small - Roboto 36/44 . 0

Headline Large - Roboto 32/40 . 0
Headline Medium - Roboto 28/36 . 0
Headline Small - Roboto 24/32 . 0

Title Large - Roboto Regular 22/28 . 0
Title Medium - Roboto Medium 16/24 . +0.15
Title Small - Roboto Medium 14/20 . +0.1

Label Large - Roboto Medium 14/20 . +0.1
Label Medium - Roboto Medium 12/16 . +0.5
Label Small - Roboto Medium 11/16 . +0.5

Body Large - Roboto 16/24 . +0.5
Body Medium - Roboto 14/20 . +0.25
Body Small - Roboto 12/16 . +0.4
 */