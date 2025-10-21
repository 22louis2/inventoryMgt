package com.example.inventorymgmt.feature.data.remote.dto

data class JokeResponse(
    val type: String,
    val joke: String?,        // for "single"
    val setup: String?,       // for "twopart"
    val delivery: String?     // for "twopart"
)
