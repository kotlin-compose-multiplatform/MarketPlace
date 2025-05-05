package com.komekci.marketplace.features.home.presentation.ui.faq

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.features.home.presentation.ui.SearchInput
import com.komekci.marketplace.features.main.presentation.ui.AppDrawer
import com.komekci.marketplace.ui.app.Accordion
import com.komekci.marketplace.ui.navigation.Routes

@Composable
fun FaqScreen(navHostController: NavHostController) {
    val drawerOpen = remember {
        mutableStateOf(false)
    }
    val strings = LocalStrings.current
    AppDrawer(
        open = drawerOpen.value,
        navHostController = navHostController,
        onBottomNavigationChange = {

        }) {
        drawerOpen.value = false
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .border(
                        1.dp,
                        Color(0xFFD3D4DB),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        navHostController.navigateUp()
                    }
                    .padding(14.dp)
                    .height(25.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color(0xFF60646C)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            SearchInput(modifier = Modifier.weight(1f)) {
                navHostController.navigate(Routes.SearchRoute.replace("{text}", it))
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .border(
                        1.dp,
                        Color(0xFFD3D4DB),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable {
                        drawerOpen.value = true
                    }
                    .padding(14.dp)
                    .height(25.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.menu),
                    contentDescription = null,
                    tint = Color(0xFF60646C)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = strings.faqTitle, style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            ),
            color = Color(0xFF000000),
            modifier = Modifier
        )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(10) {
                Accordion(
                    title = "Enim enim feugiat dictumst vehicula. Amet `velit ut blandit egestas consectetur viverra. ",
                    description = "Condimentum suspendisse iaculis duis tristique lacus consequat eget quis. Eu vulputate in amet iaculis nulla faucibus aliquam quis vestibulum. Consectetur eu urna convallis ac blandit congue. Condimentum suspendisse iaculis duis tristique lacus consequat eget quis. Eu vulputate in amet iaculis nulla faucibus aliquam quis vestibulum. Consectetur eu urna convallis ac blandit congue. Condimentum suspendisse iaculis duis tristique lacus consequat eget quis. Eu vulputate in amet iaculis nulla faucibus aliquam quis vestibulum. Consectetur eu urna convallis ac blandit congue."
                )
            }
        }
    }
}