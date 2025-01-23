package com.xy.common.util

import com.dylanc.mmkv.MMKVOwner
import com.dylanc.mmkv.mmkvInt

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

}