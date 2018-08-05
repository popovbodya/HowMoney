package ru.popov.bodya.howmoney.presentation.ui.addtransaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import dagger.android.support.AndroidSupportInjection
import ru.popov.bodya.core.mvp.AppFragment
import ru.popov.bodya.howmoney.R
import ru.popov.bodya.howmoney.domain.wallet.models.Category
import ru.popov.bodya.howmoney.domain.wallet.models.Currency
import ru.popov.bodya.howmoney.domain.wallet.models.Transaction
import ru.popov.bodya.howmoney.presentation.mvp.addtransaction.AddTransactionPresenter
import ru.popov.bodya.howmoney.presentation.mvp.addtransaction.AddTransactionView
import ru.popov.bodya.howmoney.presentation.ui.account.activities.AccountActivity
import ru.popov.bodya.howmoney.presentation.ui.common.BaseFragment
import java.util.*
import javax.inject.Inject

class AddTransactionFragment : BaseFragment(), AddTransactionView {

    override val TAG: String
        get() = "ADD_TRANSACTION_FRAGMENT"
    override val layoutRes: Int
        get() = R.layout.fragment_add_transaction
    override val toolbarTitleId: Int
        get() = R.string.create_transaction

    @Inject
    @InjectPresenter
    lateinit var addTransactionPresenter: AddTransactionPresenter

    @ProvidePresenter
    fun provideAddTransactionPresenterPresenter(): AddTransactionPresenter = addTransactionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun setUpToolbarTitle(resId: Int) {
        (activity as AccountActivity).updateToolBar(resId)
    }

    override fun onTransactionCreated() {
        addTransactionPresenter.closeFragment()
    }
}