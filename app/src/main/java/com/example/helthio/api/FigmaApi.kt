package com.example.helthio.api

import com.example.helthio.model.FigmaFileResponse
import com.example.helthio.model.FigmaImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FigmaApi {
    @GET("v1/files/{file_key}")
    suspend fun getFigmaFile(
        @Path("file_key") fileKey: String,
        @Header("X-FIGMA-TOKEN") token: String
    ): Response<FigmaFileResponse>

    @GET("v1/files/{fileKey}/nodes")
    suspend fun getNodeData(
        @Path("fileKey") fileKey: String, // The file key from the Figma URL
        @Query("ids") nodeIds: String, // The node IDs to fetch
        @Header("X-FIGMA-TOKEN") token: String // The Figma token
    ): Response<FigmaFileResponse> // The expected response from the Figma API

    @GET("v1/images/{file_key}")
    suspend fun getImages(
        @Path("file_key") fileKey: String,
        @Query("ids") nodeIds: String,
        @Header("X-FIGMA-TOKEN") token: String
    ): Response<FigmaImagesResponse>

}
