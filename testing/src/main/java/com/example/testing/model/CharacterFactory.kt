package com.example.testing.model

import com.example.core.domain.model.Character

class CharacterFactory {
    fun create(hero: Hero) = when (hero) {
        Hero.ThreeDMan -> Character(
            10111334,
            "3-D Man",
            "https://image.us/3dman.jpg"
        )
        Hero.ABomb -> Character(
            10111335,
            "A-Bomb",
            "https://image.us/abomb.jpg"
        )
    }

    sealed class Hero {
        object ThreeDMan : Hero()
        object ABomb : Hero()
    }
}