package xyz.johannainggolan.appnetworking.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.johannainggolan.appnetworking.repositories.PhotoRepo

class MyViewModelFactory constructor(private val repository: PhotoRepo): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            PhotoViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
