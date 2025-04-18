package com.hi_lo

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CourseService {

    @GET("api/v1/courses")
    suspend fun getCourses(@Header("Authorization") token: String): List<Course>

    @POST("api/v1/login")
    suspend fun login(@Body auth: AuthBody): Response<AuthResponse>
}