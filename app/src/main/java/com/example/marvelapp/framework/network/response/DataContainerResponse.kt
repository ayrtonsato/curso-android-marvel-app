package com.example.marvelapp.framework.network.response

data class DataContainerResponse(
    /*val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,*/
    val results: List<CharacterResponse>
)