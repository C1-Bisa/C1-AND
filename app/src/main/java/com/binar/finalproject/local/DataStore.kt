package com.binar.finalproject.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class DataStore(private val context: Context) {
    companion object {
        private val Context.counterDataStore by preferencesDataStore(
            name = "user_prefs"
        )
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val TOKEN = stringPreferencesKey("token_user")
        val EMAIL = stringPreferencesKey("email_user")

    }

    suspend fun saveUser(email : String, token : String){
        context.counterDataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[TOKEN] = token
            preferences[EMAIL] = email
        }
    }

    fun isLoggin() : Flow<Boolean> {
        return context.counterDataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }
    }

    val getToken : Flow<String> = context.counterDataStore.data.map {
        it[TOKEN] ?: ""

    }

    val getEmailUser : Flow<String> = context.counterDataStore.data.map {
        it[EMAIL] ?: ""
    }


    suspend fun clear() {
        context.counterDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun isAlreadyLogin() : Boolean {
        return runBlocking {
            isLoggin().first()
        }
    }

}