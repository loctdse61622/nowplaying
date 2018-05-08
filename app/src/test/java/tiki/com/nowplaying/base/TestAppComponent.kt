package tiki.com.nowplaying.base

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import tiki.com.nowplaying.di.module.AppModule
import tiki.com.nowplaying.di.module.NetworkModule
import javax.inject.Singleton

/**
 * Created by Admin on 5/7/2018.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, AndroidInjectionModule::class))
interface TestAppComponent {
    fun inject(testApplication: TestApplication)
    fun inject(test: BaseTest)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun appModule(appModule: AppModule): Builder
        fun networkModule(networkModule: NetworkModule): Builder
        fun build(): TestAppComponent
    }
}