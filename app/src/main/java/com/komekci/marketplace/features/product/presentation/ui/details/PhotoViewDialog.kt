package com.komekci.marketplace.features.product.presentation.ui.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import com.komekci.marketplace.ui.app.ImageLoader
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotoViewDialog(
    images: List<String> = emptyList(),
    open: Boolean = false,
    selectedIndex: Int = 0,
    onClose: (Int) -> Unit = {}
) {

    if (open) {
        val pagerState = rememberPagerState(pageCount = {
            images.size
        }, initialPage = selectedIndex)
        Dialog(
            onDismissRequest = {
                onClose(pagerState.currentPage)
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                IconButton(
                    onClick = { onClose(pagerState.currentPage) }, modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .clip(
                            CircleShape
                        )
                        .zIndex(10f)
                        .background(Color(0x3DD6D6D6), CircleShape)
                ) {
                    Icon(Icons.Default.Close, contentDescription = null, tint = Color(0xFF1A1A1A))
                }
                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) {
                    ImageLoader(
                        url = images[it],
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(330.dp)
                            .zoomable(rememberZoomState())
                    )
                }

                Row(
                    Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 22.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(pagerState.pageCount) { iteration ->
                        val color =
                            if (pagerState.currentPage == iteration) Color(0xFF999999) else Color(
                                0xFFE4E4E9
                            )
                        Box(
                            modifier = Modifier
                                .padding(2.dp)
                                .clip(CircleShape)
                                .background(color)
                                .size(10.dp)
                        )
                    }
                }
            }
        }
    }


}