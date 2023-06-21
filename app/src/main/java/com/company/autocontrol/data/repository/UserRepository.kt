package com.company.autocontrol.data.repository

import android.content.SharedPreferences
import com.company.autocontrol.data.model.user.User
import com.company.autocontrol.data.model.user.UserLogin
import com.company.autocontrol.data.service.UserService
import com.company.autocontrol.data.util.awaitResponseWithExceptionThrowing
import javax.inject.Inject
import javax.inject.Provider

class UserRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val userService: Provider<UserService>
) {

    fun getUser(withRole: Boolean = false): UserLogin? {
        val login = sharedPreferences.getString("login", null)
        val password = sharedPreferences.getString("password", null)
        val role = sharedPreferences.contains("role") || !withRole

        return if (login != null && password != null && role) UserLogin(login, password) else null
    }

    private fun saveUser(userLogin: UserLogin) {
        sharedPreferences.edit()
            .putString("login", userLogin.login)
            .putString("password", userLogin.password)
            .apply()
    }

    suspend fun getInfo(): User {
        return userService.get().getInfo().awaitResponseWithExceptionThrowing()!!
    }

    suspend fun fetchUser(userLogin: UserLogin) {
        saveUser(userLogin) // сохраняем пользователя, чтобы перехватчик использовать креды для авторизации
        val user = getInfo() // запрашиваем информацию о пользователе, чтобы проверить верны ли креды
        println(user)
        sharedPreferences.edit()
            .putInt("role", user.role.ordinal)
            .apply()
    }
}
