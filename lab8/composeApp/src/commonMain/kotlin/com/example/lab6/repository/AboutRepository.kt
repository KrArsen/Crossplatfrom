package com.example.lab6.repository

interface AboutRepository {
    fun getAppName(): String
    fun getVersion(): String
    fun getAuthor(): String
    fun getDescription(): String
}

class AboutRepositoryImpl : AboutRepository {
    override fun getAppName(): String = "Diagnostics App"
    override fun getVersion(): String = "1.0.0"
    override fun getAuthor(): String = "Student"
    override fun getDescription(): String = "Kotlin Multiplatform application with dynamic Material3 theme and Kermit logger."
}
