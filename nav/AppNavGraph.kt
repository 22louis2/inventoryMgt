package com.example.inventorymgmt.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.inventorymgmt.core.database.entity.Category
import com.example.inventorymgmt.feature.home.screen.HomeScreen
import com.example.inventorymgmt.feature.item.ui.screen.ItemListScreen
import com.example.inventorymgmt.feature.login.ui.screen.LoginScreen
import com.example.inventorymgmt.feature.productdetail.screen.ProductDetailScreen
import com.example.inventorymgmt.feature.setting.SettingScreen

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack<AppNavKey>(Login)
    val currentKey = backStack.lastOrNull()

    Scaffold(
        bottomBar = {
            if (currentKey == Home || currentKey == Settings) {
                NavigationBar {
                    NavigationBarItem(
                        label = { Text(text = "Home") },
                        selected = currentKey == Home,
                        onClick = {
                            backStack.clear()
                            backStack.add(Home)
                        },
                        icon = {
                            Icon(Icons.Default.Home, contentDescription = "Home")
                        }
                    )
                    NavigationBarItem(
                        label = { Text(text = "Settings") },
                        selected = currentKey == Settings,
                        onClick = {
                            backStack.clear()
                            backStack.add(Settings)
                        },
                        icon = {
                            BadgedBox(
                                badge = {
                                    if(backStack.lastOrNull() == ItemList(Category.ELECTRONICS)) {
                                        Badge {
                                            val badgeNumber = "999+"
                                            Text(
                                                badgeNumber
                                            )
                                        }
                                    }
                                }
                            ) {
                                Icon(Icons.Default.Settings, contentDescription = "Settings")
                            }
                        },
                    )
                }
            }
        }
    ) { innerPadding ->
        NavDisplay(
            backStack = backStack,
            onBack = {backStack.removeLastOrNull()},
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Home> {
                    HomeScreen(
                        navigateToItemList = { category: Category ->
                            backStack.add(ItemList(category))
                        },
                        navigateToSettings = {
                            backStack.add(Settings)
                        },
                        modifier = modifier
                    )
                }
                entry<ItemList> { item: ItemList ->
                    // content for product list goes here // ProductListScreen(key.category)
                    val category: Category = item.category
                    ItemListScreen(
                        category = category,
                        navigateToProductDetail = { itemId: Int ->
                            backStack.add(ProductDetail(itemId))
                        }
                    )
                }
                entry<ProductDetail> {productDetail: ProductDetail ->
                    // content for product detail goes here // ProductDetailScreen(key.productId)
                    val id: Int = productDetail.itemId
                    ProductDetailScreen(modifier,id)
                }
                entry<Settings>{
                    // content for settings goes here // SettingScreen()
                    SettingScreen(modifier)
                }
                entry<Login> {
                    LoginScreen(
                        onLoginSuccess = {
                            backStack.clear()
                            backStack.add(Home)
                        },
                        modifier = modifier
                    )
                }
            },
            modifier = modifier
                .padding(innerPadding)
        )
    }
}