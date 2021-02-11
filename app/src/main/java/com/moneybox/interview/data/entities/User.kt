package com.moneybox.interview.data.entities

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("UserId")
    var userId: String? = null

    @SerializedName("HasVerifiedEmail")
    var hasVerifiedEmail = false

    @SerializedName("IsPinSet")
    var isPinSet = false

    @SerializedName("AmlStatus")
    var amlStatus: String? = null

    @SerializedName("AmlAttempts")
    var amlAttempts = 0

    @SerializedName("RoundUpMode")
    var roundUpMode: String? = null

    @SerializedName("JisaRoundUpMode")
    var jisaRoundUpMode: String? = null

    @SerializedName("InvestorProduct")
    var investorProduct: String? = null

    @SerializedName("RegistrationStatus")
    var registrationStatus: String? = null

    @SerializedName("JisaRegistrationStatus")
    var jisaRegistrationStatus: String? = null

    @SerializedName("DirectDebitMandateStatus")
    var directDebitMandateStatus: String? = null

    @SerializedName("DateCreated")
    var dateCreated: String? = null

    @SerializedName("Animal")
    var animal: String? = null

    @SerializedName("ReferralCode")
    var referralCode: String? = null

    @SerializedName("IntercomHmac")
    var intercomHmac: String? = null

    @SerializedName("IntercomHmaciOS")
    var intercomHmaciOS: String? = null

    @SerializedName("IntercomHmacAndroid")
    var intercomHmacAndroid: String? = null

    @SerializedName("HasCompletedTutorial")
    var hasCompletedTutorial = false

    @SerializedName("LastPayment")
    var lastPayment = 0.0

    @SerializedName("PreviousMoneyboxAmount")
    var previousMoneyboxAmount = 0.0

    @SerializedName("MoneyboxRegistrationStatus")
    var moneyboxRegistrationStatus: String? = null

    @SerializedName("Email")
    var email: String? = null

    @SerializedName("FirstName")
    var firstName: String? = null

    @SerializedName("LastName")
    var lastName: String? = null

    @SerializedName("MobileNumber")
    var mobileNumber: String? = null

    @SerializedName("RoundUpWholePounds")
    var roundUpWholePounds = false

    @SerializedName("DoubleRoundUps")
    var doubleRoundUps = false

    @SerializedName("MoneyboxAmount")
    var moneyboxAmount = 0.0

    @SerializedName("InvestmentTotal")
    var investmentTotal = 0.0

    @SerializedName("CanReinstateMandate")
    var canReinstateMandate = false

    @SerializedName("DirectDebitHasBeenSubmitted")
    var directDebitHasBeenSubmitted = false

    @SerializedName("MonthlyBoostEnabled")
    var monthlyBoostEnabled = false

    @SerializedName("MonthlyBoostAmount")
    var monthlyBoostAmount = 0.0

    @SerializedName("MonthlyBoostDay")
    var monthlyBoostDay = 0

    @SerializedName("RestrictedDevice")
    var restrictedDevice = false

    @SerializedName("EmailTwoFactorEnabled")
    var emailTwoFactorEnabled = false

    @SerializedName("Cohort")
    var cohort = 0
}

class Session {
    @SerializedName("BearerToken")
    var bearerToken: String? = null

    @SerializedName("ExternalSessionId")
    var externalSessionId: String? = null

    @SerializedName("SessionExternalId")
    var sessionExternalId: String? = null

    @SerializedName("ExpiryInSeconds")
    var expiryInSeconds = 0
}

class Action {
    @SerializedName("Label")
    var label: String? = null

    @SerializedName("Amount")
    var amount = 0.0

    @SerializedName("Type")
    var type: String? = null

    @SerializedName("Animation")
    var animation: String? = null
}

class ActionMessage {
    @SerializedName("Type")
    var type: String? = null

    @SerializedName("Message")
    var message: String? = null

    @SerializedName("Actions")
    var actions: List<Action>? = null
}

class LoginResponse {
    @SerializedName("User")
    var user: User? = null

    @SerializedName("Session")
    var session: Session? = null

    @SerializedName("ActionMessage")
    var actionMessage: ActionMessage? = null

    @SerializedName("InformationMessage")
    var informationMessage: String? = null
}