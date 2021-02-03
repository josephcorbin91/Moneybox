package com.moneybox.interview.data.request

import com.google.gson.annotations.SerializedName

/**
 * One off  payment request body to add money to an account
 *
 * @param amount to be added
 * @param investorProductId id of product
 */
data class OneOffPaymentRequestBody (
    @SerializedName("Amount")
    val amount: Int,
    @SerializedName("InvestorProductId")
    val investorProductId: Long?
)