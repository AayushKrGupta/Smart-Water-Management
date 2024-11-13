package aayush.kumar.watermanagement

import aayush.kumar.smartwatermanagement.MyData
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {

    @GET("channels/2716484/feeds.json")
    fun getFeedData() : Call<MyData>
}