package ru.popov.bodya.howmoney.presentation.mvp.addtransaction

import ru.popov.bodya.core.mvp.AppView

interface AddTransactionView : AppView {
    fun onTransactionCreated()
}