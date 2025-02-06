package com.xy.common.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @file BaseModel
 * @author zxy
 * @date 2024/7/19 11:41
 * @brief nullmodel
 */
@Serializable
class BaseModel {
}

@Serializable
data class BaseResultModel(
    @SerialName("msg")
    val msg: String = "",
    @SerialName("code")
    val code: Int ,
    @SerialName("token")
    val token: String="",
)