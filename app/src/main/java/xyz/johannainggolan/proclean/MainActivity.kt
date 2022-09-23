package xyz.johannainggolan.proclean

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import org.koin.androidx.viewmodel.ext.android.viewModel
import xyz.johannainggolan.appnetworking.apiinterface.RestService
import xyz.johannainggolan.appnetworking.repositories.PhotoRepo
import xyz.johannainggolan.appnetworking.viewmodels.PhotoViewModel

class MainActivity : AppCompatActivity() {


    private val vm by viewModel<PhotoViewModel>()
    lateinit var tvResponse: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvResponse = findViewById(R.id.tv_main)
        val retrofitService = RestService.getInstance()
        val mainRepository = PhotoRepo(retrofitService)
        vm._lvdDataResponse.observe(this) {
            if(!it.isNullOrEmpty()){
                tvResponse.text = it
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.getPhotos()
    }
}