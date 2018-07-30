package ru.popov.bodya.howmoney.presentation.ui.account.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ru.popov.bodya.core.mvp.AppActivity
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import ru.popov.bodya.howmoney.presentation.mvp.account.AccountPresenter
import ru.popov.bodya.howmoney.presentation.mvp.account.AccountView
import ru.popov.bodya.howmoney.presentation.ui.about.fragments.AboutFragment
import ru.popov.bodya.howmoney.presentation.ui.budget.fragments.BudgetFragment
import ru.popov.bodya.howmoney.presentation.ui.common.Screens.ABOUT_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.common.Screens.BUDGET_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.common.Screens.ENROLLMENT_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.common.Screens.EXPENSE_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.common.Screens.REPLENISHMENT_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.common.Screens.SETTINGS_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.common.Screens.WRITE_EMAIL_SCREEN
import ru.popov.bodya.howmoney.presentation.ui.enrollment.EnrollmentFragment
import ru.popov.bodya.howmoney.presentation.ui.expense.ExpenseFragment
import ru.popov.bodya.howmoney.presentation.ui.replenishment.fragments.ReplenishmentFragment
import ru.popov.bodya.howmoney.presentation.ui.settings.fragments.SettingsFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject


/**
 *  @author popovbodya
 */
class AccountActivity : AppActivity(), AccountView, HasSupportFragmentInjector {

    @Inject
    @InjectPresenter
    lateinit var accountPresenter: AccountPresenter
    @Inject
    lateinit var navigationHolder: NavigatorHolder
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Fragment>

    @ProvidePresenter
    fun provideOverviewPresenter(): AccountPresenter = accountPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.launch_activity_layout)
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
        accountPresenter.onBackPressed()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = injector

    private val navigator = object : SupportAppNavigator(this, supportFragmentManager, R.id.main_container) {
        override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? {
            return when (screenKey) {
                WRITE_EMAIL_SCREEN -> composeEmail(data as? String
                        ?: getString(R.string.email), getString(R.string.about_email_subject))
                else -> null
            }
        }

        override fun createFragment(screenKey: String, data: Any?): Fragment? {
            return when (screenKey) {
                BUDGET_SCREEN -> BudgetFragment()
                ENROLLMENT_SCREEN -> EnrollmentFragment.newInstance(data as Wallet)
                EXPENSE_SCREEN -> ExpenseFragment.newInstance(data as Wallet)
                REPLENISHMENT_SCREEN -> ReplenishmentFragment.newInstance(data as Wallet)
                SETTINGS_SCREEN -> SettingsFragment()
                ABOUT_SCREEN -> AboutFragment()
                else -> null
            }
        }

        override fun showSystemMessage(message: String) {
            Toast.makeText(this@AccountActivity, message, Toast.LENGTH_SHORT).show()
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