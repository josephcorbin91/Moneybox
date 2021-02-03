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

package com.moneybox.interview.data

import com.google.gson.annotations.SerializedName

/**
 * User Session object required to make future api requests
 *
 * @param bearerToken used to make authenticated api requests
 * @param externalSessionId id of external session
 * @param sessionExternalId id of external session
 * @param expiryInSeconds expiry of session
 */
data class UserSession (
    @SerializedName("BearerToken")
    val bearerToken: String,
    @SerializedName("ExternalSessionId")
    val externalSessionId: String,
    @SerializedName("SessionExternalId")
    val sessionExternalId: String,
    @SerializedName("ExpiryInSeconds")
    val expiryInSeconds: Int
)