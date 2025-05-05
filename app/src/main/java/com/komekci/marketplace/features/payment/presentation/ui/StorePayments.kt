package com.komekci.marketplace.features.payment.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.R
import com.komekci.marketplace.core.utils.DateHelper
import com.komekci.marketplace.features.create_store.presentation.viewmodel.StoreViewModel
import com.komekci.marketplace.ui.app.AppError
import com.komekci.marketplace.ui.app.AppLoading
import com.komekci.marketplace.ui.app.NoData

@Preview(showSystemUi = true)
@Composable
fun StorePayments(
    navHostController: NavHostController = rememberNavController()
) {
    val storeViewModel: StoreViewModel = hiltViewModel()
    val state = storeViewModel.payments.collectAsState()
    val dates = listOf(
        "all","onedayago","oneweekago","onemonthago"
    )

    val types = listOf(
        "all","onedayago","oneweekago","onemonthago"
    )
    val selectedDate = rememberSaveable {
        mutableStateOf(dates[0])
    }
    val selectedType = rememberSaveable {
        mutableStateOf(types[0])
    }
    LaunchedEffect(selectedType.value, selectedDate.value) {
        storeViewModel.getPaymentHistory(selectedDate.value, selectedType.value)
    }



    val strings = LocalStrings.current
    val openMonth = remember {
        mutableStateOf(false)
    }
    val openType = remember {
        mutableStateOf(false)
    }

//    SelectSheet(
//        title = strings.myPayments,
//        open = openType.value,
//        items = listOf(strings.all, strings.appName, strings.myStore),
//        onSelected = {
//            selectedType.value = types[it]
//        }
//    ) {
//        openType.value = false
//    }

    SelectSheet(
        title = strings.myPayments,
        open = openMonth.value,
        items = listOf(strings.all, "Gunlik", "Hepdelik", strings.currentMonth),
        onSelected = {
            selectedDate.value = dates[it]
        }
    ) {
        openMonth.value = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F9FB))
    ) {


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                tint = Color(0xFF1A1A1A),
                modifier = Modifier
                    .size(34.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .clickable { navHostController.navigateUp() }
            )

            Text(
                text = strings.myPayments, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W600
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFDDF3E4), RoundedCornerShape(4.dp))
                .clickable { openMonth.value = true }
                .padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = strings.currentMonth,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                    color = Color(0xFF1C2024)
                )
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color(0xFF1A1A1A)
                )
            }

//            Row(modifier = Modifier
//                .clip(RoundedCornerShape(4.dp))
//                .background(Color.White, RoundedCornerShape(4.dp))
//                .clickable { openType.value = true }
//                .padding(6.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                Text(
//                    text = strings.payment,
//                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
//                    color = Color(0xFF1C2024)
//                )
//                Icon(
//                    Icons.Default.ArrowDropDown,
//                    contentDescription = null,
//                    tint = Color(0xFF1A1A1A)
//                )
//            }
        }

        Spacer(modifier = Modifier.height(12.dp))

       if(state.value.loading) {
           AppLoading(modifier = Modifier.fillMaxSize())
       } else if(state.value.error.isNullOrEmpty().not()) {
           AppError {
               storeViewModel.getPaymentHistory(selectedDate.value, selectedType.value)
           }
       } else if(state.value.isEmpty) {
           NoData(modifier = Modifier.fillMaxSize())
       } else {
           state.value.data?.let { list->
               LazyColumn(
                   modifier = Modifier.fillMaxSize(),
                   verticalArrangement = Arrangement.spacedBy(8.dp),
                   contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
               ) {
                   items(list.size) {index->
                       val item = list[index]
                       PaymentItem(
                           title = if (item.type == "komekchi") "Komekci" else "Dukan",
                           icon = if (item.type == "komekchi") R.drawable.mini_logo else R.drawable.storefront,
                           date = DateHelper.convertOnlyDate(item.start)?:"",
                           expireDate = DateHelper.convertOnlyDate(item.end)?:"",
                           price = "${item.totalPrice}TMT"
                       )
                   }
               }
           }
       }
    }
}