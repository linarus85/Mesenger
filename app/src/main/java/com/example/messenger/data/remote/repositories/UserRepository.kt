package com.example.messenger.data.remote.repositories

import com.example.messenger.data.value_object.UserListVO
import com.example.messenger.data.value_object.UserVO
import io.reactivex.Observable


interface UserRepository {
    fun findById(id: Long): Observable<UserVO>
    fun all(): Observable<UserListVO>
    fun echoDetails(): Observable<UserVO>
}