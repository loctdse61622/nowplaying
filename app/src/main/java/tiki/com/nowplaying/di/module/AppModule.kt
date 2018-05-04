package tiki.com.nowplaying.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Admin on 5/4/2018.
 */
@Module
class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext
}