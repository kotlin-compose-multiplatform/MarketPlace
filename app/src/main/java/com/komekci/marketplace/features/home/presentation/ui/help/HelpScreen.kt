package com.komekci.marketplace.features.home.presentation.ui.help

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R

@Composable
fun HelpScreen(navHostController: NavHostController) {
    val strings = LocalStrings.current
    val selectedPhone = rememberSaveable {
        mutableStateOf("+99362737222")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = {
                navHostController.navigateUp()
            }, modifier = Modifier.align(Alignment.TopStart)) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color(0xFF1A1A1A),
                    modifier = Modifier.size(34.dp)
                )
            }

            Text(
                text = strings.helper, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W500
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = strings.helperDescription,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
            color = Color(0xFF8B8D98)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_box_24),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = strings.freeAddTitle,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                modifier = Modifier.offset(y = (-2).dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        PhoneSelect(
            text = "Asgabat",
            phone = "+99362737222",
            selected = "+99362737222" == selectedPhone.value
        ) {
            selectedPhone.value = it
        }

        PhoneSelect(
            text = "Balkan",
            phone = "+993658900",
            selected = "+993658900" == selectedPhone.value
        ) {
            selectedPhone.value = it
        }

        PhoneSelect(
            text = "Ahal",
            phone = "+993658901",
            selected = "+993658901" == selectedPhone.value
        ) {
            selectedPhone.value = it
        }

        PhoneSelect(
            text = "Mary",
            phone = "+993658902",
            selected = "+993658902" == selectedPhone.value
        ) {
            selectedPhone.value = it
        }

        PhoneSelect(
            text = "Daşoguz",
            phone = "+993658903",
            selected = "+993658903" == selectedPhone.value
        ) {
            selectedPhone.value = it
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = strings.pay,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                    color = Color.White
                )
            }


            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.small,
                border = BorderStroke(1.dp, color = Color(0x99008D1A)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0x0D05C005)
                ),
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.chatteardropdots),
                    contentDescription = null,
                    tint = Color(0x99006316)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = strings.writeMessage,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W500),
                    color = Color(0x99006316)
                )
            }
        }
    }
}

@Composable
fun PhoneSelect(
    modifier: Modifier = Modifier,
    text: String,
    phone: String,
    selected: Boolean = false,
    onClick: (String) -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .background(
            if (selected) Color(0x0D05C005) else Color.Transparent,
            shape = RoundedCornerShape(8.dp)
        )
        .border(
            width = 1.dp,
            color = if (selected) Color(0x99008D1A) else Color(0x4500082F),
            shape = RoundedCornerShape(8.dp)
        )
        .clickable {
            onClick(phone)
        }
        .padding(vertical = 16.dp, horizontal = 8.dp)) {
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "$text / $phone",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
            color = if (selected) Color(0xD6006316) else Color(0xFF60646C)
        )

    }
}