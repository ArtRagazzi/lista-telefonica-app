package com.example.listatelefonica.ui


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listatelefonica.R
import com.example.listatelefonica.databinding.ActivityLoginBinding
import com.example.listatelefonica.service.UserService

class LoginActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var sharedPreferences: SharedPreferences


    private val userService by lazy {
        UserService(this)
    }

    override fun onStart() {
        super.onStart()
        sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)

        val savedUsername = sharedPreferences.getString("username", null)
        if (savedUsername != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
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



        binding.txtSignup.setOnClickListener {

            startActivity(Intent(this, SignUpActivity::class.java))
        }


        binding.btnLogin.setOnClickListener {
            if (userService.login(binding.editUsername.text.toString(),binding.editPassword.text.toString())) {
                //Nesse ponto o usuario ja foi validado, entao não e necessario realizar o login no onStart
                if (binding.cbKeepLogin.isChecked) {
                    keepLogin()
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } else {
                Toast.makeText(this, "Username or password incorrect", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun keepLogin() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("username", binding.editUsername.text.toString())
        editor.apply()
    }
}