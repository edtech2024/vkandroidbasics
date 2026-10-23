package com.example.vk_android_basics

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
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AppListScreen(
    onAppClick: (Int) -> Unit,
    viewModel: AppsViewModel = viewModel() // Инициализация ViewModel презентационного слоя
) {
    // Подписываемся на состояние экрана (UI State) из ViewModel
    val state by viewModel.uiState.collectAsState()

    // Состояние для управления показом Snackbar
    val snackbarHostState = remember { SnackbarHostState() }

    // Безопасный сбор одноразовых эффектов (Snackbar не сработает повторно при смене ориентации)
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
        // Интегрируем SnackbarHost в Scaffold для вывода уведомлений поверх интерфейса
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
                // Обрабатываем клик по всему Тулбару через вызов метода во ViewModel
                modifier = Modifier.clickable { viewModel.onToolbarClick() }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1. Основной вертикальный список приложений (Практическое задание №4, Часть 1)
            items(state.mainAppsList, key = { it.id }) { app ->
                AppListItem(app = app, onClick = onAppClick)
            }

            // 2. Архитектурный блок «Витрина» (Практическое задание №4, Часть 2)
            item {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Витрина",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Состояние Pager, жестко ограниченное 3-мя страницами по условию ДЗ
                val pagerState = rememberPagerState(pageCount = { 3 })

                // Горизонтальный постраничный скролл
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                    pageSpacing = 16.dp
                ) { page ->
                    // Контейнер для отображения элементов внутри одной страницы Pager
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Математическая нарезка: по 3 приложения на страницу (всего 9 приложений)
                        val startIndex = page * 3
                        val pageApps = state.showcaseAppsList.drop(startIndex).take(3)

                        // Отрисовываем элементы (верстка полностью переиспользуется)
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
fun AppListItem(app: AppInfo, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(app.id) }, // Ссылка на экшен навигации
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = app.iconRes),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(8.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = app.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = app.description, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 2)
                Spacer(modifier = Modifier.height(8.dp))
                SuggestionChip(onClick = {}, label = { Text(text = app.category, fontSize = 12.sp) })
            }
        }
    }
}
