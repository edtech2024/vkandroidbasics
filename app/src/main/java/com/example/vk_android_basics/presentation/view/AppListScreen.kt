package com.example.vk_android_basics.presentation.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vk_android_basics.presentation.viewmodel.AppsUiEffect
import com.example.vk_android_basics.presentation.viewmodel.AppsViewModel
import com.example.vkandroidbasics.domain.model.AppEntity

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AppListScreen(
    onAppClick: (Int) -> Unit,
    viewModel: AppsViewModel // Принимаем инстанс из MainActivity
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AppsUiEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(message = effect.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = "Мои Приложения", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp, end = 8.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = android.R.drawable.sym_def_app_icon),
                            contentDescription = "Логотип",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.clickable { viewModel.onToolbarClick() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.mainAppsList, key = { it.id }) { app ->
                AppListItem(app = app, onClick = onAppClick)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Витрина", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 12.dp))

                val pagerState = rememberPagerState(pageCount = { 3 })

                HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth(), pageSpacing = 16.dp) { page ->
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        val startIndex = page * 3
                        val pageApps = state.showcaseAppsList.drop(startIndex).take(3)

                        for (app in pageApps) {
                            AppListItem(app = app, onClick = onAppClick)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AppListItem(app: AppEntity, onClick: (Int) -> Unit) { // Заменили тип на AppEntity
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick(app.id) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = app.iconRes),
                contentDescription = null,
                modifier = Modifier.size(48.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant).padding(8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = app.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = app.description, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
                Spacer(modifier = Modifier.height(8.dp))
                SuggestionChip(onClick = {}, label = { Text(text = app.category, fontSize = 12.sp) })
            }
        }
    }
}
