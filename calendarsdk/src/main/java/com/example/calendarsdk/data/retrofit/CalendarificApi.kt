package com.example.calendarsdk.data.retrofit

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface CalendarificApi {
    @GET("api/v2/holidays")
    suspend fun checkHoliday(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int,
        @Query("api_key") apiKey: String,
        @Query("country") country: String
    ): CalendarificResponse
}

data class CalendarificResponse (
    @SerializedName("meta"     ) var meta     : Meta?     = Meta(),
    @SerializedName("response" ) var response : Response? = Response()
)

data class Meta (

    @SerializedName("code" ) var code : Int? = null

)

data class Response (

    @SerializedName("holidays" ) var holidays : ArrayList<Holidays> = arrayListOf()

)



data class Holidays (

    @SerializedName("name"          ) var name         : String?           = null,
    @SerializedName("description"   ) var description  : String?           = null,
    @SerializedName("country"       ) var country      : Country?          = Country(),
    @SerializedName("date"          ) var date         : Date?             = Date(),
    @SerializedName("type"          ) var type         : ArrayList<String> = arrayListOf(),
    @SerializedName("primary_type"  ) var primaryType  : String?           = null,
    @SerializedName("canonical_url" ) var canonicalUrl : String?           = null,
    @SerializedName("urlid"         ) var urlid        : String?           = null,
    @SerializedName("locations"     ) var locations    : String?           = null,
//    @SerializedName("states"        ) var states       : String?           = null

)



data class Country (

    @SerializedName("id"   ) var id   : String? = null,
    @SerializedName("name" ) var name : String? = null

)

data class Date (

    @SerializedName("iso"      ) var iso      : String?   = null,
    @SerializedName("datetime" ) var datetime : Datetime? = Datetime()

)



data class Datetime (

    @SerializedName("year"  ) var year  : Int? = null,
    @SerializedName("month" ) var month : Int? = null,
    @SerializedName("day"   ) var day   : Int? = null

)