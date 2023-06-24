package com.company.autocontrol.data.service

import com.company.autocontrol.data.model.booking.RoadSection
import retrofit2.Call
import retrofit2.http.GET

interface RoadSectionService {
    @GET("/v1/user/road-section/all")
    fun getAll(): Call<List<RoadSection>>
}
