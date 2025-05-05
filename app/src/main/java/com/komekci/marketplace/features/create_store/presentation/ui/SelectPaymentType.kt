package com.komekci.marketplace.features.create_store.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
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
import com.komekci.marketplace.ui.navigation.OnlinePayment
import com.komekci.marketplace.ui.navigation.Routes
import com.komekci.marketplace.ui.theme.newTextColor


enum class SelectPaymentPage {
    NEW,
    EXPIRE
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, device = "id:pixel_9")
@Composable
fun SelectPaymentType(
    profileNavHostController: NavHostController = rememberNavController(),
    navHostController: NavHostController = rememberNavController(),
    type: SelectPaymentPage = SelectPaymentPage.NEW
) {
    val strings = LocalStrings.current
    Column(
        Modifier
            .fillMaxSize()
            .background(
                color = Color.White
            ), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        CenterAlignedTopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = {
                        profileNavHostController.navigateUp()
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
                    text = strings.createStore,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.W600
                    ),
                    color = Color(0xFF0F1E3C)
                )
            }
        )

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 16.dp
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.threedhand),
                contentDescription = "logo",
                modifier = Modifier.size(
                    width = 191.dp,
                    height = 212.dp
                )
            )

            Spacer(Modifier.height(16.dp))

            Text(
                strings.beforeCreateStore,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 15.sp,
                ),
                modifier = Modifier.fillMaxWidth(0.7f),
                textAlign = TextAlign.Center,
                color = newTextColor
            )

            PaymentTypeButton(
                modifier = Modifier.fillMaxWidth(),
                icon = painterResource(R.drawable.online),
                title = "Online",
                onClick = {
                    navHostController.navigate(com.komekci.marketplace.ui.navigation.OnlinePayment(type.name))
                }
            )

            PaymentTypeButton(
                modifier = Modifier.fillMaxWidth(),
                icon = painterResource(R.drawable.promocod),
                title = "Promo kod",
                onClick = {
                    navHostController.navigate(com.komekci.marketplace.ui.navigation.WithCode(type.name))
                }
            )

            PaymentTypeButton(
                modifier = Modifier.fillMaxWidth(),
                icon = painterResource(R.drawable.paypal),
                title = "Paypal",
                onClick = {
                    navHostController.navigate(Routes.Paypal)
                }
            )
            PaymentTypeButton(
                modifier = Modifier.fillMaxWidth(),
                icon = painterResource(R.drawable.payme),
                title = "Payme",
                onClick = {
                    navHostController.navigate(Routes.Payme)
                }
            )
            PaymentTypeButton(
                modifier = Modifier.fillMaxWidth(),
                icon = painterResource(R.drawable.click),
                title = "Click Up",
                onClick = {
                    navHostController.navigate(Routes.ClickUp)
                }
            )

        }


    }

}

@Composable
fun PaymentTypeButton(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(10.dp)
    Row(
        modifier = modifier
            .background(
                color = Color(0xFF767680).copy(alpha = 0.12f),
                shape = shape
            )
            .clip(shape)
            .clickable {
                onClick()
            }
            .padding(vertical = 4.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Image(
            painter = icon,
            contentDescription = title,
            modifier = Modifier.size(52.dp)
        )
        Text(
            title,
            color = newTextColor,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 15.sp
            ),
            modifier = Modifier.weight(1f)
        )
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight,
            tint = Color(0xFF3C3C43).copy(alpha = 0.3f),
            contentDescription = "arrow"
        )
    }
}