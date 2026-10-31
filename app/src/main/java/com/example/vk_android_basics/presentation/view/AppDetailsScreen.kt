package com.example.vk_android_basics.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.vk_android_basics.presentation.viewmodel.AppsViewModel
import com.example.vkandroidbasics.domain.model.AppEntity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AppDetailsScreen(
    appId: Int,
    onBackClick: () -> Unit,
    onShowcaseItemClick: () -> Unit,
    viewModel: AppsViewModel // Передаем ViewModel для получения чистых данных
) {
    val state by viewModel.uiState.collectAsState()

    // Ищем приложение внутри доменных списков из UI State
    val app = state.mainAppsList.find { it.id == appId } ?: state.showcaseAppsList.find { it.id == appId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = app?.title ?: "Детали", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (app != null) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = app.iconRes),
                            contentDescription = null,
                            modifier = Modifier.size(72.dp).clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.surfaceVariant).padding(12.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(text = app.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                            Text(text = app.category, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = app.description, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                Text(text = "Приложение не найдено", fontSize = 18.sp)
            }

            Column(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Text(text = "Витрина", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))

                val pagerState = rememberPagerState(pageCount = { 3 })

                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
                    val startIndex = page * 3
                    val pageApps = state.showcaseAppsList.drop(startIndex).take(3)

                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        pageApps.forEach { showcaseApp ->
                            ShowcaseItemRow(app = showcaseApp, onClick = onShowcaseItemClick)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(Modifier.fillMaxWidth().height(8.dp), horizontalArrangement = Arrangement.Center) {
                    repeat(3) { iteration ->
                        val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                        Box(modifier = Modifier.padding(horizontal = 4.dp).clip(CircleShape).background(color).size(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ShowcaseItemRow(app: AppEntity, onClick: () -> Unit) { // Изменили тип на AppEntity
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = app.iconRes),
                contentDescription = null,
                modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant).padding(6.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = app.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = app.category, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
