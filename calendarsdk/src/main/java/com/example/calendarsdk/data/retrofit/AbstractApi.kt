package com.example.calendarsdk.data.retrofit

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query


interface AbstractApi {
    @GET("/v1")
    suspend fun checkHoliday(
        @Query("year") year: Int,
        @Query("month") month: Int,
        @Query("day") day: Int,
        @Query("api_key") apiKey: String,
        @Query("country") country: String
    ): List<AbstractResponse>
}

class AbstractResponse (
    @SerializedName("name"        ) var name        : String? = null,
    @SerializedName("name_local"  ) var nameLocal   : String? = null,
    @SerializedName("language"    ) var language    : String? = null,
    @SerializedName("description" ) var description : String? = null,
    @SerializedName("country"     ) var country     : String? = null,
    @SerializedName("location"    ) var location    : String? = null,
    @SerializedName("type"        ) var type        : String? = null,
    @SerializedName("date"        ) var date        : String? = null,
    @SerializedName("date_year"   ) var dateYear    : String? = null,
    @SerializedName("date_month"  ) var dateMonth   : String? = null,
    @SerializedName("date_day"    ) var dateDay     : String? = null,
    @SerializedName("week_day"    ) var weekDay     : String? = null
)