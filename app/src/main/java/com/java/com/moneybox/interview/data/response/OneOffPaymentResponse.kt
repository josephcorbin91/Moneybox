package com.moneybox.interview.data.response

import com.google.gson.annotations.SerializedName

/**
 * One off  payment response

 */
data class OneOffPaymentResponse (
    @SerializedName("Moneybox")
    val moneyBox: Double
)