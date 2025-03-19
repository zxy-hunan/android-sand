package com.xy.common.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleModel(
    @SerialName("comId")
    val comId: Int = 0,
//    val comStar: Int?=0,
    @SerialName("commNum")
    val commNum: Int? = 0,
    @SerialName("content")
    val content: String = "",
    @SerialName("createTime")
    val createTime: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("imageurl")
    val imageurl: String = "",
    @SerialName("arpath")
    val arpath: String = "",
    @SerialName("status")
    val status: Int = 0,
    @SerialName("title")
    val title: String = "",
    @SerialName("sysUser")
    var sysUser: SysUserModel = SysUserModel(),
    @SerialName("starNum")
    var starNum: Int? = 0,
) : java.io.Serializable


@Serializable
data class SysUserModel(
    @SerialName("nickName")
    val nickName: String = "",
    @SerialName("userName")
    val userName: String = "",
    @SerialName("avatar")
    val avatar: String = "",
    @SerialName("userId")
    val userId: Int? = 0,
): java.io.Serializable

data class StarArticleModel(
    val createBy: String? = "",
    val createTime: String = "",
    val updateBy: String? = "",
    val updateTime: String? = "",
    val remark: String? = "",
    val id: Int = 0,
    val articleId: Int? = 0,
    val userId: Int? = 0,
    val commId: Int? = 0
)

/*
"createBy": null,
"createTime": "2025-03-11 14:15:36",
"updateBy": null,
"updateTime": null,
"remark": null,
"id": 62,
"parId": 0,
"content": "HELLO",
"selUserId": null,*/


@Serializable
data class CommModel(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("parId")
    val parId: Int = 0,
    @SerialName("content")
    val content: String = "",
    @SerialName("comComm")
    val comComm: List<CommModel> = mutableListOf(),
    @SerialName("sysUser")
    val sysUser: SysUserModel = SysUserModel(),
    @SerialName("sysUserRe")
    val sysUserRe: SysUserModel = SysUserModel(),
): java.io.Serializable