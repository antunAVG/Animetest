package com.Phisher98

import com.fasterxml.jackson.annotation.JsonProperty
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

//FlixAPI

data class FlixAPI(
    val source: String,
    val subtitles: List<FlixAPISubtitle>,
)

data class FlixAPISubtitle(
    val url: String,
    val lang: String,
)


//Anichi

data class Anichi(
    val data: AnichiData,
)

data class AnichiData(
    val shows: AnichiShows,
)

data class AnichiShows(
    val pageInfo: PageInfo,
    val edges: List<Edge>,
)

data class PageInfo(
    val total: Long,
)

data class Edge(
    @JsonProperty("_id")
    val id: String,
    val name: String,
    val englishName: String,
    val nativeName: String,
)

data class LastEpisodeInfo(
    val sub: Sub,
    val dub: Dub,
)

data class Sub(
    val episodeString: String,
)

data class Dub(
    val episodeString: String,
)

data class LastEpisodeDate(
    val sub: Sub2,
    val dub: Dub2,
    val raw: Map<String, Any>,
)

data class Sub2(
    val hour: Long,
    val minute: Long,
    val year: Long,
    val month: Long,
    val date: Long,
)

data class Dub2(
    val hour: Long,
    val minute: Long,
    val year: Long,
    val month: Long,
    val date: Long,
)

data class Season(
    val quarter: String,
    val year: Long,
)

data class AiredStart(
    val year: Long,
    val month: Long,
    val date: Long,
    val hour: Long,
    val minute: Long,
)

data class AvailableEpisodes(
    val sub: Long,
    val dub: Long,
    val raw: Long,
)

//Anichi Ep Parser

data class AnichiEP(
    val data: AnichiEPData,
)

data class AnichiEPData(
    val episode: AnichiEpisode,
)

data class AnichiEpisode(
    val sourceUrls: List<SourceUrl>,
)

data class SourceUrl(
    val sourceUrl: String,
    val sourceName: String,
    val downloads: AnichiDownloads?,
)

data class AnichiDownloads(
    val sourceName: String,
    val downloadUrl: String,
)

//Anichi Download URL Parser

data class AnichiDownload(
    val links: List<AnichiDownloadLink>,
)

data class AnichiDownloadLink(
    val link: String,
    val hls: Boolean,
    val mp4: Boolean?,
    val resolutionStr: String,
    val priority: Long,
    val src: String?,
)



data class CrunchyrollAccessToken(
    val accessToken: String? = null,
    val tokenType: String? = null,
    val bucket: String? = null,
    val policy: String? = null,
    val signature: String? = null,
    val key_pair_id: String? = null,
)

data class FDMovieIFrame(
    val link: String,
    val quality: String,
    val size: String,
    val type: String,
)

data class AniIds(var id: Int? = null, var idMal: Int? = null)

data class TmdbDate(
    val today: String,
    val nextWeek: String,
)

data class AniwaveResponse(
    val result: String
) {
    fun asJsoup(): Document {
        return Jsoup.parse(result)
    }
}

data class AniwaveServer(
    val result: Result
) {
    data class Result(
        val url: String
    ) {
        fun decrypt(): String {
            return AniwaveUtils.vrfDecrypt(url)
        }
    }
}

data class MoflixResponse(
    @JsonProperty("title") val title: Episode? = null,
    @JsonProperty("episode") val episode: Episode? = null,
) {
    data class Episode(
        @JsonProperty("id") val id: Int? = null,
        @JsonProperty("videos") val videos: ArrayList<Videos>? = arrayListOf(),
    ) {
        data class Videos(
            @JsonProperty("name") val name: String? = null,
            @JsonProperty("category") val category: String? = null,
            @JsonProperty("src") val src: String? = null,
            @JsonProperty("quality") val quality: String? = null,
        )
    }
}

data class AniMedia(
    @JsonProperty("id") var id: Int? = null,
    @JsonProperty("idMal") var idMal: Int? = null
)

data class AniPage(@JsonProperty("media") var media: java.util.ArrayList<AniMedia> = arrayListOf())

data class AniData(@JsonProperty("Page") var Page: AniPage? = AniPage())

data class AniSearch(@JsonProperty("data") var data: AniData? = AniData())

data class GpressSources(
    @JsonProperty("src") val src: String,
    @JsonProperty("file") val file: String? = null,
    @JsonProperty("label") val label: Int? = null,
    @JsonProperty("max") val max: String,
)
//AsianHDResponse
data class AsianHDResponse(
    val data: Data
)
data class Data(
    val links: List<Link>
)
data class Link(
    val type: String,
    val url: String
)

