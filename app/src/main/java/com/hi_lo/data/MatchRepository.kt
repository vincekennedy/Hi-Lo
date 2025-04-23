package com.hi_lo.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.hi_lo.viewmodel.MatchData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MatchRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object MatchPreferencesKeys {
        val MATCH_DATA = stringPreferencesKey("match_data")
    }

    val matchDataFlow: Flow<MatchData?> = context.matchDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences()) // Handle any exceptions
            } else {
                throw exception
            }
        }
        .map { prefs ->
            prefs[MatchPreferencesKeys.MATCH_DATA]?.let {
                Json.decodeFromString<MatchData>(it)
            }
        }

    suspend fun saveMatchData(matchData: MatchData) {
        val json = Json.encodeToString(matchData)
        context.matchDataStore.edit { prefs ->
            prefs[MatchPreferencesKeys.MATCH_DATA] = json
        }
    }

    suspend fun loadMatchData(): MatchData? {
        return matchDataFlow.firstOrNull()
    }
}
