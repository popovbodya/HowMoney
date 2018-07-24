package ru.popov.bodya.howmoney.domain.operation.models

import ru.popov.bodya.howmoney.domain.account.models.Currency

/**
 *  @author popovbodya
 */
data class Operation(val operationType: OperationType, val currency: Currency, val amount: Long)