//Flicky
data class FlickyStream(
    val link: String,
    val language: String
)

//WyZIESUBAPI

data class WyZIESUB(
    val id: String,
    val url: String,
    val flagUrl: String,
    val format: String,
    val display: String,
    val language: String,
    val media: String,
    val isHearingImpaired: Boolean,
)

data class UHDBackupUrl(
    @JsonProperty("url") val url: String? = null,
)

data class ResponseHash(
    @JsonProperty("embed_url") val embed_url: String,
    @JsonProperty("key") val key: String? = null,
    @JsonProperty("type") val type: String? = null,
)

data class KisskhSources(
    @JsonProperty("Video") val video: String?,
    @JsonProperty("ThirdParty") val thirdParty: String?,
)

data class KisskhSubtitle(
    @JsonProperty("src") val src: String?,
    @JsonProperty("label") val label: String?,
)

data class KisskhEpisodes(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("number") val number: Int?,
)

data class KisskhDetail(
    @JsonProperty("episodes") val episodes: ArrayList<KisskhEpisodes>? = arrayListOf(),
)

data class KisskhResults(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("title") val title: String?,
)

data class DriveBotLink(
    @JsonProperty("url") val url: String? = null,
)

data class DirectDl(
    @JsonProperty("download_url") val download_url: String? = null,
)

data class Safelink(
    @JsonProperty("safelink") val safelink: String? = null,
)

data class FDAds(
    @JsonProperty("linkr") val linkr: String? = null,
)

data class ZShowEmbed(
    @JsonProperty("m") val meta: String? = null,
)

data class WatchsomuchTorrents(
    @JsonProperty("id") val id: Int? = null,
    @JsonProperty("movieId") val movieId: Int? = null,
    @JsonProperty("season") val season: Int? = null,
    @JsonProperty("episode") val episode: Int? = null,
)

data class WatchsomuchMovies(
    @JsonProperty("torrents") val torrents: ArrayList<WatchsomuchTorrents>? = arrayListOf(),
)

data class WatchsomuchResponses(
    @JsonProperty("movie") val movie: WatchsomuchMovies? = null,
)

data class WatchsomuchSubtitles(
    @JsonProperty("url") val url: String? = null,
    @JsonProperty("label") val label: String? = null,
)

data class WatchsomuchSubResponses(
    @JsonProperty("subtitles") val subtitles: ArrayList<WatchsomuchSubtitles>? = arrayListOf(),
)

data class WHVXSubtitle(
    val url: String,
    val languageName: String,
)

//Catflix Juicey

data class CatflixJuicy(
    val action: String,
    val success: Boolean,
    val msg: String,
    val juice: String,
    val juicePost: String,
)

data class CatflixJuicydata(
    val action: String,
    val success: Boolean,
    val msg: String,
    val data: String,
    val juice: String,
)



data class IndexMedia(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("driveId") val driveId: String? = null,
    @JsonProperty("mimeType") val mimeType: String? = null,
    @JsonProperty("size") val size: String? = null,
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("modifiedTime") val modifiedTime: String? = null,
)

data class IndexData(
    @JsonProperty("files") val files: ArrayList<IndexMedia>? = arrayListOf(),
)

data class IndexSearch(
    @JsonProperty("data") val data: IndexData? = null,
)

data class JikanExternal(
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("url") val url: String? = null,
)

data class JikanData(
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("external") val external: ArrayList<JikanExternal>? = arrayListOf(),
    val season: String,
)

data class JikanResponse(
    @JsonProperty("data") val data: JikanData? = null,
)

data class VidsrctoResult(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("url") val url: String? = null,
)

data class VidsrctoResponse(
    @JsonProperty("result") val result: VidsrctoResult? = null,
)

data class VidsrctoSources(
    @JsonProperty("result") val result: ArrayList<VidsrctoResult>? = arrayListOf(),
)

data class VidsrctoSubtitles(
    @JsonProperty("label") val label: String? = null,
    @JsonProperty("file") val file: String? = null,
)


data class SmashyRoot(
    val data: SmashyData,
    val success: Boolean,
)

data class SmashyData(
    val sources: List<Source>,
    val tracks: String,
)

data class Source(
    val file: String,
)



data class AnilistExternalLinks(
    @JsonProperty("id") var id: Int? = null,
    @JsonProperty("site") var site: String? = null,
    @JsonProperty("url") var url: String? = null,
    @JsonProperty("type") var type: String? = null,
)

