package com.example.inventorymgmt.feature.home.screen

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkManager
import com.example.inventorymgmt.core.database.InventoryDatabase
import com.example.inventorymgmt.core.database.entity.Category
import com.example.inventorymgmt.feature.home.viewmodel.HomeViewModel
import com.example.inventorymgmt.feature.home.viewmodel.WorkerViewModel
import com.example.inventorymgmt.feature.item.data.ItemRepositoryImpl

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToItemList: (Category) -> Unit,
    navigateToSettings: () -> Boolean
) {
    val context = LocalContext.current
    val applicationContext = context.applicationContext
    val database: InventoryDatabase = remember(applicationContext) {
        InventoryDatabase.getDatabase(applicationContext)
    }

    // get the dao instance
    val itemDao = remember { database.itemDao() }

    val viewModel: HomeViewModel = viewModel {
        HomeViewModel(ItemRepositoryImpl(itemDao))
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // WorkerViewModel for joke

    val workManager = WorkManager.getInstance(context)
    val workerViewModel: WorkerViewModel = viewModel {
        WorkerViewModel(workManager)
    }

    val workerState by workerViewModel.workerState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        workerViewModel.scheduleJokeWorker()
        workerViewModel.observeWorker("joke_worker")
    }

    Column(modifier = modifier) {
        Text(
            text = "Today's Joke",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 4.dp),
            textAlign = TextAlign.Center
        )
        // Joke text styled
        Text(
            text = workerState.outputData ?: "",
            fontSize = 20.sp,
            color = Color(0xFF3F51B5),
            fontFamily = FontFamily.Cursive,
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Item Categories",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )
        LazyColumn {
            items(uiState.categories) { category ->
                ListItem(
                    headlineContent = {
                        Text(text = category.name)
                    },
                    modifier = Modifier.clickable {
                        navigateToItemList(category)
                    }
                )
                HorizontalDivider()
            }
            item {
                ListItem(
                    headlineContent = {
                        Text(text = "Settings")
                    },
                    modifier = Modifier.clickable {
                        navigateToSettings()
                    }
                )
            }
        }
    }
}