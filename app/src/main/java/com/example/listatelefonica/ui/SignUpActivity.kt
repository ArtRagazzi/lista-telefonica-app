package com.example.listatelefonica.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listatelefonica.R
import com.example.listatelefonica.database.DBHelper
import com.example.listatelefonica.database.UserDAO
import com.example.listatelefonica.databinding.ActivitySignUpBinding
import com.example.listatelefonica.model.UserModel

class SignUpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }
    private val db by lazy {
        DBHelper(this)
    }

    private val userDAO by lazy {
        UserDAO(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSignUp.setOnClickListener {
            if(validarCampos()){
                cadastrarUsuario(
                    binding.editUsername.text.toString(),
                    binding.editPassword.text.toString()
                )
            }
        }
        binding.txtAlready.setOnClickListener {
            finish()
        }
    }

    fun cadastrarUsuario(username: String, password: String) {
        if(userDAO.insert(UserModel(username = username, password = password))){
            Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            //o RETORNO FALSO PODE SER DE ALGUM ERRO NO INSERT, NAO APENAS NO USUARIO EXISTENTE
            Toast.makeText(this, "User already exist", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validarCampos(): Boolean {
        var username = binding.editUsername.text.toString()
        var password = binding.editPassword.text.toString()
        var cPassword = binding.editConfirmPassword.text.toString()
        if (username.isNotEmpty() && password.isNotEmpty() && cPassword.isNotEmpty() && password == cPassword) {
            return true
        } else {
            //Para tirar a mensagem de erro quando digitar novamente
            binding.tfUsername.error = null
            binding.tfPassword.error = null
            binding.tfConfirmPassword.error = null

            if (username.isEmpty()) {
                binding.tfUsername.error = "Username cannot be empty!"
            }
            if (password.isEmpty()) {
                binding.tfPassword.error = "Password cannot be empty!"
            }
            if (cPassword.isEmpty()) {
                binding.tfConfirmPassword.error = "Confirm your password"
            }
            if (cPassword != password) {
                binding.tfConfirmPassword.error = "Password don't match"
            }
            return false
        }
    }


}