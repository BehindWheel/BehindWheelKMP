package com.egoriku.grodnoroads.data.api

import com.egoriku.grodnoroads.data.model.ActionResponse
import com.github.theapache64.retrosheet.annotations.Write
import retrofit2.http.Body
import retrofit2.http.POST

interface GrodnoRoadsApi {

    @Write
    @POST(FORM_REPORT_ACTION)
    suspend fun sendReport(@Body actionResponse: ActionResponse): ActionResponse
}