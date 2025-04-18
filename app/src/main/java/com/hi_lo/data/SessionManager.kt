import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(SESSION_PREFS, Context.MODE_PRIVATE)

    fun saveSessionToken(token: String) {
        prefs.edit().putString(KEY_SESSION_TOKEN, token).apply()
    }

    fun getSessionToken(): String? {
        return prefs.getString(KEY_SESSION_TOKEN, null)
    }

    fun clearSession() {
        prefs.edit().remove(KEY_SESSION_TOKEN).apply()
    }

    companion object {
        private const val SESSION_PREFS = "session_prefs"
        private const val KEY_SESSION_TOKEN = "session_token"
    }
}