data class AnilistMedia(@JsonProperty("externalLinks") var externalLinks: ArrayList<AnilistExternalLinks> = arrayListOf())

data class AnilistData(@JsonProperty("Media") var Media: AnilistMedia? = AnilistMedia())

data class AnilistResponses(@JsonProperty("data") var data: AnilistData? = AnilistData())

data class CrunchyrollToken(
    @JsonProperty("access_token") val accessToken: String? = null,
    @JsonProperty("token_type") val tokenType: String? = null,
    @JsonProperty("cms") val cms: Cms? = null,
) {
    data class Cms(
        @JsonProperty("bucket") var bucket: String? = null,
        @JsonProperty("policy") var policy: String? = null,
        @JsonProperty("signature") var signature: String? = null,
        @JsonProperty("key_pair_id") var key_pair_id: String? = null,
    )
}

data class CrunchyrollVersions(
    @JsonProperty("audio_locale") val audio_locale: String? = null,
    @JsonProperty("guid") val guid: String? = null,
)

data class CrunchyrollData(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("slug_title") val slug_title: String? = null,
    @JsonProperty("season_number") val season_number: Int? = null,
    @JsonProperty("episode_number") val episode_number: Int? = null,
    @JsonProperty("versions") val versions: ArrayList<CrunchyrollVersions>? = null,
    @JsonProperty("streams_link") val streams_link: String? = null,
)

data class CrunchyrollResponses(
    @JsonProperty("data") val data: ArrayList<CrunchyrollData>? = arrayListOf(),
)

data class CrunchyrollSourcesResponses(
    @JsonProperty("streams") val streams: Streams? = Streams(),
    @JsonProperty("subtitles") val subtitles: HashMap<String, HashMap<String, String>>? = hashMapOf(),
) {
    data class Streams(
        @JsonProperty("adaptive_hls") val adaptive_hls: HashMap<String, HashMap<String, String>>? = hashMapOf(),
        @JsonProperty("vo_adaptive_hls") val vo_adaptive_hls: HashMap<String, HashMap<String, String>>? = hashMapOf(),
    )
}
//Hianime


data class Hianime(
    val success: Boolean,
    val data: HianimeData,
)

data class HianimeData(
    val tracks: List<HianimeTrack>,
    val intro: Intro,
    val outro: Outro,
    val sources: List<HianimeSource>,
    @JsonProperty("anilistID")
    val anilistId: Long,
    @JsonProperty("malID")
    val malId: Long,
)

data class HianimeTrack(
    val file: String,
    val label: String?,
    val kind: String,
    val default: Boolean?,
)

data class Intro(
    val start: Long,
    val end: Long,
)

data class Outro(
    val start: Long,
    val end: Long,
)

data class HianimeSource(
    val url: String,
    val type: String,
)

//anime animepahe parser

data class animepahe(
    val total: Long,
    @JsonProperty("per_page")
    val perPage: Long,
    @JsonProperty("current_page")
    val currentPage: Long,
    @JsonProperty("last_page")
    val lastPage: Long,
    @JsonProperty("next_page_url")
    val nextPageUrl: Any?,
    @JsonProperty("prev_page_url")
    val prevPageUrl: Any?,
    val from: Long,
    val to: Long,
    val data: List<Daum>,
)

data class Daum(
    val id: Long,
    @JsonProperty("anime_id")
    val animeId: Long,
    val episode: Int,
    val episode2: Long,
    val edition: String,
    val title: String,
    val snapshot: String,
    val disc: String,
    val audio: String,
    val duration: String,
    val session: String,
    val filler: Long,
    @JsonProperty("created_at")
    val createdAt: String,
)


data class MALSyncSites(
    @JsonProperty("Gogoanime") val Gogoanime: HashMap<String?, HashMap<String, String?>>? = hashMapOf(),
    @JsonProperty("Zoro") val zoro: HashMap<String?, HashMap<String, String?>>? = hashMapOf(),
    @JsonProperty("9anime") val nineAnime: HashMap<String?, HashMap<String, String?>>? = hashMapOf(),
    @JsonProperty("animepahe") val animepahe: HashMap<String?, HashMap<String, String?>>? = hashMapOf(),
)

data class MALSyncResponses(
    @JsonProperty("Sites") val sites: MALSyncSites? = null,
)

data class HianimeResponses(
    @JsonProperty("html") val html: String? = null,
    @JsonProperty("link") val link: String? = null,
)

