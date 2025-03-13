

sealed class FlowBusTag {
    /**
     * 用户信息改变 编辑用户本地数据
     */
    data class UserInfoNativeDataSucceed(val profileCompleteness: Int = 0) :
        FlowBusTag()


    data class LoginSuccess(val tag: Int = 0) :FlowBusTag()

}
