package com.egoriku.grodnoroads

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.egoriku.grodnoroads.common.datastore.DataFlow.language
import com.egoriku.grodnoroads.common.datastore.dataStore
import com.egoriku.grodnoroads.util.LocalizationUtil
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import java.util.*

abstract class LocalizedActivity : AppCompatActivity() {

    private var currentLocale: Locale? = null

    init {
        lifecycleScope.launchWhenCreated {
            this@LocalizedActivity.dataStore.data.collectLatest { preferences ->
                currentLocale = Locale(preferences.language.lang)

                LocalizationUtil.applyLanguageContext(
                    context = this@LocalizedActivity,
                    locale = this@LocalizedActivity.getLocale()
                )
            }
        }
    }

    override fun getApplicationContext(): Context {
        val context = super.getApplicationContext()
        return LocalizationUtil.applyLanguageContext(context, context.getLocale())
    }

    override fun getBaseContext(): Context {
        val context = super.getBaseContext()
        return LocalizationUtil.applyLanguageContext(context, context.getLocale())
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocalizationUtil.applyLanguageContext(
                newBase,
                newBase.getLocale()
            )
        )
    }

    private fun Context.getLocale(): Locale {
        if (currentLocale == null) {
            runBlocking {
                this@getLocale.dataStore.data
                    .take(1)
                    .map { it.language }
                    .collect { currentLocale = Locale(it.lang) }
            }
        }

        return currentLocale!!
    }
}