data class MalSyncRes(
    @JsonProperty("Sites") val Sites: Map<String, Map<String, Map<String, String>>>? = null,
)

data class GokuData(
    @JsonProperty("link") val link: String? = null,
)

data class GokuServer(
    @JsonProperty("data") val data: GokuData? = GokuData(),
)
//Tom

data class TomResponse (
    var videoSource    : String,
    var subtitles      : ArrayList<TomSubtitles> = arrayListOf(),
)

data class TomSubtitles (
    var file    : String,
    var label   : String
)

//Gojo

data class Gojoresponseshashh(
    val sources: List<shashhSource>,
    val thumbs: String,
    val skips: Any?,
)

data class shashhSource(
    val quality: String,
    val url: String,
)

data class Gojoresponsevibe(
    val sources: List<vibeSource>,
    val skips: Any?,
)

data class vibeSource(
    val url: String,
    val quality: String,
    val type: String,
)


//MiruroanimeGogo

data class MiruroanimeGogo(
    val sources: List<MiruroSource>,
    val download: String,
)

data class MiruroSource(
    val url: String,
    val isM3U8: Boolean,
    val quality: String,
)


data class AllMovielandEpisodeFolder(
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("file") val file: String? = null,
)

data class AllMovielandSeasonFolder(
    @JsonProperty("episode") val episode: String? = null,
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("folder") val folder: ArrayList<AllMovielandEpisodeFolder>? = arrayListOf(),
)

data class AllMovielandServer(
    @JsonProperty("title") val title: String? = null,
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("file") val file: String? = null,
    @JsonProperty("folder") val folder: ArrayList<AllMovielandSeasonFolder>? = arrayListOf(),
)

data class AllMovielandPlaylist(
    @JsonProperty("file") val file: String? = null,
    @JsonProperty("key") val key: String? = null,
    @JsonProperty("href") val href: String? = null,
)

data class DumpMedia(
    @JsonProperty("id") val id: String? = null,
    @JsonProperty("domainType") val domainType: Int? = null,
    @JsonProperty("name") val name: String? = null,
    @JsonProperty("releaseTime") val releaseTime: String? = null,
)

data class DumpQuickSearchData(
    @JsonProperty("searchResults") val searchResults: ArrayList<DumpMedia>? = arrayListOf(),
)

data class SubtitlingList(
    @JsonProperty("languageAbbr") val languageAbbr: String? = null,
    @JsonProperty("language") val language: String? = null,
    @JsonProperty("subtitlingUrl") val subtitlingUrl: String? = null,
)

data class DefinitionList(
    @JsonProperty("code") val code: String? = null,
    @JsonProperty("description") val description: String? = null,
)

data class EpisodeVo(
    @JsonProperty("id") val id: Int? = null,
    @JsonProperty("seriesNo") val seriesNo: Int? = null,
    @JsonProperty("definitionList") val definitionList: ArrayList<DefinitionList>? = arrayListOf(),
    @JsonProperty("subtitlingList") val subtitlingList: ArrayList<SubtitlingList>? = arrayListOf(),
)

data class DumpMediaDetail(
    @JsonProperty("episodeVo") val episodeVo: ArrayList<EpisodeVo>? = arrayListOf(),
)

data class EMovieServer(
    @JsonProperty("value") val value: String? = null,
)

data class EMovieSources(
    @JsonProperty("file") val file: String? = null,
)

data class EMovieTraks(
    @JsonProperty("file") val file: String? = null,
    @JsonProperty("label") val label: String? = null,
)

data class ShowflixResultsMovies(
    @JsonProperty("movieName") val movieName: String? = null,
    @JsonProperty("streamwish") val streamwish: String? = null,
    @JsonProperty("filelions") val filelions: String? = null,
    @JsonProperty("streamruby") val streamruby: String? = null,
)

data class ShowflixResultsSeries(
    @JsonProperty("seriesName") val seriesName: String? = null,
    @JsonProperty("streamwish") val streamwish: HashMap<String, List<String>>? = hashMapOf(),
    @JsonProperty("filelions") val filelions: HashMap<String, List<String>>? = hashMapOf(),
    @JsonProperty("streamruby") val streamruby: HashMap<String, List<String>>? = hashMapOf(),
)

data class ShowflixSearchMovies(
    @JsonProperty("results") val resultsMovies: ArrayList<ShowflixResultsMovies>? = arrayListOf(),
)

