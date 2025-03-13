package com.xy.common.data.model

data class ArticleModel(
    val comId: Int,
    val comStar: Int?=0,
    val commNum: Int?=0,
    val content: String,
    val createTime: String,
    val id: Int,
    val imageurl: String,
    val arpath: String,
    val status:Int,
    val title:String,
    var sysUser: SysUserModel,
    var starNum:Int?=0,
)


data class SysUserModel(
    val nickName: String,
    val userName: String,
    val avatar: String,
    val userId: Int,
)

