package com.onecode.jan.got.model

fun String.returnStringOrNull(): String? = this.ifEmpty { null }

fun List<String>.returnListOrNull(): List<String>? =
    if (this.size == 1 && this[0].isEmpty()) null else this