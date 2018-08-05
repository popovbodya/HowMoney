package ru.popov.bodya.howmoney.presentation.ui.settings.fragments

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.presentation.mvp.settings.SettingsPresenter
import ru.popov.bodya.howmoney.presentation.mvp.settings.SettingsView
import ru.popov.bodya.howmoney.presentation.ui.account.activities.AccountActivity
import ru.popov.bodya.howmoney.presentation.ui.common.BaseFragment
import ru.popov.bodya.howmoney.presentation.ui.common.Screens
import javax.inject.Inject

/**
 *  @author popovbodya
 */
class SettingsFragment : BaseFragment(), SettingsView {

    override val toolbarTitleId: Int
        get() = R.string.settings
    override val TAG: String
        get() = Screens.SETTINGS_SCREEN
    override val layoutRes: Int
        get() = R.layout.fragment_settings

    @Inject
    @InjectPresenter
    lateinit var settingsPresenter: SettingsPresenter

    @ProvidePresenter
    fun provideSettingsPresenter(): SettingsPresenter = settingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        initUI()
        setHasOptionsMenu(true)
    }

    override fun setUpToolbarTitle(resId: Int) {
        (activity as AccountActivity).updateToolBar(resId)
    }

    private fun initUI() {
    }
}