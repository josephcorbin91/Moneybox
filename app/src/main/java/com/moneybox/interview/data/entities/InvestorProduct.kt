/*
 * Copyright 2021 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moneybox.interview.data.entities

import com.google.gson.annotations.SerializedName
import com.moneybox.interview.data.response.ProductResponse

data class InvestorProduct (
        @SerializedName( "MoneyboxEndOfTaxYear")
    val moneyboxEndOfTaxYear: String,

        @SerializedName( "TotalPlanValue")
    val totalPlanValue: Double,

        @SerializedName( "TotalEarnings")
    val totalEarnings: Double,

        @SerializedName( "TotalContributionsNet")
    val totalContributionsNet: Double,

        @SerializedName( "TotalEarningsAsPercentage")
    val totalEarningsAsPercentage: Double,

        @SerializedName( "ProductResponses")
    val productResponses: List<ProductResponse>,

        @SerializedName( "Accounts")
    val accounts: List<Account>
)

data class Account (
    @SerializedName( "Type")
    val type: String,

    @SerializedName( "Name")
    val name: String,

    @SerializedName( "Wrapper")
    val wrapper: Wrapper
)

data class Wrapper (
    @SerializedName( "Id")
    val id: String,

    @SerializedName( "TotalValue")
    val totalValue: Double,

    @SerializedName( "TotalContributions")
    val totalContributions: Double,

    @SerializedName( "EarningsNet")
    val earningsNet: Double,

    @SerializedName( "EarningsAsPercentage")
    val earningsAsPercentage: Double
)


data class AssetBox (
    @SerializedName( "Title")
    val title: String
)

data class Contributions (
    @SerializedName( "Status")
    val status: String
)

data class InvestorAccount (
    @SerializedName( "ContributionsNet")
    val contributionsNet: Double,

    @SerializedName( "EarningsNet")
    val earningsNet: Double,

    @SerializedName( "EarningsAsPercentage")
    val earningsAsPercentage: Double,

    @SerializedName( "TodaysInterest")
    val todaysInterest: Double? = null
)

data class MoneyboxCircle (
    @SerializedName( "State")
    val state: String
)

data class Personalisation (
        @SerializedName( "QuickAddDeposit")
    val quickAddDeposit: QuickAddDeposit,

        @SerializedName( "HideAccounts")
    val hideAccounts: HideAccounts
)

data class HideAccounts (
    @SerializedName( "Enabled")
    val enabled: Boolean,

    @SerializedName( "IsHidden")
    val isHidden: Boolean,

    @SerializedName( "Sequence")
    val sequence: Long
)

data class QuickAddDeposit (
    @SerializedName( "Amount")
    val amount: Double
)

data class Product (
        @SerializedName( "Id")
    val id: Long,

        @SerializedName( "Name")
    val name: String,

        @SerializedName( "CategoryType")
    val categoryType: String,

        @SerializedName( "Type")
    val type: String,

        @SerializedName( "FriendlyName")
    val friendlyName: String,

        @SerializedName( "CanWithdraw")
    val canWithdraw: Boolean,

        @SerializedName( "ProductHexCode")
    val productHexCode: String,

        @SerializedName( "AnnualLimit")
    val annualLimit: Double,

        @SerializedName( "DepositLimit")
    val depositLimit: Double,

        @SerializedName( "BonusMultiplier")
    val bonusMultiplier: Double,

        @SerializedName( "MinimumWeeklyDeposit")
    val minimumWeeklyDeposit: Double,

        @SerializedName( "MaximumWeeklyDeposit")
    val maximumWeeklyDeposit: Double,

        @SerializedName( "Documents")
    val documents: Documents,

        @SerializedName( "State")
    val state: String,

        @SerializedName( "Lisa")
    val lisa: Lisa? = null,

        @SerializedName( "InterestRate")
    val interestRate: String? = null,

        @SerializedName( "InterestRateAmount")
    val interestRateAmount: Double? = null,

        @SerializedName( "LogoUrl")
    val logoURL: String? = null,

        @SerializedName( "Fund")
    val fund: Fund? = null
)

data class Documents (
    @SerializedName( "KeyFeaturesUrl")
    val keyFeaturesURL: String
)

data class Fund (
    @SerializedName( "FundId")
    val fundID: Long,

    @SerializedName( "Name")
    val name: String,

    @SerializedName( "LogoUrl")
    val logoURL: String,

    @SerializedName( "IsFundDMB")
    val isFundDMB: Boolean
)

data class Lisa (
    @SerializedName( "MaximumBonus")
    val maximumBonus: Double
)