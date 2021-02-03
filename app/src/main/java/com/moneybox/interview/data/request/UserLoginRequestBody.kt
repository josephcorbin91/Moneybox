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

package com.moneybox.interview.data.request

import com.google.gson.annotations.SerializedName

/**
 * User Login object required to login into backend server
 *
 * @param email of user
 * @param password of user
 * @param idfa N/A
 */
data class UserLoginRequestBody (
    @SerializedName("Email")
    val email: String,
    @SerializedName("Password")
    val password: String,
    @SerializedName("Idfa")
    val idfa: String = "Anything"
)