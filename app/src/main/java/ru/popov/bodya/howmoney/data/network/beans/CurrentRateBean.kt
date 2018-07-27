package ru.popov.bodya.howmoney.data.network.beans

import com.google.gson.annotations.SerializedName

/**
 * @author popovbodya
 */
data class CurrentRateBean(@SerializedName("date") val date: String,
                           @SerializedName("result") val result: Double)