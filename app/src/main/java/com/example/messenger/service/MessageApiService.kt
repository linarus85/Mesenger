package com.example.messenger.service

import com.example.messenger.data.remote.LoginRequestObject
import com.example.messenger.data.remote.MessageRequestObject
import com.example.messenger.data.remote.UserRequestObject
import com.example.messenger.data.value_object.*
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MessageApiService {
    @POST("login")
    @Headers("Content-Type: application/json")
    fun login(@Body user: LoginRequestObject):
            Observable<retrofit2.Response<ResponseBody>>

    @POST("users/register")
    fun createUser(@Body user: UserRequestObject):
            Observable<UserVO>

    @GET("users")
    fun listUsers(@Header("Authorization") authorization: String):
            Observable<UserListVO>

    @PUT("users")
    fun updateUserStatus(
        @Path("userId") userId: Long,
        @Header("Authorization") authorization: String
    ): Observable<UserVO>

    @GET("users/{userId}")
    fun showUser(
        @Path("userId") userId: Long,
        @Header("Authorization") authorization: String
    ): Observable<UserVO>

    @GET("users/details")
    fun echoDetails(@Header("Authorization") authorization: String):
            Observable<UserVO>

    @POST("messages")
    fun createMessage(
        @Body messageRequestObject: MessageRequestObject,
        @Header("Authorization") authorization: String
    ): Observable<MessageVO>

    @GET("conversations")
    fun listConversations(@Header("Authorization") authorization: String):
            Observable<ConversationListVO>

    @GET("conversations/{conversationsId}")
    fun showConversation(
        @Path("conversationsId") conversationsId: Long,
        @Header("Authorization") authorization: String
    ): Observable<ConversationVO>

    companion object Factory {
        private var service: MessageApiService? = null
        fun getInstance(): MessageApiService {
            if (service == null) {
                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl( "https://mesenger-api.herokuapp.com/" )
                    .build()
                service = retrofit.create(MessageApiService::class.java)
            }
            return service as MessageApiService
        }
    }

}