package com.komekci.marketplace.features.home.presentation.ui.about

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.ui.app.ImageLoader
import com.primex.core.VerticalGrid

@Preview(showSystemUi = true)
@Composable
fun AboutUsScreen(navHostController: NavHostController = rememberNavController()) {
    val strings = LocalStrings.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = {
                navHostController.navigateUp()
            }, modifier = Modifier.align(Alignment.TopStart)) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color(0xFF1A1A1A),
                    modifier = Modifier.size(34.dp)
                )
            }

            Text(
                text = strings.aboutUs, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W700
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = strings.aboutUs, style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700
                ),
                color = Color(0xFF1C2024)
            )

            Text(
                text = strings.short, style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W600
                ),
                color = Color(0xFFA1A1AA)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        ImageLoader(
            url = "https://turkmenportal.com/images/uploads/cache/blogs/6be8ee32698655a52e44c48dcc9ad14a-8968182-382x254-2.webp",
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(
                    RoundedCornerShape(8.dp)
                ),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = strings.aboutDescription, style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.W500
            ),
            color = Color(0xFF3D3D3D)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = strings.ourShops, style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.W700
            ),
            color = Color(0xFF18181B)
        )
        Spacer(modifier = Modifier.height(16.dp))
        VerticalGrid(columns = 3, modifier = Modifier.fillMaxWidth()) {
            repeat(20) {
                ImageLoader(
                    url = "https://download.logo.wine/logo/Zara_(retailer)/Zara_(retailer)-Logo.wine.png",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(end = if ((it + 1) % 3 == 0) 0.dp else 12.dp, bottom = 12.dp)
                        .background(Color(0xFFF4F4F5)),
                    contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}