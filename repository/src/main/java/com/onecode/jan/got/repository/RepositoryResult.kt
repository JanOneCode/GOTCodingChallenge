package com.onecode.jan.got.repository

sealed interface RepositoryResult<out T>{
    data class Success<T>(val data: T) : RepositoryResult<T>
    data class Error(val error: Exception) : RepositoryResult<Nothing>
}