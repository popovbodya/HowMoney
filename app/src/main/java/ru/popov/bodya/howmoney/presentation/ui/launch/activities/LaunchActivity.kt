package ru.popov.bodya.howmoney.presentation.ui.launch.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ru.popov.bodya.core.dagger.Injectable
import ru.popov.bodya.core.mvp.BaseCoreActivity
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.presentation.mvp.launch.LaunchPresenter
import ru.popov.bodya.howmoney.presentation.mvp.launch.LaunchView
import ru.popov.bodya.howmoney.presentation.ui.about.fragments.AboutFragment
import ru.popov.bodya.howmoney.presentation.ui.account.fragments.AccountFragment
import ru.popov.bodya.howmoney.presentation.ui.global.Screens.ABOUT_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.global.Screens.ACCOUNT_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.global.Screens.SETTINGS_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.global.Screens.WRITE_EMAIL_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.settings.fragments.SettingsFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject


/**
 *  @author popovbodya
 */
class LaunchActivity : BaseCoreActivity(), LaunchView, Injectable, HasSupportFragmentInjector {

    @Inject
    @InjectPresenter
    lateinit var launchPresenter: LaunchPresenter
    @Inject
    lateinit var navigationHolder: NavigatorHolder
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>

    @ProvidePresenter
    fun provideOverviewPresenter(): LaunchPresenter = launchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launch_activity_layout)
        Log.e("bodya", launchPresenter.toString())
    }

    override fun onResume() {
        super.onResume()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        launchPresenter.onBackPressed()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = injector

    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.main_container) {
        override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? {
            return when (screenKey) {
                WRITE_EMAIL_SCREEN -> composeEmail(data as? String ?: getString(R.string.email), getString(R.string.about_email_subject))
                else -> null
            }
        }

        override fun createFragment(screenKey: String, data: Any?): Fragment? {
            return when (screenKey) {
                ACCOUNT_SCREEN -> AccountFragment()
                SETTINGS_SCREEN -> SettingsFragment()
                ABOUT_SCREEN -> AboutFragment()
                else -> null
            }
        }

        override fun showSystemMessage(message: String) {
            Toast.makeText(this@LaunchActivity, message, Toast.LENGTH_SHORT).show()
        }

        override fun exit() {
            finish()
        }
    }

    private fun composeEmail(address: String, subject: String): Intent {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(address))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        return intent
    }
}