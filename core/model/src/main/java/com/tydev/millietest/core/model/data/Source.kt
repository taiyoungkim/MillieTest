package com.tydev.millietest.core.model.data

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?,
    val name: String
)
