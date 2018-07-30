package ru.popov.bodya.howmoney.presentation.ui.budget.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import ru.popov.bodya.core.mvp.AppFragment
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.domain.wallet.models.Currency
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import ru.popov.bodya.howmoney.presentation.mvp.budget.BudgetPresenter
import ru.popov.bodya.howmoney.presentation.mvp.budget.BudgetView
import java.util.*
import javax.inject.Inject


/**
 *  @author popovbodya
 */
class BudgetFragment : AppFragment(), BudgetView {

    @Inject
    @InjectPresenter
    lateinit var budgetPresenter: BudgetPresenter

    private lateinit var enrollmentTextView: TextView
    private lateinit var expenseTextView: TextView
    private lateinit var replenishmentFab: FloatingActionButton

    @ProvidePresenter
    fun provideAccountPresenter(): BudgetPresenter = budgetPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.account_fragment_layout, container, false)
        setHasOptionsMenu(true)
        initViews(parentView)
        return parentView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.launch_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.debit -> {
                budgetPresenter.onWalletChanged(Wallet.DebitWallet)
                return true
            }
            R.id.credit -> {
                budgetPresenter.onWalletChanged(Wallet.CreditWallet)
                return true
            }
            R.id.cache -> {
                budgetPresenter.onWalletChanged(Wallet.CacheWallet)
                return true
            }
            R.id.settings -> {
                budgetPresenter.onSettingsMenuItemClick()
                true
            }
            R.id.about -> {
                budgetPresenter.onAboutMenuItemClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showEnrollmentBalance(amount: Double) {
        enrollmentTextView.text = String.format(Locale.ENGLISH, getString(R.string.account_amount), amount, Currency.RUB.stringValue)
    }

    override fun showExpenseBalance(amount: Double) {
        expenseTextView.text = String.format(Locale.ENGLISH, getString(R.string.account_amount), amount, Currency.RUB.stringValue)
    }

    private fun initViews(parentView: View) {
        enrollmentTextView = parentView.findViewById(R.id.enrollment_text_view)
        enrollmentTextView.setOnClickListener { budgetPresenter.onEnrollmentBlockClick() }
        expenseTextView = parentView.findViewById(R.id.expense_text_view)
        expenseTextView.setOnClickListener { budgetPresenter.onExpenseBlockClick() }
        replenishmentFab = parentView.findViewById(R.id.add_replenishment_fab)
        replenishmentFab.setOnClickListener { budgetPresenter.onAddReplenishmentFabClick() }
        initToolbar(parentView)
    }

    private fun initToolbar(parentView: View) {
        val toolbar = parentView.findViewById<Toolbar>(R.id.toolbar)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
    }

}