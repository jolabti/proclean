package xyz.johannainggolan.appnetworking.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import xyz.johannainggolan.appnetworking.models.Photo
import xyz.johannainggolan.appnetworking.repositories.PhotoRepo

class PhotoViewModel constructor(val photoRepo: PhotoRepo) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Photo>>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getPhotos() {
        job = CoroutineScope(Dispatchers.IO+exceptionHandler).launch {
            val resultApi = photoRepo.getPhotos()
            withContext(Dispatchers.Main) {
                Log.d("trace-resbody", resultApi?.body().toString())
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}