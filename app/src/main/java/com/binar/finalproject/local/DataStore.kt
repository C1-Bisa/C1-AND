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
        val EMAIL = stringPreferencesKey("email_user")
        val TOKEN = stringPreferencesKey("token_user")

    }

    suspend fun saveUser(email : String, token : String){
        context.counterDataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = true
            preferences[EMAIL] = email
            preferences[TOKEN] = token

        }
    }

    fun isLoggin() : Flow<Boolean> {
        return context.counterDataStore.data.map { preferences ->
            preferences[IS_LOGGED_IN] ?: false
        }
    }

    val getEmailUser : Flow<String> = context.counterDataStore.data.map {
        it[EMAIL] ?: ""
    }

    val getToken : Flow<String> = context.counterDataStore.data.map {
        it[TOKEN] ?: ""

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