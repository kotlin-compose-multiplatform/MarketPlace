package com.komekci.marketplace.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.features.auth.presentation.ui.CreateAccountScreen
import com.komekci.marketplace.features.auth.presentation.ui.LoginScreen
import com.komekci.marketplace.features.auth.presentation.viewmodel.UserViewModel
import com.komekci.marketplace.features.main.presentation.ui.HomeScreen
import com.komekci.marketplace.features.onbaording.presentation.SplashScreen
import com.komekci.marketplace.features.onbaording.presentation.WelcomeScreen
import com.komekci.marketplace.state.LocalRouteState

@Composable
fun RootNavigation(){
    val rootNavController = rememberNavController()
    val userViewModel: UserViewModel = hiltViewModel()
    val localUserState = userViewModel.localUser.collectAsState()
    val globalRoute = LocalRouteState.current



    LaunchedEffect(globalRoute.value.mainRoute) {
        rootNavController.navigate(globalRoute.value.mainRoute) {
            popUpTo(Routes.Main) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }


    LaunchedEffect(true) {
        userViewModel.getUserData {

        }
    }

    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()


    LaunchedEffect(navBackStackEntry?.destination?.route) {
        navBackStackEntry?.destination?.route?.let {
            globalRoute.value = globalRoute.value.copy(
                mainRoute = it
            )
        }
    }

    NavHost(navController = rootNavController, startDestination = Routes.OnBoarding) {

        navigation(
            route = Routes.OnBoarding,
            startDestination = Routes.SplashScreen
        ) {

            composable(Routes.SplashScreen) {
                val nextRoute = remember {
                    mutableStateOf<String?>("")
                }
                LaunchedEffect(localUserState.value) {
                    Log.e("SKIP", (localUserState.value?.skipAuth).toString())

                    nextRoute.value = localUserState.value?.let {
                        Log.e("TAG","One: "+it)
                        if(it.skipAuth=="1" || it.isFirstLaunch=="0" || it.token.isNullOrEmpty().not()) {
                            Routes.Main
                        } else {
                            Routes.Main
                        }
                    }
                }
                SplashScreen {
                    rootNavController.navigate(nextRoute.value?:Routes.WelcomeScreen) {
                        popUpTo(Routes.SplashScreen) {
                            inclusive = true
                        }
                    }
                }
            }

            composable(Routes.WelcomeScreen) {
                WelcomeScreen(
                    onLoginClick = {
                        rootNavController.navigate(Routes.LoginScreen)
                    },
                    onRegisterClick = {
                        rootNavController.navigate(Routes.CreateAccountScreen)
                    },
                    onSkipClick = {
                        userViewModel.changeSkipAuth("1")
                        rootNavController.navigate(Routes.Main) {
                            popUpTo(0)
                        }
                    }
                )
            }
        }

        composable(Routes.CreateAccountScreen) {
            CreateAccountScreen {
                rootNavController.navigate(Routes.Main) {
                    popUpTo(0)
                }
            }
        }

        composable(Routes.LoginScreen) {
            LoginScreen(rootNavController) {
                rootNavController.navigate(Routes.Main) {
                    popUpTo(0)
                }
            }
        }

        navigation(
            route = Routes.Main,
            startDestination = Routes.Home
        ) {
            composable(Routes.Home) {
                HomeScreen()
            }

        }


    }
}