package br.com.kotlinflowcoroutines

import LoginRepository
import LoginViewModel
import UserModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.kotlinflowcoroutines.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(
            this,
            LoginViewModel.LoginViewModelFactory(LoginRepository(this))
        )[LoginViewModel::class.java]

        binding = ActivityLoginBinding.inflate(layoutInflater)
        handleLogin()
        observe()
    }
    private fun handleLogin() {
        val email = "myemail@gmail.teste"
        val password = "123"
        viewModel.login(email, password)
    }

    /*
    *
    * Teste result login
    * */
    private fun observe() {
        lifecycleScope.launch {
            viewModel.loginState.collect { state ->
                when (state) {
                    is ApiState.InProgress -> {
                        Toast.makeText(this@LoginActivity, "InProgress", Toast.LENGTH_SHORT).show()
                    }
                    is ApiState.Success<UserModel?> -> {
                        Toast.makeText(
                            this@LoginActivity,
                            "Login realizado com sucesso",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ApiState.Error -> {
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    is ApiState.Failed -> {
                        Toast.makeText(this@LoginActivity, state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this@LoginActivity, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}