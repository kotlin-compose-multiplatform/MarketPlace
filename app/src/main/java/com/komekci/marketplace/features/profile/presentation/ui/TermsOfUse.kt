package com.komekci.marketplace.features.profile.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.komekci.marketplace.LocalStrings
import com.komekci.marketplace.features.profile.presentation.viewmodel.ProfileViewModel
import com.komekci.marketplace.ui.app.AppLoading

@Composable
fun TermsOfUse(
    navHostController: NavHostController
) {
    val strings = LocalStrings.current

    val profileViewModel: ProfileViewModel = hiltViewModel()
    val state = profileViewModel.privacyState
    LaunchedEffect(true) {
        profileViewModel.getPrivacyPolicy()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
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
                text = strings.termsOfUse, style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.W700
                ), textAlign = TextAlign.Center,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.align(Alignment.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

        }

        if(state.value.loading) {
            AppLoading(Modifier.fillMaxSize())
        } else {
            state.value.result?.let {
                Text(
                    text = it.title?:"", style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.W700,
                        fontSize = 18.sp
                    ),
                    color = Color(0xFF1C2024),
                    modifier = Modifier,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )



                Text(
                    text = it.text?:"",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.W400
                    ),
                    color = Color(0xFF333333),
                    modifier = Modifier,
                )
            }
        }



    }
}