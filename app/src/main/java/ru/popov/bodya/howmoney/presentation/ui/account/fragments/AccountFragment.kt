package ru.popov.bodya.howmoney.presentation.ui.account.fragments

import android.os.Bundle
import android.view.*
import ru.popov.bodya.core.mvp.BaseCoreFragment
import ru.popov.bodya.howmoney.R
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.popov.bodya.howmoney.presentation.mvp.account.AccountPresenter
import javax.inject.Inject
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import dagger.android.support.AndroidSupportInjection
import ru.popov.bodya.howmoney.presentation.mvp.account.AccountView
import android.view.MenuInflater




/**
 *  @author popovbodya
 */
class AccountFragment : BaseCoreFragment(), AccountView {

    @Inject
    @InjectPresenter
    lateinit var accountPresenter: AccountPresenter

    @ProvidePresenter
    fun provideAccountPresenter(): AccountPresenter = accountPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.account_fragment_layout, container, false)
        setHasOptionsMenu(true);
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

    private fun initViews(parentView: View) {
        initToolbar(parentView)
    }

    private fun initToolbar(parentView: View) {
        val toolbar = parentView.findViewById<Toolbar>(R.id.toolbar)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
    }

}