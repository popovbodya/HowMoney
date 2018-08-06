package ru.popov.bodya.howmoney.data.repositories.prefs

import android.content.SharedPreferences
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.*
import ru.popov.bodya.howmoney.data.database.preferences.SharedPreferencesWrapper

@RunWith(JUnit4::class)
class SharedPrefsWrapperTest {

    @Test
    fun shouldSaveCurrencyInPrefs() {
        val editor = mock(SharedPreferences.Editor::class.java)
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)

        val prefs = initMockPrefs(editor)
        val prefsWrapper = SharedPreferencesWrapper(prefs)

        prefsWrapper.setNewFavCurrency("RUB")

        verify(prefs).edit()
        verify(editor).putString(eq("FAV_CURRENCY_KEY"), eq("RUB"))
        verify(editor).apply()
    }


    private fun initMockPrefs(editor: SharedPreferences.Editor): SharedPreferences {
        val sharedPreferences = mock(SharedPreferences::class.java)
        `when`(sharedPreferences.edit()).thenReturn(editor)
        return sharedPreferences
    }
}