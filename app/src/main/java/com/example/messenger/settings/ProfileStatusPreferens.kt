package com.example.messenger.settings

import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import androidx.preference.EditTextPreference
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.example.messenger.R
import com.example.messenger.data.local.AppPreferences
import com.example.messenger.data.remote.StatusUpdateRequestObject
import com.example.messenger.service.MessageApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProfileStatusPreferens(context: Context, atrtbuteSet: AttributeSet) :
    EditTextPreference(context, atrtbuteSet), DialogInterface.OnClickListener {
    private val service: MessageApiService = MessageApiService.getInstance()
    private val preferences: AppPreferences = AppPreferences.create(context)

    fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            val etStatus: EditText? = null
            if (TextUtils.isEmpty(etStatus?.text)) {
                Toast.makeText(
                    context, R.string.status_empty,
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val reqvestObject = StatusUpdateRequestObject(etStatus?.text.toString())
                service.updateUserStatus(
                    reqvestObject as Long,
                    preferences.accessToken as String
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ res ->
                        preferences.storeUserDetails(res)
                    }, { error ->
                        Toast.makeText(
                            context, R.string.Unable_load_,
                            Toast.LENGTH_LONG
                        ).show()
                    })
            }
        }

    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        TODO("Not yet implemented")
    }

}
