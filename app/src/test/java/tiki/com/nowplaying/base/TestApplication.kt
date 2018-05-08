package tiki.com.nowplaying.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import org.robolectric.TestLifecycleApplication
import tiki.com.nowplaying.BuildConfig
import tiki.com.nowplaying.di.module.AppModule
import tiki.com.nowplaying.di.module.NetworkModule
import java.lang.reflect.Method
import javax.inject.Inject

/**
 * Created by Admin on 5/7/2018.
 */
class TestApplication : Application(), HasActivityInjector, TestLifecycleApplication {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    private val appComponent: TestAppComponent = DaggerTestAppComponent.builder().application(this)
            .appModule(AppModule(this))
            .networkModule(NetworkModule(BuildConfig.BASE_URL))
            .build()

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    fun getAppComponent(): TestAppComponent {
        return appComponent
    }

    override fun beforeTest(method: Method) {
        getAppComponent()
    }

    override fun prepareTest(test: Any) {
        if (test is BaseTest) {
            getAppComponent().inject(test = test)
        }
    }

    override fun afterTest(method: Method) {
    }

}