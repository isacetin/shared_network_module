package com.isacetin.shared_network_module

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform