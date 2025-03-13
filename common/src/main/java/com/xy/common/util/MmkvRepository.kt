package com.xy.common.util

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvInt
import com.dylanc.mmkv.mmkvParcelable
import com.dylanc.mmkv.mmkvString

/**
 * @file MmkvRepository
 * @author zxy
 * @date 2025/1/23 16:44
 * @brief MmkvRepository
 */
object MmkvRepository : MMKVOwner{

    var imAppId by mmkvInt(
        default = 1600045254
    )

    var loginToken by mmkvString(default = "")

}