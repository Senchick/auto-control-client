package com.company.autocontrol.data.repository

import com.company.autocontrol.data.model.booking.RoadSection
import com.company.autocontrol.data.service.RoadSectionService
import com.company.autocontrol.data.util.awaitResponseWithExceptionThrowing
import javax.inject.Inject
import javax.inject.Provider

class RoadSectionRepository @Inject constructor(
    private val roadSectionService: Provider<RoadSectionService>
) {
    suspend fun getAll(): List<RoadSection> {
        return roadSectionService.get().getAll().awaitResponseWithExceptionThrowing()!!
    }
}
