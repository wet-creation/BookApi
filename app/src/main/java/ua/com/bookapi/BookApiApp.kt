package ua.com.bookapi

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import ua.com.bookapi.core.di.DbModule
import ua.com.bookapi.core.di.NetworkModule
import ua.com.bookapi.di.MainModule
import ua.com.bookapi.features.auth.di.AuthModule
import ua.com.bookapi.features.books.di.BookModule

val ApplicationModule = module {
    single<CoroutineScope> {
        (androidApplication() as BookApiApp).applicationScope
    }
}

class BookApiApp : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@BookApiApp)
            modules(
                ApplicationModule,
                DbModule,
                NetworkModule,
                BookModule,
                AuthModule,
                MainModule
            )
        }
    }
}

