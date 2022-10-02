package com.example.messenger.base

import android.content.Context

interface BaseView {
    fun bindView()
    fun getContext():Context
}