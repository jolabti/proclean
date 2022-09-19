package xyz.johannainggolan.proclean

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import xyz.johannainggolan.appnetworking.apiinterface.RestService
import xyz.johannainggolan.appnetworking.repositories.PhotoRepo
import xyz.johannainggolan.appnetworking.viewmodels.MyViewModelFactory
import xyz.johannainggolan.appnetworking.viewmodels.PhotoViewModel

class MainActivity : AppCompatActivity() {

    var vm: PhotoViewModel? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofitService = RestService.getInstance()
        val mainRepository = PhotoRepo(retrofitService)
        vm = ViewModelProvider(this, MyViewModelFactory(mainRepository)).get(PhotoViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        vm?.getPhotos()
    }
}