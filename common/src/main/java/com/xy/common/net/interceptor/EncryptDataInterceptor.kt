
import com.drake.net.request.MediaConst
import okhttp3.Interceptor
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer

/**
 * 演示如何加密请求参数/解密响应数据
 */
class EncryptDataInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // 加密, 仅加密 POST请求/JSON参数类型
        if (request.method == "POST" && request.header("Content-Type") == MediaConst.JSON.toString()) {
            val body = request.body
            if (body != null) {
                val buff = Buffer()
                body.writeTo(buff)
                val json = buff.readUtf8()
                if (json.isNotBlank()) {
                    val encryptJson = AESUtils.encrypt(json)
                    val newBody = encryptJson.toRequestBody(MediaConst.JSON)
                    request = request.newBuilder().post(newBody).build()
                }
            }
        }

        var response = chain.proceed(request)

        // 解密, 仅解密JSON响应类型
        if (response.header("Content-Type") == MediaConst.JSON.toString()) {
            val body = response.body
            if (body != null) {
                val json = body.string()
                if (json.isNotBlank()) {
                    val decryptJson = AESUtils.decrypt(json)
                    val requestBody = decryptJson.toResponseBody(MediaConst.JSON)
                    response = response.newBuilder().body(requestBody).build()
                }
            }
        }

        return response
    }
}