package com.example.inventorymgmt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.inventorymgmt.nav.AppNavGraph
import com.example.inventorymgmt.ui.theme.InventoryMgmtTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InventoryMgmtTheme {
                AppNavGraph()
            }
        }
    }
}