data class ShowflixSearchSeries(
    @JsonProperty("results") val resultsSeries: ArrayList<ShowflixResultsSeries>? = arrayListOf(),
)

data class SFMoviesSeriess(
    @JsonProperty("title") var title: String? = null,
    @JsonProperty("svideos") var svideos: String? = null,
)

data class SFMoviesAttributes(
    @JsonProperty("title") var title: String? = null,
    @JsonProperty("video") var video: String? = null,
    @JsonProperty("releaseDate") var releaseDate: String? = null,
    @JsonProperty("seriess") var seriess: ArrayList<ArrayList<SFMoviesSeriess>>? = arrayListOf(),
    @JsonProperty("contentId") var contentId: String? = null,
)

data class SFMoviesData(
    @JsonProperty("id") var id: Int? = null,
    @JsonProperty("attributes") var attributes: SFMoviesAttributes? = SFMoviesAttributes()
)

data class SFMoviesSearch(
    @JsonProperty("data") var data: ArrayList<SFMoviesData>? = arrayListOf(),
)

data class RidoContentable(
    @JsonProperty("imdbId") var imdbId: String? = null,
    @JsonProperty("tmdbId") var tmdbId: Int? = null,
)

data class RidoItems(
    @JsonProperty("slug") var slug: String? = null,
    @JsonProperty("contentable") var contentable: RidoContentable? = null,
)

data class RidoData(
    @JsonProperty("url") var url: String? = null,
    @JsonProperty("items") var items: ArrayList<RidoItems>? = arrayListOf(),
)

data class RidoResponses(
    @JsonProperty("data") var data: ArrayList<RidoData>? = arrayListOf(),
)

data class RidoSearch(
    @JsonProperty("data") var data: RidoData? = null,
)

data class SmashySources(
    @JsonProperty("sourceUrls") var sourceUrls: ArrayList<String>? = arrayListOf(),
    @JsonProperty("subtitleUrls") var subtitleUrls: String? = null,
)

data class AoneroomResponse(
    @JsonProperty("data") val data: Data? = null,
) {
    data class Data(
        @JsonProperty("items") val items: ArrayList<Items>? = arrayListOf(),
        @JsonProperty("list") val list: ArrayList<List>? = arrayListOf(),
    ) {
        data class Items(
            @JsonProperty("subjectId") val subjectId: String? = null,
            @JsonProperty("title") val title: String? = null,
            @JsonProperty("releaseDate") val releaseDate: String? = null,
        )

        data class List(
            @JsonProperty("resourceLink") val resourceLink: String? = null,
            @JsonProperty("extCaptions") val extCaptions: ArrayList<ExtCaptions>? = arrayListOf(),
            @JsonProperty("se") val se: Int? = null,
            @JsonProperty("ep") val ep: Int? = null,
            @JsonProperty("resolution") val resolution: Int? = null,
        ) {
            data class ExtCaptions(
                @JsonProperty("lanName") val lanName: String? = null,
                @JsonProperty("url") val url: String? = null,
            )
        }
    }
}

//

data class Aoneroomep(
    val code: Long,
    val message: String,
    val data: AoneroomepData,
)

data class AoneroomepData(
    val streams: List<AoneroomepStream>,
    val title: String,
)

data class AoneroomepStream(
    val format: String,
    val id: String,
    val url: String,
    val resolutions: String,
    val size: String,
    val duration: Long,
    val codecName: String,
    val signCookie: String,
)


data class CinemaTvResponse(
    @JsonProperty("streams") val streams: HashMap<String, String>? = null,
    @JsonProperty("subtitles") val subtitles: ArrayList<Subtitles>? = arrayListOf(),
) {
    data class Subtitles(
        @JsonProperty("language") val language: String? = null,
        @JsonProperty("file") val file: Any? = null,
    )
}

data class NepuSearch(
    @JsonProperty("data") val data: ArrayList<Data>? = arrayListOf(),
) {
    data class Data(
        @JsonProperty("url") val url: String? = null,
        @JsonProperty("name") val name: String? = null,
        @JsonProperty("type") val type: String? = null,
    )
}

data class ConsumetSources(
    val sources: List<ConsumetSource>?,
    val subtitles: List<ConsumetSubtitle>?,
    val download: String?
)

data class ConsumetSource(
    val url: String,
    val isM3u8: Boolean
)

data class ConsumetSubtitle(
    val url: String,
    val lang: String
)

data class Vidbinge(
    val token: String,
)

data class Vidbingesources(
    val embedId: String,
    val url: String,
)

