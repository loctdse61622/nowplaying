package tiki.com.nowplaying.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import tiki.com.nowplaying.detail.MovieDetailActivity
import tiki.com.nowplaying.detail.MovieDetailActivityModule
import tiki.com.nowplaying.main.MainActivity
import tiki.com.nowplaying.main.MainActivityModule

/**
 * Created by Admin on 5/4/2018.
 */
@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(MovieDetailActivityModule::class)])
    abstract fun bindMovieDetailActivity(): MovieDetailActivity
}