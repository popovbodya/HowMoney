package ru.popov.bodya.howmoney.presentation.ui.settings.fragments

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import dagger.android.support.AndroidSupportInjection
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.data.database.preferences.SharedPreferencesWrapper
import javax.inject.Inject

/**
 *  @author popovbodya
 */
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var sharedPreferencesWrapper: SharedPreferencesWrapper

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val preference = findPreference(getString(R.string.settings_currency_key))
        preference.setOnPreferenceChangeListener { pref, newValue -> saveNewValue(newValue) }
    }

    private fun saveNewValue(newValue: Any): Boolean {
        when (newValue) {
            is String -> sharedPreferencesWrapper.saveDefaultCurrency(newValue)
        }
        return true
    }
}