package com.example.burnbook.model.response

data class PageResponse<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Int,
    val number: Int,
    val last: Boolean
)
