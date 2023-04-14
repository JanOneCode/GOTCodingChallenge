package com.onecode.jan.got.detail

data class UiHouseDetail(
    val name: String,
    val region: String? = null,
    val coatOfArms: String? = null,
    val words: String? = null,
    val titles: List<String>? = null,
    val seats: List<String>? = null,
    val founded: String? = null,
    val diedOut: String? = null,
    val ancestralWeapons: List<String>? = null,
)