package com.moneybox.interview.data.response

import com.google.gson.annotations.SerializedName
import com.moneybox.interview.data.entities.AssetBox
import com.moneybox.interview.data.entities.Contributions
import com.moneybox.interview.data.entities.InvestorAccount
import com.moneybox.interview.data.entities.MoneyboxCircle
import com.moneybox.interview.data.entities.Personalisation
import com.moneybox.interview.data.entities.Product

data class ProductResponse (
        @SerializedName( "Id")
    val id: Long,

        @SerializedName( "PlanValue")
    val planValue: Double,

        @SerializedName( "Moneybox")
    val moneybox: Double,

        @SerializedName( "SubscriptionAmount")
    val subscriptionAmount: Double,

        @SerializedName( "TotalFees")
    val totalFees: Double,

        @SerializedName( "IsSelected")
    val isSelected: Boolean,

        @SerializedName( "IsFavourite")
    val isFavourite: Boolean,

        @SerializedName( "CollectionDayMessage")
    val collectionDayMessage: String,

        @SerializedName( "WrapperId")
    val wrapperID: String,

        @SerializedName( "IsCashBox")
    val isCashBox: Boolean,

        @SerializedName( "AssetBox")
    val assetBox: AssetBox,

        @SerializedName( "Product")
    val product: Product,

        @SerializedName( "InvestorAccount")
    val investorAccount: InvestorAccount,

        @SerializedName( "Personalisation")
    val personalisation: Personalisation,

        @SerializedName( "Contributions")
    val contributions: Contributions,

        @SerializedName( "MoneyboxCircle")
    val moneyboxCircle: MoneyboxCircle,

        @SerializedName( "State")
    val state: String
)

