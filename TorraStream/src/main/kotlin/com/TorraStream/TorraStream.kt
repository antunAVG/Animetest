package com.TorraStream

//import android.util.Log
import com.lagradost.api.Log
import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.APIHolder.capitalize
import com.lagradost.cloudstream3.metaproviders.TraktProvider
import com.lagradost.cloudstream3.syncproviders.SyncIdName
import com.lagradost.cloudstream3.utils.*

class TorraStream() : TraktProvider() {
    override var name = "TorraStream"
    override var supportedTypes = setOf(TvType.Movie, TvType.TvSeries, TvType.AsianDrama,TvType.Torrent)
    override var lang = "en"
    override val supportedSyncNames = setOf(SyncIdName.Trakt)
    override val hasMainPage = true
    override val hasQuickSearch = false

    companion object
    {
        val ServerURL="https://torrentio.strem.fun"
    }

    private val traktApiUrl = base64Decode("aHR0cHM6Ly9hcGl6LnRyYWt0LnR2")

    protected fun Any.toStringData(): String {
        return mapper.writeValueAsString(this)
    }

    override val mainPage =
        mainPageOf(
            "$traktApiUrl/movies/trending?extended=cloud9,full&limit=25" to
                    "Trending Movies",
            "$traktApiUrl/movies/popular?extended=cloud9,full&limit=25" to "Popular Movies",
            "$traktApiUrl/shows/trending?extended=cloud9,full&limit=25" to "Trending Shows",
            "$traktApiUrl/shows/popular?extended=cloud9,full&limit=25" to "Popular Shows",
            "$traktApiUrl/shows/trending?extended=cloud9,full&limit=25&network_ids=53,1465" to
                    "Netflix",
            "$traktApiUrl/shows/trending?extended=cloud9,full&limit=25&network_ids=47,2385" to
                    "Amazon Prime Video",
            "$traktApiUrl/shows/trending?extended=cloud9,full&limit=25&network_ids=256" to
                    "Apple TV+",
            "$traktApiUrl/shows/trending?extended=cloud9,full&limit=25&network_ids=41,2018,2566,2567,2597" to
                    "Disney+",
            "$traktApiUrl/shows/trending?extended=cloud9,full&limit=25&network_ids=87" to
                    "Hulu",
            "$traktApiUrl/shows/trending?extended=cloud9,full&limit=25&network_ids=1623" to
                    "Paramount+",
            "$traktApiUrl/shows/trending?extended=cloud9,full&limit=25&network_ids=550,3027" to
                    "Peacock",
            //"$traktApiUrl/shows/trending?extended=cloud9&genres=anime,full&limit=25&extended=full" to "Trending Animes"
        )

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val TRACKER_LIST_URL="https://raw.githubusercontent.com/ngosang/trackerslist/refs/heads/master/trackers_all.txt"
        val season =AppUtils.parseJson<LinkData>(data).season
        val episode =AppUtils.parseJson<LinkData>(data).episode
        val id =AppUtils.parseJson<LinkData>(data).imdbId
        val torrentioAPI:String
        if (mainUrl.contains(","))
            {
                val splitdata = mainUrl.split(",")
                val service = splitdata[0]
                val key= splitdata[1]
                torrentioAPI="$ServerURL/$service=$key"
                val url = if(season == null) {
                    "$torrentioAPI/stream/movie/$id.json"
                }
                else {
                    "$torrentioAPI/stream/series/$id:$season:$episode.json"
                }
                Log.d("Phisher",url)
                val headers = mapOf(
                    "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8",
                    "User-Agent" to "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36",
                )
                val res = app.get(url, headers = headers, timeout = 100L).parsedSafe<DebianRoot>()
                res?.streams?.forEach { stream ->
                    callback.invoke(
                        ExtractorLink(
                            "Torrentio",
                            stream.title,
                            stream.url,
                            "",
                            getIndexQuality(stream.name),
                            INFER_TYPE,
                        )
                    )
                }
            }
            else
            {
                torrentioAPI=ServerURL
                val url = if(season == null) {
                    "$torrentioAPI/stream/movie/$id.json"
                }
                else {
                    "$torrentioAPI/stream/series/$id:$season:$episode.json"
                }
                Log.d("Phisher",url)
                val headers = mapOf(
                    "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8",
                    "User-Agent" to "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36",
                )
                val res = app.get(url, headers = headers, timeout = 100L).parsedSafe<TorrentioResponse>()
                res?.streams?.forEach { stream ->
                    val magnet = generateMagnetLink(TRACKER_LIST_URL, stream.infoHash)
                    callback.invoke(
                        ExtractorLink(
                            "Torrentio",
                            stream.title ?: stream.name ?: "",
                            magnet,
                            "",
                            getIndexQuality(stream.name),
                            INFER_TYPE,
                        )
                    )
                }
            }
        val SubAPI="https://opensubtitles-v3.strem.io"
        val url = if(season == null) {
            "$SubAPI/subtitles/movie/$id.json"
        }
        else {
            "$SubAPI/subtitles/series/$id:$season:$episode.json"
        }
        val headers = mapOf(
            "Accept" to "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8",
            "User-Agent" to "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36",
        )
        Log.d("Phisher", url)
        app.get(url, headers = headers, timeout = 100L).parsedSafe<Subtitles>()?.subtitles?.amap {
            val lan=it.lang
            val suburl=it.url
            Log.d("Phisher","$lan $suburl")
            subtitleCallback.invoke(
                SubtitleFile(
                    lan.capitalize(),  // Use label for the name
                    suburl     // Use extracted URL
                )
            )
        }
        return true
    }

    suspend fun generateMagnetLink(url: String, hash: String?): String {
        // Fetch the content of the file from the provided URL
        val response = app.get(url)
        val trackerList = response.text.trim().split("\n") // Assuming each tracker is on a new line

        // Build the magnet link
        return buildString {
            append("magnet:?xt=urn:btih:$hash")
            trackerList.forEach { tracker ->
                if (tracker.isNotBlank()) {
                    append("&tr=").append(tracker.trim())
                }
            }
        }
    }
}
