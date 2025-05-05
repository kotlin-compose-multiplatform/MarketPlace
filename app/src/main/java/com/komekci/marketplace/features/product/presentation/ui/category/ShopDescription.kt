package com.komekci.marketplace.features.product.presentation.ui.category

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.ui.app.CheckAuthScreen
import com.komekci.marketplace.ui.app.ImageLoader

@Preview(showSystemUi = true)
@Composable
fun ShopAds(
    open: Boolean = true,
    instagram: String? = null,
    tiktok: String? = null,
    onDismiss: () -> Unit = {}
) {
    if (open) {
        val uriHandler = LocalUriHandler.current
        Dialog(
            onDismissRequest = {
                onDismiss()
            }
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                onDismiss()
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "close",
                                tint = Color(0xFF64748B)
                            )
                        }
                    }

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = LocalStrings.current.ads,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.W700
                        ),
                        color = Color(0xFF0F1E3C)
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = LocalStrings.current.youCanSee,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.W400,
                            fontSize = 13.sp
                        ),
                        color = Color(0xFF0F1E3C),
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(12.dp))

                    instagram?.let {
                        Button(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(14.dp),
                            onClick = {
                                uriHandler.openUri("https://instagram.com/${instagram}")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF767680).copy(0.1f)
                            )
                        ) {
                            Text(
                                "Instagram",
                                color = Color(0xFF0F1E3C),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.W600,
                                    fontSize = 15.sp
                                )
                            )
                        }

                        Spacer(Modifier.height(4.dp))
                    }

                    tiktok?.let {
                        TextButton(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            shape = RoundedCornerShape(14.dp),
                            onClick = {
                                uriHandler.openUri("https://tiktok.com/${tiktok}")
                            }
                        ) {
                            Text(
                                "TikTok",
                                color = Color(0xFF0F1E3C),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.W600,
                                    fontSize = 15.sp
                                )
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                }
            }
        }
    }
}

@Composable
fun ShopDescription(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    title: String,
    countProducts: Int,
    logo: String,
    instagram: String? = null,
    tiktok: String? = null,
    onChatClick: () -> Unit
) {
    val openAds = remember {
        mutableStateOf(false)
    }
    val strings = LocalStrings.current
    ShopAds(
        open = openAds.value,
        instagram = instagram,
        tiktok = tiktok,
        onDismiss = {
            openAds.value = false
        }
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF767680).copy(alpha = 0.12f), shape = RoundedCornerShape(8.dp))
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ImageLoader(
            url = logo, modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .width(85.dp)
                .height(68.dp),
            contentScale = ContentScale.FillBounds
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title, style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.W600,
                    fontSize = 18.sp
                ),
                color = Color(0xFF0F1E3C)
            )
            val boldText = buildAnnotatedString {
                append(strings.total)
                withStyle(style = SpanStyle(fontWeight = FontWeight.W700)) {
                    append(" $countProducts ")
                }
                append(strings.product)
            }
            Text(
                text = boldText,
                color = Color(0xFF0F1E3C),
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400)
            )
        }
        CheckAuthScreen(
            successContent = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .clickable {
                               openAds.value = true
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.play),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable {
                                if (loading.not()) {
                                    onChatClick()
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(30.dp),
                                color = Color.White
                            )
                        } else {
                            Icon(
                                painter = painterResource(id = R.drawable.chat_second),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }

                    }
                }
            },
            errorContent = {

            }
        )
    }
}