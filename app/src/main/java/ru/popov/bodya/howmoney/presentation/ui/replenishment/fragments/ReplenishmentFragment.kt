package ru.popov.bodya.howmoney.presentation.ui.replenishment.fragments

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import ru.popov.bodya.core.mvp.AppFragment
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.domain.operation.models.OperationType
import ru.popov.bodya.howmoney.domain.wallet.models.Wallet
import ru.popov.bodya.howmoney.presentation.mvp.replenishment.ReplenishmentPresenter
import ru.popov.bodya.howmoney.presentation.mvp.replenishment.ReplenishmentView
import javax.inject.Inject
import android.widget.AdapterView
import ru.popov.bodya.howmoney.domain.enrollment.models.EnrollmentCategory
import ru.popov.bodya.howmoney.domain.enrollment.models.EnrollmentCategory.*
import ru.popov.bodya.howmoney.domain.expense.models.ExpenseCategory
import ru.popov.bodya.howmoney.domain.expense.models.ExpenseCategory.*
import ru.popov.bodya.howmoney.domain.wallet.models.Currency


/**
 *  @author popovbodya
 */
class ReplenishmentFragment : AppFragment(), ReplenishmentView {

    companion object {
        private const val WALLET_KEY = "walletKey"

        fun newInstance(wallet: Wallet): ReplenishmentFragment {
            val bundle = Bundle()
            bundle.putSerializable(WALLET_KEY, wallet)
            val enrollmentFragment = ReplenishmentFragment()
            enrollmentFragment.arguments = bundle
            return enrollmentFragment
        }
    }

    @Inject
    @InjectPresenter
    lateinit var replenishmentPresenter: ReplenishmentPresenter

    private lateinit var wallet: Wallet
    private lateinit var walletNameTitleTextView: TextView
    private lateinit var operationTypeSpinner: AppCompatSpinner
    private lateinit var replenishmentCategorySpinner: AppCompatSpinner
    private lateinit var currencySpinner: AppCompatSpinner
    private lateinit var amountSpinner: AppCompatSpinner

    @ProvidePresenter
    fun provideReplenishmentPresenter(): ReplenishmentPresenter {
        replenishmentPresenter.initPresenterParams(wallet)
        return replenishmentPresenter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        wallet = arguments?.getSerializable(WALLET_KEY) as Wallet
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val parentView = inflater.inflate(R.layout.replenishment_fragment_layout, container, false)
        setHasOptionsMenu(true)
        initViews(parentView)
        return parentView
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                replenishmentPresenter.onUpButtonPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun setWalletNameTitle(name: String) {
        walletNameTitleTextView.text = name
    }

    private fun initViews(parentView: View) {
        initToolbar(parentView)
        initSpinners(parentView)
        walletNameTitleTextView = parentView.findViewById(R.id.wallet_name_text_view)
        val addReplenishmentButton: Button = parentView.findViewById(R.id.add_replenishment_button)
        addReplenishmentButton.setOnClickListener {
            val operationType = operationTypeSpinner.selectedItem as? OperationType
            val currency = currencySpinner.selectedItem as? Currency
            val amount = amountSpinner.selectedItem as? Long
            when (operationType) {
                OperationType.Enrollment -> replenishmentPresenter.onAddReplenishmentButtonClick(operationType, replenishmentCategorySpinner.selectedItem as? EnrollmentCategory, null, currency, amount)
                OperationType.Expense -> replenishmentPresenter.onAddReplenishmentButtonClick(operationType, null, replenishmentCategorySpinner.selectedItem as? ExpenseCategory, currency, amount)
            }
        }
    }

    private fun initSpinners(parentView: View) {

        operationTypeSpinner = parentView.findViewById(R.id.operationTypeSpinner)
        replenishmentCategorySpinner = parentView.findViewById(R.id.categorySpinner)
        currencySpinner = parentView.findViewById(R.id.currencySpinner)
        amountSpinner = parentView.findViewById(R.id.amountSpinner)

        val operationTypeAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, arrayOf(OperationType.Enrollment, OperationType.Expense))
        operationTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        operationTypeSpinner.adapter = operationTypeAdapter

        val currencyAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, arrayOf(Currency.RUB, Currency.USD))
        currencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.adapter = currencyAdapter

        val amountAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, arrayOf<Long>(10, 50, 100, 1000, 2000, 5000))
        amountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        amountSpinner.adapter = amountAdapter

        val defaultCategoryAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, arrayOf(Salary, Cache, Transfer))
        val expenseCategoryAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, arrayOf(Transport, Clothing, Health, Restaurant, Supermarket))
        defaultCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        replenishmentCategorySpinner.adapter = defaultCategoryAdapter
        operationTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                when (i) {
                    0 -> replenishmentCategorySpinner.adapter = defaultCategoryAdapter
                    else -> replenishmentCategorySpinner.adapter = expenseCategoryAdapter
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
    }

    private fun initToolbar(parentView: View) {
        val toolbar = parentView.findViewById<Toolbar>(R.id.toolbar)
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}