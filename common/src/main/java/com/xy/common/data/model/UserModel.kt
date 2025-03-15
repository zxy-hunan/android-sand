package com.xy.common.data.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.serialization.Serializable

/**
 * @file UserModel
 * @author zxy
 * @date 2025/3/15 13:59
 * @brief
 */

@Serializable
data class UserModel(
/*    "userId": 100,
    "deptId": null,
"userName": "15711974110",
"nickName": "StarryRiver",
"email": "",
"phonenumber": "",
"sex": "0",
"avatar": "/profile/avatar/2024/06/14/1_20240614092039A001.png",
"password": "$2a$10$NyEeRgkpOikgakIAcBykKOZVhMyaCWuXxlzGsMtIH7lbfNC34jzza",
"status": "0",
"delFlag": "0",*/
    val userId: Int=0,
//    val comStar: Int?=0,
    val userName: String?="",
    val nickName: String?="",
    val phonenumber: String?="",
    val sex: Int=0,
    val avatar: String?="",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeString(userName)
        parcel.writeString(nickName)
        parcel.writeString(phonenumber)
        parcel.writeInt(sex)
        parcel.writeString(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}