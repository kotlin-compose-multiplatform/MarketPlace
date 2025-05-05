package com.komekci.marketplace.features.create_store.presentation.ui.product

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.create_store.data.entity.product.MyProductsItem
import com.komekci.marketplace.ui.app.ImageLoader

@Composable
fun ProductItem(
    product: MyProductsItem,
    modifier: Modifier = Modifier,
    onClickEdit: () -> Unit,
    onClick: () -> Unit,
) {
    val strings = LocalStrings.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF2F2F5), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Text(
                text = "${strings.codeOfProduct}: #${product.code}",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W500),
                color = Color(0x9E000713),
                modifier = Modifier
                    .background(Color(0x0D00003B), RoundedCornerShape(3.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )

            Text(
                text = product.status,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF006316),
                modifier = Modifier
                    .background(Color(0x1402B302), RoundedCornerShape(3.dp))
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            )
        }
        Spacer(modifier = Modifier.height(11.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 69.dp, height = 80.dp)
            ) {
                ImageLoader(
                    url = try {
                        product.images[0].image
                    } catch (_: Exception) {
                        ""
                    }, modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(4.dp)
                        ),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "+${product.images.size}",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W700),
                    color = Color(0xFF333333),
                    modifier = Modifier
                        .padding(1.dp)
                        .background(Color(0xCCD9D9D9), RoundedCornerShape(4.dp))
                        .padding(2.dp)
                        .align(Alignment.BottomEnd)
                )
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = translateValue(instance = product.name, property = "", prefix = ""),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF3D3D3D),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                if(product.prices.isNullOrEmpty()) {
                    Text(
                        text = "${product.price?:0} ${product.currency?:"TMT"}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700),
                        color = Color(0xFF333333),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    product.prices.let { prices->
                        if(prices.isNotEmpty()) {
                            Column {
                                repeat(prices.count()) { index->
                                    val price = prices[index]
                                    Text(
                                        text = "${price.price} ${price.currency?:"TMT"}",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W700),
                                        color = Color(0xFF333333),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFFFCFCFD), RoundedCornerShape(4.dp))
                            .clip(RoundedCornerShape(4.dp))
                            .clickable {
                                onClickEdit()
                            }
                            .padding(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_icon),
                            contentDescription = null,
                            tint = Color(0xFF3F3F46),
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(Color(0xFFFCFCFD), RoundedCornerShape(4.dp))
                            .padding(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.outline_remove_red_eye_24),
                            contentDescription = null,
                            tint = Color(0xFF3F3F46),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}