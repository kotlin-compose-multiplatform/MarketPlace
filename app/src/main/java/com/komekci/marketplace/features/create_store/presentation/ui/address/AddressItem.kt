package com.komekci.marketplace.features.create_store.presentation.ui.address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.komekci.marketplace.R

@Composable
fun AddressItem(
    region: String,
    city: String,
    onEditClick: ()-> Unit,
    onDelete: ()-> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = region,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W500),
                color = Color(0xFF333333)
            )
            Text(
                text = city,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W400),
                color = Color(0xFF333333),
                modifier = Modifier.weight(1f)
            )
            
            IconButton(onClick = onEditClick) {
                Icon(painter = painterResource(id = R.drawable.edit), contentDescription = null, tint = Color(0xFFD4D4D8))
            }
            IconButton(onClick = onDelete) {
                Icon(painter = painterResource(id = R.drawable.outline_delete_24), contentDescription = null, tint = Color(0xFFD4D4D8))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color(0xFFEAECF0))
    }
}