package com.egoriku.grodnoroads.data.api

import com.egoriku.grodnoroads.data.model.ActionResponse
import com.egoriku.grodnoroads.data.model.StationaryResponse
import com.github.theapache64.retrosheet.annotations.Read
import com.github.theapache64.retrosheet.annotations.Write
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GrodnoRoadsApi {
    @Read("SELECT *")
    @GET(SHEET_ROAD_EVENTS)
    suspend fun getRoadEvents(): List<ActionResponse>

    @Read
    @GET(SHEET_STATIONARY_CAMERA)
    suspend fun getStationaryCameras(): List<StationaryResponse>

    @Write
    @POST(FORM_REPORT_ACTION)
    suspend fun sendReport(@Body actionResponse: ActionResponse): ActionResponse
}