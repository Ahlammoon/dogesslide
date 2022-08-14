package com.example.dogesslide.api

import com.example.dogesslide.model.AnimalImageModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {



    @GET("/api/breeds/image/random")
    suspend fun  getRandomAnimalImage() : Response<AnimalImageModel>// name of the model calss
}