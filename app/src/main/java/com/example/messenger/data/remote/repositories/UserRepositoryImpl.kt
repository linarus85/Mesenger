package com.example.messenger.data.remote.repositories

import android.content.Context
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.data.value_object.UserListVO
import com.example.messenger.data.value_object.UserVO
import com.example.messenger.service.MessageApiService
import io.reactivex.Observable

class UserRepositoryImpl(context: Context):UserRepository {
    private val preferences:AppPreferences = AppPreferences.create(context)
    private val service:MessageApiService = MessageApiService.getInstance()

    override fun findById(id: Long): Observable<UserVO> {
       return service.showUser(id,preferences.accessToken as String)
    }

    override fun all(): Observable<UserListVO> {
        return service.listUsers(preferences.accessToken as String)
    }

    override fun echoDetails(): Observable<UserVO> {
        return service.echoDetails(preferences.accessToken as String)
    }
}