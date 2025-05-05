package com.komekci.marketplace.features.basket.presentation.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.komekci.marketplace.LocalStrings


@Preview(showSystemUi = true)
@Composable
fun BasketCompleteDialog(
    open: Boolean = true,
    onOrdersClick: () -> Unit = {},
    onClose: () -> Unit = {}
) {
    val strings = LocalStrings.current
   if(open) {
       Dialog(onDismissRequest = onClose) {
           Column(
               modifier = Modifier
                   .fillMaxWidth()
                   .background(
                       Color(0xFFFFFFFF),
                       shape = RoundedCornerShape(8.dp)
                   )
                   .padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)
           ) {
               Spacer(modifier = Modifier.height(12.dp))
               Row(
                   modifier = Modifier.fillMaxWidth(),
                   verticalAlignment = Alignment.CenterVertically
               ) {
                   Text(
                       text = strings.thanksForOrder,
                       color = Color(0xFF2F313F),
                       style = MaterialTheme.typography.titleLarge.copy(
                           fontSize = 20.sp, fontWeight = FontWeight.W700
                       ),
                       overflow = TextOverflow.Ellipsis,
                       maxLines = 2,
                       modifier = Modifier.weight(1f)
                   )
                   Box(
                       modifier = Modifier
                           .size(32.dp)
                           .clip(CircleShape)
                           .border(1.5.dp, Color(0xFFF2F2F5), CircleShape)
                           .clickable { onClose() },
                       contentAlignment = Alignment.Center
                   ) {
                       Icon(
                           Icons.Default.Clear, contentDescription = null, tint = Color(0xFF8B8D98)
                       )
                   }
               }

               Spacer(modifier = Modifier.height(6.dp))

               Text(
                   text = strings.orderReceived,
                   color = Color(0xFF7E808A),
                   style = MaterialTheme.typography.bodyMedium.copy(
                       fontWeight = FontWeight.W500
                   ),
               )
               Spacer(modifier = Modifier.height(6.dp))
               HorizontalDivider(
                   color = Color(0xFFDDDDE3)
               )
               Spacer(modifier = Modifier.height(6.dp))

               Row(
                   modifier = Modifier.fillMaxWidth(),
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.SpaceBetween
               ) {
                   Text(
                       text = strings.orderCode,
                       color = Color(0xFF7E808A),
                       style = MaterialTheme.typography.bodyLarge.copy(
                           fontWeight = FontWeight.W500
                       ),
                   )
                   Text(
                       text = "1001",
                       color = Color(0xFF1C2024),
                       style = MaterialTheme.typography.bodyLarge.copy(
                           fontWeight = FontWeight.W500
                       ),
                   )
               }
               Spacer(modifier = Modifier.height(6.dp))

               Row(
                   modifier = Modifier.fillMaxWidth(),
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.SpaceBetween
               ) {
                   Text(
                       text = strings.orderDate,
                       color = Color(0xFF7E808A),
                       style = MaterialTheme.typography.bodyLarge.copy(
                           fontWeight = FontWeight.W500
                       ),
                   )
                   Text(
                       text = "October 27, 2024",
                       color = Color(0xFF1C2024),
                       style = MaterialTheme.typography.bodyLarge.copy(
                           fontWeight = FontWeight.W500
                       ),
                   )
               }

               Spacer(modifier = Modifier.height(6.dp))

               Text(
                   text = strings.whereOrder,
                   color = Color(0xFF7E808A),
                   style = MaterialTheme.typography.bodyMedium.copy(
                       fontWeight = FontWeight.W500
                   ),
                   modifier = Modifier.clickable {
                       onOrdersClick()
                   }
               )

               Spacer(modifier = Modifier.height(6.dp))

           }
       }
   }
}