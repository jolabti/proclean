package xyz.johannainggolan.appnetworking.repositories

import xyz.johannainggolan.appnetworking.apiinterface.RestService

class PhotoRepo constructor(val retroService : RestService) {
    suspend fun getPhotos()=  retroService.getPhotos()
}