
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository(private val context: Context) : BaseDataSource(context) {

    private val remote = RetrofitClient.getService(ILoginService::class.java)

    suspend fun login(email: String, password: String) : Flow<ApiState<UserModel?>> {

        val device = Device(context)

        val body = HashMap<String, Any>()

        body["Email"] = email
        body["Senha"] = password
        body["Modelo"] = device.getNameAndModel()

        return flow {
            val result = safeApiCall<UserModel?> { remote.login(body) }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}