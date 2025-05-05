package com.komekci.marketplace.features.auth.data.local

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.komekci.marketplace.core.locale.Locales
import com.komekci.marketplace.features.auth.data.entity.LocalUserEntity
import kotlinx.coroutines.flow.first


enum class AppLanguage(key: String) {
    turkmen(Locales.TK),
    english(Locales.EN),
    russian(Locales.RU),
    uzbek(Locales.UZ)
}

private val Context.dataStore by preferencesDataStore(
    name = "PreferenceDataStore"
)
class UserDataStore(
    private val context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        val USER_ID = stringPreferencesKey("user_id")
        val USERNAME = stringPreferencesKey("username")
        val PHONE = stringPreferencesKey("phone")
        val EMAIL = stringPreferencesKey("email")
        val TOKEN = stringPreferencesKey("token")
        val IMAGE = stringPreferencesKey("image")
        val STORE_TOKEN = stringPreferencesKey("store_token")
        val SKIP_AUTH = stringPreferencesKey("skip_auth")
        val IS_FIRST_LAUNCH = stringPreferencesKey("is_first_launch")
        val LANGUAGE_KEY = stringPreferencesKey("language_key")
        val GUEST_ID = stringPreferencesKey("guest_id_key")
        val COUNTRY_ID = stringPreferencesKey("country_id_key")
        val REGION_ID = stringPreferencesKey("region_id_key")
    }

    suspend fun saveCountryId(id: String) {
        dataStore.edit {
            it[COUNTRY_ID] = id
        }
    }

    suspend fun getCountryId(): String? {
        val preferences = dataStore.data.first()
        return preferences[COUNTRY_ID]
    }

    suspend fun saveRegionId(id: String) {
        dataStore.edit {
            it[REGION_ID] = id
        }
    }


    suspend fun getRegionId(): String? {
        val preferences = dataStore.data.first()
        return preferences[REGION_ID]
    }


    suspend fun saveGuestId(id: String) {
        Log.e("GUEST ID", "SAVING ${id}")
        dataStore.edit {
            it[GUEST_ID] = id
        }
    }

    suspend fun getGuestId(): String {
        val preferences = dataStore.data.first()
        Log.e("GUEST ID", "READING ${preferences[GUEST_ID]}")
        return preferences[GUEST_ID]?:""
    }

    suspend fun saveLanguage(language: String) {
        dataStore.edit {
            it[LANGUAGE_KEY] = language
        }
    }

    suspend fun getLanguage(): String {
        val preferences = dataStore.data.first()
        val lang = preferences[LANGUAGE_KEY]
        return lang?:Locales.TK
    }

    suspend fun saveUserData(data: LocalUserEntity) {
        dataStore.edit {
            it[USER_ID] = data.id?:""
            it[USERNAME] = data.username?:""
            it[PHONE] = data.phone?:""
            it[EMAIL] = data.email?:""
            it[TOKEN] = data.token?:""
            it[STORE_TOKEN] = data.store_token?:""
            it[IMAGE] = data.image?:""
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[TOKEN] = token
        }
    }

    suspend fun clearUserData() {
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun saveSkipAuth(skipAuth: String) {
        dataStore.edit {
            it[SKIP_AUTH] = skipAuth
        }
    }

    suspend fun saveIsFirstLaunch(isFirstLaunch: String) {
        dataStore.edit {
            it[IS_FIRST_LAUNCH] = isFirstLaunch
        }
    }

    suspend fun saveImage(image: String) {
        dataStore.edit {
            it[IMAGE] = image
        }
    }

    suspend fun savePhone(phone: String) {
        dataStore.edit {
            it[PHONE] = phone
        }
    }

    suspend fun saveUsername(username: String) {
        dataStore.edit {
            it[USERNAME] = username
        }
    }

    suspend fun saveEmail(email: String) {
        dataStore.edit {
            it[EMAIL] = email
        }
    }

    suspend fun saveId(id: String) {
        dataStore.edit {
            it[USER_ID] = id
        }
    }

    suspend fun getUserData(onResult: (LocalUserEntity) -> Unit) {
        Log.e("TAG","Four")
        val preferences = dataStore.data.first()
        Log.e("TAG","Five:"+preferences)
        onResult(LocalUserEntity(
            preferences[USER_ID],
            preferences[USERNAME],
            preferences[PHONE],
            preferences[EMAIL],
            preferences[TOKEN],
            store_token = preferences[STORE_TOKEN]?:"hello",
            preferences[IMAGE],
            preferences[SKIP_AUTH] ?: "0",
            preferences[IS_FIRST_LAUNCH] ?: "1"
        ))
    }

    suspend fun clearToken() {
        dataStore.edit {
            it[TOKEN] = ""
            it[STORE_TOKEN] = ""
        }
    }

    suspend fun logout() {
        dataStore.edit {
            it[USER_ID] = ""
            it[USERNAME] = ""
            it[PHONE] = ""
            it[EMAIL] = ""
            it[TOKEN] = ""
            it[STORE_TOKEN] = ""
            it[IMAGE] = ""
        }
    }
}
