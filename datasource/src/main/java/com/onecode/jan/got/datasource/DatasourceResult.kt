package com.onecode.jan.got.datasource

sealed interface DatasourceResult<out T>{
    data class Success<T>(val data: T) : DatasourceResult<T>
    data class Error(val error: Exception) : DatasourceResult<Nothing>
}