package com.example.listatelefonica.ui

import android.content.Intent
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


    private val userService by lazy {
        UserService(this)
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
            if(userService.login(binding.editUsername.text.toString(),binding.editPassword.text.toString())){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this, "Username or password incorrect", Toast.LENGTH_LONG).show()
            }
        }
    }
}