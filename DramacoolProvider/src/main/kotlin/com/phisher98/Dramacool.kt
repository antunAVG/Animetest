package com.phisher98

import android.util.Log
import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.LoadResponse.Companion.addActors
import com.lagradost.cloudstream3.extractors.helper.GogoHelper
import com.lagradost.cloudstream3.utils.AppUtils.toJson
import com.lagradost.cloudstream3.utils.AppUtils.tryParseJson
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.httpsify
import com.lagradost.cloudstream3.utils.loadExtractor
import org.jsoup.nodes.Element

class Dramacool : MainAPI() {
    override val supportedTypes = setOf(
        TvType.AsianDrama
    )
    override var lang = "en"

    override var mainUrl = "https://asianc.co"
    override var name = "Dramacool"

    override val hasMainPage = true

    override val mainPage = mainPageOf(
        "most-popular-drama" to "Popular Drama",
        "genre/crime.html" to "Crime Series",
    )

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val document = app.get("$mainUrl/${request.data}?page=$page", timeout = 30L).document
        val items = document.select("ul.switch-block.list-episode-item li").mapNotNull {
            it.toSearchResult()
        }

        return newHomePageResponse(request.name, items)
    }

    private fun Element.toSearchResult(): SearchResponse? {
        val title = selectFirst("h3")?.text() ?: return null
        val href = fixUrlNull(selectFirst("a")?.attr("href")) ?: return null
        val posterUrl = fixUrlNull(selectFirst("a img")?.attr("data-original"))
        return newTvSeriesSearchResponse(title, href,TvType.TvSeries) {
            this.posterUrl = posterUrl
        }
    }


    override suspend fun search(query: String): List<SearchResponse> {
        val searchtitle=query.createSlug()
        val url = "$mainUrl/search?type=movies&keyword=$searchtitle"
        val document = app.get(url, referer = "$mainUrl/").document
        val items = document.select("ul.switch-block.list-episode-item li").mapNotNull {
            it.toSearchResult()
        }
        return items
    }

    override suspend fun load(url: String): LoadResponse? {
        val document = app.get(url, referer = "$mainUrl/", timeout = 10L).document
        val title = document.selectFirst("h1")?.text()?.trim() ?: return null
        val actors = document.select("div.item a").map {
            Actor(
                it.select("h3").text(),
                it.select("img").attr("src")
            )
        }
        val description=document.selectFirst("div.info > p:nth-child(6)")?.text()?.trim() ?: return null
        val posterurl=document.selectFirst("div.details img")?.attr("src") ?: return null
        val episodes = document.select("ul.list-episode-item-2 li").mapNotNull { el ->
            val name=el.selectFirst("a h3")?.text()?.substringAfter("Episode")?.trim()
            val href=el.selectFirst("a")?.attr("href") ?:""
            Episode(href, "Episode $name")
        }.reversed()

        return newTvSeriesLoadResponse(title, url, TvType.TvSeries, episodes) {
            posterUrl = posterurl
            addActors(actors)
            this.plot=description
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val url= app.get("$mainUrl$data").document.selectFirst("div.anime_muti_link ul li.kvid")?.attr("data-video") ?:""
        val iframe = app.get(httpsify(url))
        val iframeDoc = iframe.document
        argamap({
            iframeDoc.select(".list-server-items > .linkserver")
                .amap { element ->
                    val extractorData = element.attr("data-video").substringBefore("=http")
                    val status = element.attr("data-status") ?: return@amap
                    if (status != "1") return@amap
                    if (extractorData.contains("asianbxkiun"))
                    {
                        Log.d("Not Found","Error")
                    }
                    else
                    loadExtractor(extractorData, iframe.url, subtitleCallback, callback)
                }
        }, {
            val iv = "9262859232435825"
            val secretKey = "93422192433952489752342908585752"
            val secretDecryptKey = secretKey
            GogoHelper.extractVidstream(
                iframe.url,
                this.name,
                callback,
                iv,
                secretKey,
                secretDecryptKey,
                isUsingAdaptiveKeys = false,
                isUsingAdaptiveData = true,
                iframeDocument = iframeDoc
            )
        })
        return true
    }

    fun String?.createSlug(): String? {
        return this?.filter { it.isWhitespace() || it.isLetterOrDigit() }
            ?.trim()
            ?.replace("\\s+".toRegex(), "-")
            ?.lowercase()
    }

}
