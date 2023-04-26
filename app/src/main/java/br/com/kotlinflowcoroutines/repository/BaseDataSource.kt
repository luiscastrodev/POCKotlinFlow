
import ResponseModelDefault
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import br.com.kotlinflowcoroutines.R
import com.google.gson.Gson
import retrofit2.Response

abstract class BaseDataSource(private val context: Context) {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiState<T> {

        try {
            if (!isConnectionAvailable()) {
                return ApiState.Error(message = context.getString(R.string.ERROR_INTERNET_CONNECTION))
            }

            val response = apiCall()

            if (response.isSuccessful) {
                val body = response.body()
                return if (body != null) {
                    val bodyConvert = response.body() as ResponseModelDefault
                    val status = bodyConvert?.status
                    val message = bodyConvert?.message ?: ""

                    if (status == Constants.APIRESULT.SUCCESS)
                        ApiState.Success(body, message)
                    else ApiState.Error(message = message)

                }else{
                    ApiState.Error(failResponse(response.errorBody()!!.string()))
                }
            } else {
                return ApiState.Error(failResponse(response.errorBody()!!.string()))
            }
            return ApiState.Failed("Something went wrong, try again")

        } catch (e: Exception) {
            return ApiState.Failed("Something went wrong, $e")
        }
    }

    private fun failResponse(json: String): String = Gson().fromJson(json, String::class.java)

    private fun isConnectionAvailable(): Boolean {
        var result = false

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNet = cm.activeNetwork ?: return false

            val netWorkCapabilities = cm.getNetworkCapabilities(activeNet) ?: return false

            result = when {
                netWorkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                netWorkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                else -> false
            }
        } else {
            if (cm.activeNetworkInfo != null) {
                result = when (cm.activeNetworkInfo!!.type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }

        return result
    }

}