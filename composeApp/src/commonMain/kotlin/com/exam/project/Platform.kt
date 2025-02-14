package com.exam.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform