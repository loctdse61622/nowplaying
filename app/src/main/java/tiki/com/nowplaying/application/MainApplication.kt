package tiki.com.nowplaying.application

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import tiki.com.nowplaying.BuildConfig
import tiki.com.nowplaying.di.component.AppComponent
import tiki.com.nowplaying.di.component.DaggerAppComponent
import tiki.com.nowplaying.di.module.AppModule
import tiki.com.nowplaying.di.module.NetworkModule
import javax.inject.Inject

/**
 * Created by Admin on 5/4/2018.
 */
class MainApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private val appComponent: AppComponent = DaggerAppComponent.builder().application(this)
            .appModule(AppModule(this))
            .networkModule(NetworkModule(BuildConfig.BASE_URL))
            .build()

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }
}