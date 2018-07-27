package ru.popov.bodya.howmoney.presentation.ui.account.fragments

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import ru.popov.bodya.core.mvp.BaseCoreFragment
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.domain.account.models.Currency
import ru.popov.bodya.howmoney.presentation.mvp.account.AccountPresenter
import ru.popov.bodya.howmoney.presentation.mvp.account.AccountView
import java.util.*
import javax.inject.Inject


/**
 *  @author popovbodya
 */
class AccountFragment : BaseCoreFragment(), AccountView {

    @Inject
    @InjectPresenter
    lateinit var accountPresenter: AccountPresenter

    private lateinit var amountRUBTextView: TextView
    private lateinit var amountUSDTextView: TextView

    @ProvidePresenter
    fun provideAccountPresenter(): AccountPresenter = accountPresenter

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
            R.id.settings -> {
                accountPresenter.onSettingsMenuItemClick()
                true
            }
            R.id.about -> {
                accountPresenter.onAboutMenuItemClick()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showRUBAmount(amount: Double) {
        amountRUBTextView.text = String.format(Locale.ENGLISH, getString(R.string.account_amount), amount, Currency.RUB.stringValue)
    }

    override fun showUSDAmount(amount: Double) {
        amountUSDTextView.text = String.format(Locale.ENGLISH, getString(R.string.account_amount), amount, Currency.USD.stringValue)
    }

    private fun initViews(parentView: View) {
        amountRUBTextView = parentView.findViewById(R.id.amount_rub_text_view)
        amountUSDTextView = parentView.findViewById(R.id.amount_usd_text_view)
        initToolbar(parentView)
    }

    private fun initToolbar(parentView: View) {
        val toolbar = parentView.findViewById<Toolbar>(R.id.toolbar)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
    }

}