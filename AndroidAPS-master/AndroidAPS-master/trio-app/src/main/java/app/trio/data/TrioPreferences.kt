package app.trio.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "trio_settings")

class TrioPreferences(private val context: Context) {

    companion object {
        val NIGHTSCOUT_URL = stringPreferencesKey("nightscout_url")
        val NIGHTSCOUT_API_SECRET = stringPreferencesKey("nightscout_api_secret")
        val IS_SMB_ENABLED = booleanPreferencesKey("is_smb_enabled")
        val IS_AUTOSENS_ENABLED = booleanPreferencesKey("is_autosens_enabled")
        val LOOP_MODE = stringPreferencesKey("loop_mode")
    }

    // Nightscout
    val nightscoutUrl: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[NIGHTSCOUT_URL] ?: ""
    }

    val nightscoutSecret: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[NIGHTSCOUT_API_SECRET] ?: ""
    }

    suspend fun saveNightscoutConfig(url: String, secret: String) {
        context.dataStore.edit { settings ->
            settings[NIGHTSCOUT_URL] = url
            settings[NIGHTSCOUT_API_SECRET] = secret
        }
    }

    // SMB
    val isSmbEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_SMB_ENABLED] ?: false
    }

    suspend fun setSmbEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_SMB_ENABLED] = enabled
        }
    }

    // Autosens
    val isAutosensEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_AUTOSENS_ENABLED] ?: false
    }

    suspend fun setAutosensEnabled(enabled: Boolean) {
        context.dataStore.edit { settings ->
            settings[IS_AUTOSENS_ENABLED] = enabled
        }
    }
    
    // Loop Mode
    val loopMode: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LOOP_MODE] ?: "Closed Loop"
    }

    suspend fun setLoopMode(mode: String) {
        context.dataStore.edit { settings ->
            settings[LOOP_MODE] = mode
        }
    }
}
