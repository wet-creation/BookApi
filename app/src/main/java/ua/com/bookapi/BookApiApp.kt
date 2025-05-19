package ua.com.bookapi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import ua.com.bookapi.core.di.DbModule
import ua.com.bookapi.core.di.NetworkModule
import ua.com.bookapi.di.MainModule
import ua.com.bookapi.features.auth.di.AuthModule

class BookApiApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BookApiApp)
            modules(
                DbModule,
                NetworkModule,
                AuthModule,
                MainModule
            )
        }
    }
}