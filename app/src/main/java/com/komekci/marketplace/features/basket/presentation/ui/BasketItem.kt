package com.komekci.marketplace.features.basket.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.basket.data.entity.BasketLocalEntity
import com.komekci.marketplace.features.basket.data.entity.sampleBasketItem
import com.komekci.marketplace.features.basket.presentation.viewmodel.BasketViewModel
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.ui.app.FavoriteButton
import com.komekci.marketplace.ui.app.ImageLoader

@Preview(showSystemUi = true)
@Composable
fun BasketItem(
    product: BasketLocalEntity = sampleBasketItem,
    viewModel: BasketViewModel? = null,
    onClick: ()-> Unit = {}
) {
    val strings = LocalStrings.current
    val count = remember {
        mutableIntStateOf(product.count)
    }
    fun change(c: Int) {
        viewModel?.changeCountBasket(c, product.id) {
            count.intValue = it
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFAFAFA))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ImageLoader(
                url = product.image,
                modifier = Modifier
                    .height(80.dp)
                    .width(70.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${product.price} TMT",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W700),
                        color = Color(0xFF333333)
                    )

                    if(product.discount>0) {
                        Text(
                            text = "${product.oldPrice} TMT",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W600),
                            color = Color(0xFF333333),
                            textDecoration = TextDecoration.LineThrough
                        )
                    }

                }
                Text(
                    text = translateValue(instance = product, property = "name"),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF3D3D3D)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "${strings.size} 42",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF71717A)
                    )

                    Box(
                        Modifier
                            .size(6.dp)
                            .background(Color(0xFF888888), CircleShape)
                    ) {

                    }

                    Text(
                        text = "${strings.color}",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                        color = Color(0xFF71717A)
                    )

                }

            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFF4F4F5), RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {

                        },
                    contentAlignment = Alignment.Center
                ) {
                    FavoriteButton(id = product.id, type = FavoriteType.PRODUCT) {

                    }
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFF4F4F5), RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                            viewModel?.deleteFromBasket(product.id)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.outline_delete_24),
                        contentDescription = null,
                        tint = Color(0xFF3F3F46)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFF4F4F5), RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                            change(-1)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.baseline_remove_24),
                        contentDescription = null,
                        tint = Color(0xFFA1A1AA)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFF4F4F5), RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {

                        },
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = count.intValue.toString(),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.W700
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color(0xFF3F3F46)
                    )
                }


                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFF4F4F5), RoundedCornerShape(4.dp))
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                            change(1)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = Color(0xFFA1A1AA)
                    )
                }
            }
        }
    }
}