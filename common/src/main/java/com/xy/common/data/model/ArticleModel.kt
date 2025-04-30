package com.xy.common.data.model

import android.graphics.drawable.Drawable
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
    var imageurl: String = "",
    @SerialName("arpath")
    val arpath: String = "",
    @SerialName("status")
    val status: Int = 0,
    @SerialName("title")
    var title: String = "",
    @SerialName("sysUser")
    var sysUser: SysUserModel = SysUserModel(),
    @SerialName("starNum")
    var starNum: Int? = 0,
    @SerialName("videos")
    var videos: KyVideoModel? = null,
    @SerialName("images")
    var images: List<BingImgModel>? = null,
) : java.io.Serializable


@Serializable
data class SysUserModel(
    @SerialName("nickName")
    var nickName: String = "",
    @SerialName("userName")
    val userName: String = "",
    @SerialName("avatar")
    var avatar: String = "",
    @SerialName("userId")
    val userId: Int? = 0,
) : java.io.Serializable

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
) : java.io.Serializable


@Serializable
data class CnModel(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("type")
    val type: Int = 0,
    @SerialName("name")
    val name: String = "分类",
    @SerialName("list")
    val list: List<CnModel> = mutableListOf(),
    @SerialName("url")
    val url: Int = 0,
) : java.io.Serializable


@Serializable
class KyBaseModel<T>(
    @SerialName("code")
    val code: Int = 0,
    @SerialName("message")
    val message: String = "",
    @SerialName("result")
    val result: T
) : java.io.Serializable


@Serializable
class KyImageResultModel(
    @SerialName("total")
    val total: Int = 0,
    @SerialName("list")
    val list: List<KyImageModel> = listOf(),
) : java.io.Serializable

@Serializable
class KyImageModel(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("title")
    val title: String = "",
    @SerialName("url")
    val url: String = "",
    @SerialName("type")
    val type: String = "",
) : java.io.Serializable


@Serializable
class KyVideoResultModel(
    @SerialName("total")
    val total: Int = 0,
    @SerialName("list")
    val list: List<KyVideoModel> = listOf(),
) : java.io.Serializable
@Serializable
class KyVideoModel(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("title")
    val title: String = "",
    @SerialName("userName")
    val userName: String = "",
    @SerialName("userPic")
    val userPic: String = "",
    @SerialName("coverUrl")
    val coverUrl: String = "",
    @SerialName("playUrl")
    val playUrl: String = "",
    @SerialName("duration")
    val duration: String = "",
) : java.io.Serializable



@Serializable
data class BingImgModel(
    @SerialName("title")
    val title: String = "",
    @SerialName("fullUrl")
    val fullUrl: String = "",
    @SerialName("thumbUrl")
    val thumbUrl: String = "",
    @SerialName("date")
    val date: String = "",
    @SerialName("imageUrl")
    val imageUrl: String = "",
    @SerialName("copyright")
    val copyright: String = "",
    @SerialName("pageUrl")
    val pageUrl: String = "",
) : java.io.Serializable