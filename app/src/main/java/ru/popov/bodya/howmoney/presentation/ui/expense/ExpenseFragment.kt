package ru.popov.bodya.howmoney.presentation.ui.expense

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import ru.popov.bodya.core.mvp.AppFragment
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.presentation.mvp.expense.ExpensePresenter
import ru.popov.bodya.howmoney.presentation.mvp.expense.ExpenseView
import javax.inject.Inject

/**
 *  @author popovbodya
 */
class ExpenseFragment : AppFragment(), ExpenseView {

    @Inject
    @InjectPresenter
    lateinit var expensePresenter: ExpensePresenter

    @ProvidePresenter
    fun provideExpensePresenter(): ExpensePresenter = expensePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.expense_fragment_layout, container, false)
        setHasOptionsMenu(true)
        initViews(parentView)
        return parentView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                expensePresenter.onUpButtonPressed()
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
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}