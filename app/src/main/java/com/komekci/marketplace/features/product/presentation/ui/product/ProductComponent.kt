package com.komekci.marketplace.features.product.presentation.ui.product

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.favorite.domain.model.FavoriteType
import com.komekci.marketplace.features.product.domain.model.ProductsEntity
import com.komekci.marketplace.features.product.domain.model.sampleProduct
import com.komekci.marketplace.features.product.domain.model.sampleProductWithoutDiscount
import com.komekci.marketplace.features.product.presentation.viewmodel.CategoryViewModel
import com.komekci.marketplace.ui.app.CheckAuthScreen
import com.komekci.marketplace.ui.app.FavoriteButton
import com.komekci.marketplace.ui.app.ImageLoader
import com.primex.core.VerticalGrid

@Preview
@Composable
fun ProductGridPreview(modifier: Modifier = Modifier) {
    VerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = 2
    ) {
        ProductComponent(
            Modifier
                .fillMaxWidth()
                .padding(end = 6.dp), item = sampleProduct)
        ProductComponent(
            Modifier
                .fillMaxWidth()
                .padding(start = 6.dp), item = sampleProductWithoutDiscount)
    }
}



@Preview
@Composable
fun ProductComponent(
    modifier: Modifier = Modifier,
    item: ProductsEntity? = sampleProduct,
    viewModel: CategoryViewModel = hiltViewModel(),
    isPreview: Boolean = false,
    isFavorite: Boolean = false,
    onFavoriteClick: (String) -> Unit = {},
    onClick: () -> Unit = {},
) {
    val strings = LocalStrings.current

    item?.let {
        val count = rememberSaveable {
            mutableIntStateOf(0)
        }
        LaunchedEffect(item.id) {
            viewModel.getBasketCount(item.id) {
                count.intValue = it
            }
        }

        fun change(count: Int) {
            viewModel.changeCountBasket(count, item, onChange = {

            })
        }
        Column(
            modifier = modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFF2F2F5))
                .clickable { onClick() }
                .padding(vertical = 4.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp), contentAlignment = Alignment.Center
            ) {
                ImageLoader(
                    url = if (item.image.isNullOrEmpty().not()) item.image?.get(0) ?: "" else "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(136.dp)
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(
                                topEnd = 8.dp,
                                topStart = 8.dp
                            )
                        )
                        .clip(
                            RoundedCornerShape(
                                topEnd = 8.dp,
                                topStart = 8.dp
                            )
                        ),
                    contentScale = ContentScale.Crop
                )
                CheckAuthScreen(
                    successContent = {
                        FavoriteButton(
                            id = item.id,
                            type = FavoriteType.PRODUCT,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(y = 2.dp, x = (-2).dp)

                        ) {
                            onFavoriteClick(item.id)
                        }
                    },
                    errorContent = {

                    }
                )

               if(item.discount>0) {
                   Box(
                       modifier = Modifier
                           .padding(10.dp)
                           .background(
                               Color(0xFFDE2622), RoundedCornerShape(
                                   topEnd = 6.dp,
                                   topStart = 6.dp
                               )
                           )
                           .defaultMinSize(minWidth = 42.dp)
                           .padding(vertical = 7.dp, horizontal = 4.dp)
                           .align(Alignment.BottomEnd),
                       contentAlignment = Alignment.Center
                   ) {
                       Text(
                           text = "${item.discount}%",
                           style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                           color = Color.White,
                           maxLines = 1,
                           overflow = TextOverflow.Ellipsis
                       )

                   }
               }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "${item.price} TMT",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                        color = Color(0xFF182E5C)
                    )
                    if (item.discount > 0) {
                        Text(
                            text = "${item.oldPrice} TMT",
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400, fontSize = 11.sp),
                            color = Color(0xFFDE2622),
                            modifier = Modifier,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }

                }
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        painter = painterResource(R.drawable.storefront),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "store",
                        modifier = Modifier.size(11.dp)
                    )
                    Text(
                        text = "${strings.shop}: ${item.shopName}",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W500, fontSize = 11.sp),
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = if(isPreview) "Test" else translateValue(instance = item, property = "name"),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF101828),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.weight(1f))
            Box(Modifier.padding(horizontal = 4.dp)) {
                if (count.intValue > 0) {
                    Button(
                        onClick = { /*TODO*/ },
                        shape = MaterialTheme.shapes.small,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF4F4F5)
                        ),
                        modifier = modifier
                            .height(60.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Icon(
                                painterResource(id = R.drawable.baseline_remove_24),
                                contentDescription = null,
                                tint = Color(0xFFA1A1AA),
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        count.intValue = count.intValue.minus(1)
                                        change(-1)
                                    }
                            )
                            Text(
                                text = count.intValue.toString(),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.W500
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = Color(0xFF3F3F46)
                            )
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                tint = Color(0xD6006316),
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        count.intValue = count.intValue.plus(1)
                                        change(1)
                                    }
                            )
                        }
                    }
                } else {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(
                            bottomEnd = 8.dp,
                            bottomStart = 8.dp
                        ),
                        onClick = {
                            count.intValue = 1
                            change(1)
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.basket),
                                contentDescription = "basket",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                strings.addToBasket,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.W400
                                ),
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

    }

}
