
import com.xy.common.data.model.ArticleModel
import com.xy.common.data.model.UserModel
import com.xy.mviframework.network.def.BaseRes
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * @file MainApiService
 * @author zxy
 * @date 2024/7/19 11:31
 * @brief mainapiserviice
 */
interface UserApiService {
    @POST("loginOS")
    fun login(@Body params: Map<String,String>): Flow<BaseRes<String>>

    @GET("getInfo")
    fun getInfo(@Header("Authorization") token: String): Flow<BaseRes<UserModel>>

}