package com.komekci.marketplace.features.chat.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komekci.marketplace.R
import com.komekci.marketplace.core.utils.DateHelper
import com.komekci.marketplace.features.chat.domain.model.ChatsModel
import com.komekci.marketplace.ui.app.ImageLoader

@Composable
fun ChatsItem(
    modifier: Modifier = Modifier,
    item: ChatsModel,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFE5484D), RoundedCornerShape(16.dp))
                    .padding(if (item.image.isEmpty()) 4.dp else 0.dp),
                contentAlignment = Alignment.Center
            ) {
                if (item.image.isEmpty()) {
                    Text(
                        text = "FTD",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700),
                        color = Color.White
                    )
                } else {
                    ImageLoader(
                        url = item.image, modifier = Modifier
                            .fillMaxSize()
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                    )
                }
            }

            if(item.isOnline) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF2CC069), CircleShape)
                        .align(Alignment.TopEnd)
                        .border(2.dp, Color.White, CircleShape)
                        .offset(x = 6.dp, y = (-12).dp)
                ) {

                }
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF0F1828),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.lastMessage,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.W500,
                    fontSize = 11.sp
                ),
                color = Color(0xFFADB5BD),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = DateHelper.convertDateAndTime(item.time),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.W400,
                    fontSize = 10.sp
                ),
                color = Color(0xFFA4A4A4)
            )
            Spacer(modifier = Modifier.height(12.dp))
            if (item.unreadCount > 0) {
                Text(
                    text = if (item.unreadCount > 99) "99+" else item.unreadCount.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.W600,
                        fontSize = 10.sp
                    ),
                    color = Color(0xFFD84D4D),
                    modifier = Modifier
                        .background(Color(0xFFFFEFEF), RoundedCornerShape(40.dp))
                        .defaultMinSize(minWidth = 21.dp, minHeight = 21.dp)
                        .padding(2.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = null,
                    tint = Color(0xFFE5484D)
                )
            }
        }
    }
}