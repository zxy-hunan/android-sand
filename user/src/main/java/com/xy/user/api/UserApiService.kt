
import com.xy.mviframework.network.def.BaseRes
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @file MainApiService
 * @author zxy
 * @date 2024/7/19 11:31
 * @brief mainapiserviice
 */
interface UserApiService {
    @POST("loginOS")
    fun login(@Body params: Map<String,String>): Flow<BaseRes<String>>
}