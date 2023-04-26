
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ILoginService {
    @POST("/login")
    suspend  fun login(@Body body: HashMap<String, Any>): Response<UserModel?>
}