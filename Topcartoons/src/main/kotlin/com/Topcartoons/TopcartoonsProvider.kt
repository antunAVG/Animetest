package com.Topcartoons

import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
import com.lagradost.cloudstream3.plugins.Plugin
import android.content.Context

@CloudstreamPlugin
class TopcartoonsProvider: Plugin() {
    override fun load(context: Context) {
        registerMainAPI(Topcartoons())
    }
}