data class Vidbingeplaylist(
    val stream: List<Streamplaylist>,
)

//SharmaFlix

// Top-level list alias
typealias SharmaFlixRoot = List<SharmaFlixRoot2>

// Root2 data class
data class SharmaFlixRoot2(
    val id: String,
    @JsonProperty("TMDB_ID")
    val tmdbId: String,
    val name: String,
    val description: String,
    val genres: String,
    @JsonProperty("release_date")
    val releaseDate: String,
    val runtime: String,
    val poster: String,
    val banner: String,
    @JsonProperty("youtube_trailer")
    val youtubeTrailer: String,
    val downloadable: String,
    val type: String,
    val status: String,
    @JsonProperty("content_type")
    val contentType: String,
    @JsonProperty("custom_tag")
    val customTag: CustomTag
)

// CustomTag data class
data class CustomTag(
    val id: String,
    @JsonProperty("custom_tags_id")
    val customTagsId: String,
    @JsonProperty("content_id")
    val contentId: String,
    @JsonProperty("content_type")
    val contentType: String,
    @JsonProperty("custom_tags_name")
    val customTagsName: String,
    @JsonProperty("background_color")
    val backgroundColor: String,
    @JsonProperty("text_color")
    val textColor: String
)

typealias SharmaFlixLinks = List<SharmaFlixLink>

data class SharmaFlixLink(
    val id: String,
    val name: String,
    val size: String,
    val quality: String,
    @JsonProperty("link_order")
    val linkOrder: String,
    @JsonProperty("movie_id")
    val movieId: String,
    val url: String,
    val type: String,
    val status: String,
    @JsonProperty("skip_available")
    val skipAvailable: String,
    @JsonProperty("intro_start")
    val introStart: String?,
    @JsonProperty("intro_end")
    val introEnd: String?,
    @JsonProperty("end_credits_marker")
    val endCreditsMarker: String,
    @JsonProperty("link_type")
    val linkType: String,
    @JsonProperty("drm_uuid")
    val drmUuid: String?,
    @JsonProperty("drm_license_uri")
    val drmLicenseUri: String?
)


data class Streamplaylist(
    val id: String,
    val type: String,
    val playlist: String,
    val flags: List<String>,
    val captions: List<Captionplaylist>,
)

data class Captionplaylist(
    val id: String,
    val url: String,
    val type: String,
    val hasCorsRestrictions: Boolean,
    val language: String,
)

//

data class Embedsu(
    val title: String,
    val server: String,
    val ref: String,
    val xid: String,
    val uwuId: String,
    val episodeId: String,
    val hash: String,
    val poster: String,
)

data class EmbedsuItem(val name: String, val hash: String)


data class Embedsuhref(
    val source: String,
    val subtitles: List<EmbedsuhrefSubtitle>,
    val skips: List<Any?>,
    val format: String,
)

data class EmbedsuhrefSubtitle(
    val label: String,
    val file: String,
)




data class SubtitlesAPI(
    val subtitles: List<Subtitle>,
    val cacheMaxAge: Long,
)

data class Subtitle(
    val id: String,
    val url: String,
    @JsonProperty("SubEncoding")
    val subEncoding: String,
    val lang: String,
    val m: String,
    val g: String,
)

// Theyallsayflix

data class Theyallsayflix(
    val error: Boolean,
    val title: String,
    @JsonProperty("poster_url")
    val posterUrl: String,
    @JsonProperty("backdrop_url")
    val backdropUrl: String,
    @JsonProperty("release_date")
    val releaseDate: String,
    val rating: Double,
    val desc: String,
    @JsonProperty("original_lang")
    val originalLang: String,
    val minutes: Long,
    val status: String,
    @JsonProperty("status_desc")
    val statusDesc: String,
    val streams: List<TheyallsayflixStream>,
    val subtitles: List<TheyallsayflixSubtitle>,
)

data class TheyallsayflixStream(
    val quality: Long,
    @JsonProperty("file_size")
    val fileSize: Long,
    @JsonProperty("file_name")
    val fileName: String,
    @JsonProperty("play_url")
    val playUrl: String,
)

data class TheyallsayflixSubtitle(
    @JsonProperty("lang_code")
    val langCode: String,
    @JsonProperty("lang_name")
    val langName: String,
    @JsonProperty("sub_name")
    val subName: String,
    @JsonProperty("is_hearing_impaired")
    val isHearingImpaired: Boolean,
    @JsonProperty("download_link")
    val downloadLink: String,
)

