package com.company.autocontrol.data.service

import com.company.autocontrol.data.model.user.User
import retrofit2.http.GET

interface RoadSectionService {
    @GET("/v1/roadsection/info")
    suspend fun getInfo(): User
}