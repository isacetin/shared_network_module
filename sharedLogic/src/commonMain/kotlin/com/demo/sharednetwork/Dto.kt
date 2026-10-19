package com.demo.sharednetwork

import kotlinx.serialization.Serializable

@Serializable
internal data class AddressDto(val city: String)

@Serializable
internal data class UserDto(
    val id: Int,
    val name: String,
    val email: String,
    val address: AddressDto,
)

@Serializable
internal data class PostDto(
    val id: Int,
    val title: String,
    val body: String,
)
