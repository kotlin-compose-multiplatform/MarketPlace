package com.komekci.marketplace.features.home.presentation.ui.help

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.state.LocalRouteState

@Preview(showSystemUi = true)
@Composable
fun KomekciScreen(navHostController: NavHostController = rememberNavController()) {
    val strings = LocalStrings.current
    val globalRoute = LocalRouteState.current
    val openDialog = remember {
        mutableStateOf(false)
    }
    val screen = rememberSaveable {
        mutableStateOf("")
    }
    if (openDialog.value) {
        SelectBank(
            open = openDialog.value,
            onNext = {
                screen.value = "code"
            }
        ) {
            openDialog.value = false
        }
    }
    fun back() {
        if (screen.value == "code") {
            screen.value = ""
        } else {
            navHostController.navigateUp()
        }
    }
    BackHandler(screen.value.isNotEmpty()) {
        back()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = {
                back()
            }, modifier = Modifier.align(Alignment.TopStart)) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color(0xFF1A1A1A),
                    modifier = Modifier.size(34.dp)
                )
            }

            Text(
                text = strings.helper, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W700
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Column(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(32.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.logo_full),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
        AnimatedContent(targetState = screen.value, label = "") {
            if (it.isEmpty()) {
                Column {
                    Text(
                        text = strings.pay,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W700
                        ),
                        color = Color(0xFF2F313F)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = strings.payDescription,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                        color = Color(0xFF667085)
                    )
                    Spacer(modifier = Modifier.height(22.dp))
//                    Button(
//                        onClick = { openDialog.value = true },
//                        shape = RoundedCornerShape(6.dp),
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(
//                            text = strings.payWithCard,
//                            color = Color.White,
//                            style = MaterialTheme.typography.bodyLarge.copy(
//                                fontSize = 16.sp,
//                                fontWeight = FontWeight.W500
//                            )
//                        )
//                    }
//                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                                  screen.value = "key"
                        },
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x0D05C005)
                        ),
                        border = BorderStroke(1.dp, Color(0x99008D1A))
                    ) {
                        Text(
                            text = strings.enterPayCode,
                            color = Color(0xD6006316),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W500
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

//                    TextButton(onClick = {
//                        globalRoute.value = globalRoute.value.copy(
//                            homeRoute = Routes.Chats,
//                            chatRoute = Routes.OnChat
//                        )
//                    }, modifier = Modifier.fillMaxWidth()) {
//                        Text(
//                            text = strings.writeMessageKomekci,
//                            color = Color(0xFF338E4C),
//                            style = MaterialTheme.typography.bodyMedium.copy(
//                                fontWeight = FontWeight.W700
//                            )
//                        )
//                    }

                    Spacer(modifier = Modifier.height(22.dp))
                }
            } else if (it == "code") {
                EnterCardCode()
            } else if(it == "key") {
                EnterKey()
            }
        }

    }

}