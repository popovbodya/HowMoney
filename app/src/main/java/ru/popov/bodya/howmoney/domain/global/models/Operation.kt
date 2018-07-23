package ru.popov.bodya.howmoney.domain.global.models

/**
 *  @author popovbodya
 */
data class Operation(val operationType: OperationType, val currency: Currency, val amount: Long)