
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<ApiState<UserModel?>>(ApiState.InProgress)
    val loginState: StateFlow<ApiState<UserModel?>> = _loginState

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.login(email,password)
                .catch {e->
                    _loginState.value = ApiState.Error(e.message ?: "Unknown error")
                }
                .collect{
                        item ->_loginState.value = item
                }
        }
    }
    class LoginViewModelFactory(private val repository: LoginRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                return LoginViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}