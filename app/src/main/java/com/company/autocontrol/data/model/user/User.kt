package com.company.autocontrol.data.model.user

data class User(
    val firstname: String,
    val surname: String,
    val department: String,
    val login: String,
    val role: Role
)
