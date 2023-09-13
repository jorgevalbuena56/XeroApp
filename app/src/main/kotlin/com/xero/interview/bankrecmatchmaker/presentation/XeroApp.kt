package com.xero.interview.bankrecmatchmaker.presentation

import android.app.Application
import com.xero.interview.bankrecmatchmaker.data.XeroRepository
import com.xero.interview.bankrecmatchmaker.data.XeroRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class XeroApp : Application() {

    private val appModule = module {
        single<XeroRepository> { XeroRepositoryImpl() }
        viewModelOf(::FindMatchViewModel)
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@XeroApp)
            modules(appModule)
        }
    }
}