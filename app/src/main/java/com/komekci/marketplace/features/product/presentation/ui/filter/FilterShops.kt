package com.komekci.marketplace.features.product.presentation.ui.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.ui.app.AppCheckBox

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FilterShops(
    modifier: Modifier = Modifier,
    title: String = LocalStrings.current.shops,
    list: List<String>? = emptyList(),
    values: List<String>? = emptyList(),
    selected: List<String>? = emptyList(),
    onChecked: (Boolean, String) -> Unit
) {
    val strings = LocalStrings.current
    list?.let { l->
        if(l.isNotEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = title, style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W700
                    ),
                    color = Color(0xFF2F313F)
                )

                Spacer(modifier = Modifier.height(12.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    repeat(l.size) {
                        val item = l[it]
                        Row(
                            modifier = Modifier,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val checked = remember {
                                mutableStateOf(selected?.contains(values?.get(it) ?: "")?:false)
                            }
                            AppCheckBox(
                                checked = checked.value,
                                onChange = {check->
                                    onChecked(check, values?.get(it)?:"")
                                    checked.value = check
                                }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = item,
                                color = Color(0xFF1C2024),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.W500
                                ),
                                modifier = Modifier.clickable {
                                    checked.value = !checked.value
                                }
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                        }
                    }
                }
            }
        }
    }
}