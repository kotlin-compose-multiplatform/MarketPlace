package com.komekci.marketplace.features.auth.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.bonsai.core.Bonsai
import cafe.adriel.bonsai.core.node.Branch
import cafe.adriel.bonsai.core.node.Leaf
import cafe.adriel.bonsai.core.tree.Tree
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.core.locale.translateValue
import com.komekci.marketplace.features.auth.data.entity.country.AllCountryEntityItem
import com.komekci.marketplace.features.auth.data.entity.country.Region
import com.komekci.marketplace.features.auth.presentation.viewmodel.CreateAccountViewModel
import com.komekci.marketplace.features.onbaording.presentation.TopShapes
import com.komekci.marketplace.state.LocalRouteState
import com.komekci.marketplace.ui.navigation.Routes
import com.komekci.marketplace.ui.theme.newTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun CreateAccountScreen(onDone: () -> Unit = {}) {
    val viewmodel: CreateAccountViewModel = hiltViewModel()
    val strings = LocalStrings.current
    val globalRoute = LocalRouteState.current
    val countries = viewmodel.countryState



    LaunchedEffect(true) {
        viewmodel.getAllCountries()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = viewmodel.index.intValue, label = "", modifier = Modifier.align(
                if(viewmodel.index.intValue == 3) Alignment.Center else Alignment.TopCenter
            )
        ) {
            if (it == 0) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                    CenterAlignedTopAppBar(
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    onDone()
                                }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "back",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        },
                        title = {
                            Text(
                                text = strings.createAccount,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.W600
                                ),
                                color = Color(0xFF0F1E3C)
                            )
                        }
                    )
                    Spacer(Modifier.height(33.dp))
                    Text(
                        strings.enterNameAndCountry,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 15.sp,
                        ),
                        modifier = Modifier.fillMaxWidth(0.7f),
                        textAlign = TextAlign.Center,
                        color = newTextColor
                    )
                    Spacer(Modifier.height(33.dp))
                    PhoneNumberScreen(
                        viewModel = viewmodel,
                        title = strings.createAccount,
                        alternativeText = strings.haveAccount,
                        handleBack = true,
                        actionText = strings.signIn,
                        countries = countries.value.countries,
                        onActionClick = {
                            globalRoute.value = globalRoute.value.copy(
                                mainRoute = Routes.LoginScreen
                            )
                        },
                        onBackClick = {
                            onDone()
                        }
                    )


                }
            }
            if (it == 2) {
                ConfirmationScreen(
                    viewModel = viewmodel
                )
            }

            if (it == 3) {
                FinishScreen()
            }
        }

       if(viewmodel.index.intValue==3){
           Box(modifier = Modifier
               .fillMaxWidth()
               .padding(16.dp)
               .align(Alignment.BottomCenter)){
               Button(
                   onClick = onDone, colors = ButtonDefaults.buttonColors(
                       containerColor = MaterialTheme.colorScheme.primary
                   ),
                   shape = RoundedCornerShape(6.dp),
                   modifier = Modifier.fillMaxWidth()
               ) {
                   Text(
                       text = strings.start,
                       style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.W500),
                       color = MaterialTheme.colorScheme.onPrimary
                   )
               }
           }
       }

        BackHandler {
            onDone()
        }
    }
}

/*
Select Country
     Turkmenistan
         Ashgabat
         Balkan
         Dashoguz
     Uzbekistan
     Russia
 */

@Composable
fun TreeSelect(
    countries: List<AllCountryEntityItem>,
    defaultCountry: AllCountryEntityItem? = null,
    defaultRegion: Region? = null,
    onCountrySelect: (AllCountryEntityItem) -> Unit,
    onRegionSelect: (Region) -> Unit
) {
    val show = remember {
        mutableStateOf(false)
    }
    val strings = LocalStrings.current
    val selectedCountry = remember(defaultCountry) {
        mutableStateOf<AllCountryEntityItem?>(defaultCountry)
    }
    val selectedRegion = remember(defaultRegion) {
        mutableStateOf<Region?>(defaultRegion)
    }
    Column(Modifier.fillMaxWidth().background(
        color = Color(0xFF767680).copy(alpha = 0.12f),
        shape = RoundedCornerShape(10.dp)
    ).clip(RoundedCornerShape(10.dp)).clickable {
        show.value = !show.value
    }.padding(8.dp)) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = strings.selectCountry,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF0F1E3C)
            )

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }
        AnimatedVisibility(show.value) {
            LazyColumn {
                countries.forEach { country ->
                    item {
                        var isExpanded by remember { mutableStateOf(false) }

                        Column {
                            // Country Row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCountry.value = country
                                        onCountrySelect(country)
                                        isExpanded = !isExpanded
                                    }
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                selectedCountry.value?.let { c->
                                    if(c.id == country.id) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                                Text(
                                    text = translateValue(country.name?:"","",""),
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFF0F1E3C)
                                )
                            }

                            // Regions List
                            if (isExpanded) {
                                country.regions?.forEach { region ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedRegion.value = region
                                                isExpanded = false
                                                onRegionSelect(region)
                                            }
                                            .padding(start = 32.dp, top = 8.dp, bottom = 8.dp)
                                    ) {
                                        selectedRegion.value?.let { r->
                                            if(r.id == region.id) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                        Text(
                                            text = translateValue(region.name!!, "",""),
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color(0xFF0F1E3C)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
