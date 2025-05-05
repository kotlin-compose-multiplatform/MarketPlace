package com.komekci.marketplace.features.home.presentation.ui.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.ui.app.ImageLoader
import com.komekci.marketplace.ui.app.NoData

@Composable
fun NotificationScreen(homeNavController: NavHostController) {
    val strings = LocalStrings.current
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            IconButton(onClick = {
                homeNavController.navigateUp()
            }) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color(0xFF1A1A1A),
                    modifier = Modifier.size(40.dp)
                )
            }

            Text(
                text = strings.notifications, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W700
                ),
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFF1A1A1A)
            )


        }

        NoData(modifier = Modifier.fillMaxSize())

//        NotificationItem(
//            title = "Amazon",
//            message = "Discover fresh deals in various categories! Explore now and find your favorites. Don't miss out on these fantastic offers.",
//            image = "https://www.atavatan-turkmenistan.com/wp-content/uploads/2020/09/14-12.jpg",
//            date = "07 Dekabr 2023",
//            time = "12:53",
//            status = "Прочитанно",
//            tags = listOf(
//                "Скидки", "Новинки", "Акции"
//            )
//        )
    }
}

@Composable
fun NotificationItem(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    image: String,
    date: String,
    time: String,
    status: String,
    tags: List<String>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFEFF6FF))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ImageLoader(
                url = image,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFFE4E4E7), shape = CircleShape),
                contentScale = ContentScale.Crop,
            )

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                color = Color(0xFF3D3D3D)
            )
        }

        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W400),
            color = Color(0xFF5D5D5D)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = date,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                color = Color(0xFF888888)
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(3.dp)
                    .background(Color(0xFF888888))
            ) {
            }



            Text(
                text = time,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W400),
                color = Color(0xFF888888)
            )


            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(3.dp)
                    .background(Color(0xFF888888))
            ) {
            }

            Text(
                text = status,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W600),
                color = Color(0xFF888888)
            )

        }

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(tags.size) {
                Text(
                    text = tags[it],
                    modifier = Modifier
                        .background(
                            Color(0xFFDBEAFE),
                            shape = RoundedCornerShape(38.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W600),
                    color = Color(0xFF5D5D5D)
                )
            }
        }
    }
}