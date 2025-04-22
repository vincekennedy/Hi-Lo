package com.hi_lo.data

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object MatchPreferencesKeys {
    val MATCH_DATA = stringPreferencesKey("match_data")
}

// Extension property for DataStore
val Context.matchDataStore by preferencesDataStore(name = "match_data")
