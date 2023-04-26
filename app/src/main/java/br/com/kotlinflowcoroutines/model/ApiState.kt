
sealed class ApiState<out T> {
    object InProgress : ApiState<Nothing>()
    data class Success<T>(val data: T?, val message: String = "") : ApiState<T>()
    data class Error(val message: String) : ApiState<Nothing>()
    data class Failed(val message: String) : ApiState<Nothing>()

}