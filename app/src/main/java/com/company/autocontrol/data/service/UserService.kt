package com.company.autocontrol.data.service

import com.company.autocontrol.data.model.user.User
import retrofit2.Call
import retrofit2.http.GET

interface UserService {
    @GET("/v1/user/info")
    fun getInfo(): Call<User>
}
