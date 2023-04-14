package com.onecode.jan.got.detail

data class UiHouseDetail(
    val name: String = "",
    val region: String = "",
    val coatOfArms: String = "",
    val words: String = "",
    val titles: List<String> = listOf(""),
    val seats: List<String> = listOf(""),
    val founded: String = "",
    val diedOut: String = "",
    val ancestralWeapons: List<String> = listOf(""),
)