package com.komekci.marketplace.features.auth.presentation.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.auth.presentation.viewmodel.CreateAccountViewModel
import com.komekci.marketplace.features.onbaording.presentation.TopShapes
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.ui.navigation.Routes
import com.komekci.marketplace.ui.theme.newTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, onDone: () -> Unit = {}) {
    val viewmodel: CreateAccountViewModel = hiltViewModel()
    val strings = LocalStrings.current
    val globalRoute = LocalRouteState.current
    val index = viewmodel.index
    Box(modifier = Modifier.fillMaxSize()) {
//        TopShapes()
        AnimatedContent(
            targetState = viewmodel.index.intValue, label = "", modifier = Modifier.align(
                if(index.intValue == 2) Alignment.Center else Alignment.TopCenter
            )
        ) {
            if (it == 0) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CenterAlignedTopAppBar(
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    onDone()
                                }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "back",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        title = {
                            Text(
                                text = strings.signIn,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.W600
                                ),
                                color = Color(0xFF0F1E3C)
                            )
                        }
                    )
                    Spacer(Modifier.height(33.dp))
                    Text(
                       strings.enterNameAndCountry,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 15.sp,
                        ),
                        modifier = Modifier.fillMaxWidth(0.7f),
                        textAlign = TextAlign.Center,
                        color = newTextColor
                    )
                    Spacer(Modifier.height(33.dp))
                    PhoneNumberScreen(
                        viewModel = viewmodel,
                        title = strings.signIn,
                        alternativeText = strings.noAccount,
                        handleBack = false,
                        actionText = strings.createAccount,
                        nextIndex = 1,
                        previousIndex = 0,
                        onBackClick = {
                            navController.navigateUp()
                        },
                        onActionClick = {
                            globalRoute.value = globalRoute.value.copy(
                                mainRoute = Routes.CreateAccountScreen
                            )
                        }
                    )
                }
            }
            if (it == 1) {
                ConfirmationScreen(viewmodel, nextIndex = 2, previousIndex = 0)
            }

            if (it == 2) {
                FinishScreen()
            }
        }

        if (viewmodel.index.intValue == 2) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomCenter)) {
                Button(
                    onClick = onDone, colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = strings.start,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}