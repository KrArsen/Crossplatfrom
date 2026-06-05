package com.example.lab6

data class SystemInfo(
    val osName: String,
    val osVersion: String,
    val deviceModel: String
)

expect fun getSystemInfo(): SystemInfo
