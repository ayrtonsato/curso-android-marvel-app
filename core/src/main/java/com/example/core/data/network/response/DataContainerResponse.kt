package com.example.core.data.network.response

data class DataContainerResponse(
    /*val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,*/
    val results: List<CharacterResponse>
)