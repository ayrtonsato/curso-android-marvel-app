package com.example.marvelapp.factory.response

import com.example.core.domain.model.Character
import com.example.core.domain.model.CharacterPaging
import com.example.marvelapp.framework.network.response.CharacterResponse
import com.example.marvelapp.framework.network.response.DataContainerResponse
import com.example.marvelapp.framework.network.response.DataWrapperResponse
import com.example.marvelapp.framework.network.response.ThumbnailResponse

class CharacterPagingFactory {
    fun create() = CharacterPaging(
        offset = 0,
        total = 2,
        characters = listOf(
            Character(
                10111334,
                "3-D Man",
                "https://image.us/3dman.jpg"
            ),
            Character(
                10111335,
                "A-Bomb",
                "https://image.us/abomb.jpg"
            )
        )
    )
}