package com.onecode.jan.got.repository

sealed interface RepositoryState<out T>{
    object Loading : RepositoryState<Nothing>
    data class Success<T>(val data: T) : RepositoryState<T>
    data class Error(val error: Exception) : RepositoryState<Nothing>
}