package xyz.johannainggolan.appnetworking

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CoreApplication)
            modules(
                viewModuleCollections,
                serviceModule,
                networkConnectionModule,
                repoModule
            )
        }
    }
}