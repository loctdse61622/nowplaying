package tiki.com.nowplaying.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import tiki.com.nowplaying.application.MainApplication
import tiki.com.nowplaying.di.ActivityBuilder
import tiki.com.nowplaying.di.module.AppModule
import tiki.com.nowplaying.di.module.NetworkModule
import javax.inject.Singleton

/**
 * Created by Admin on 5/4/2018.
 */
@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class),(AndroidInjectionModule::class), (ActivityBuilder::class)])
interface AppComponent {

    fun inject(mainApplication: MainApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun build(): AppComponent
    }
}