package com.example.inventorymgmt.feature.home.state

import androidx.work.WorkInfo

data class WorkerState(
    val state: WorkInfo.State? = null,
    val outputData: String? = null,
    val errorMessage: String? = null
)

