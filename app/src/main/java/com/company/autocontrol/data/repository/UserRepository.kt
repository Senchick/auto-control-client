package com.company.autocontrol.data.repository

import android.content.SharedPreferences
import com.company.autocontrol.data.model.user.UserLogin
import javax.inject.Inject

class UserRepository @Inject constructor(private val sharedPreferences: SharedPreferences) {

    fun getUser(): UserLogin? {
        val login = sharedPreferences.getString("login", null)
        val password = sharedPreferences.getString("password", null)

        return if (login != null && password != null) UserLogin(login, password) else null
    }

    fun saveUser(userLogin: UserLogin) {
        with(sharedPreferences.edit()) {
            putString("login", userLogin.login)
            putString("password", userLogin.password)
            apply()
        }
    }
}
