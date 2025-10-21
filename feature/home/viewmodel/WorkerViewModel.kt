package com.example.inventorymgmt.feature.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.ExistingPeriodicWorkPolicy
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.inventorymgmt.feature.home.state.WorkerState
import com.example.inventorymgmt.core.worker.JokeWorker
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WorkerViewModel(
    private val workManager: WorkManager
) : ViewModel() {
    private val _workerState = MutableStateFlow(WorkerState())
    val workerState: StateFlow<WorkerState> = _workerState.asStateFlow()

    fun observeWorker(workerTag: String) {
        viewModelScope.launch {
            workManager.getWorkInfosByTagLiveData(workerTag).observeForever { infos ->
                val info = infos.firstOrNull()
                _workerState.update {
                    it.copy(
                        state = info?.state,
                        outputData = info?.outputData?.getString("joke"),
                        errorMessage =
                            if (info?.state == WorkInfo.State.FAILED) info.outputData.getString("error") else null
                    )
                }
            }
        }
    }

    fun scheduleJokeWorker() {
        val request = PeriodicWorkRequestBuilder<JokeWorker>(30, TimeUnit.MINUTES)
            .addTag("joke_worker")
            .build()
        workManager.enqueueUniquePeriodicWork(
            "joke_worker",
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }
}
