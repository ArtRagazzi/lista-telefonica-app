package com.example.listatelefonica.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listatelefonica.R
import com.example.listatelefonica.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
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

        //Ativa a Toolbar
        setSupportActionBar(binding.tbContact)

    }


    //Menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.search->{
                return true
            }
            R.id.profile->{
                return true
            }
            R.id.logout->{
                logout()
                return true
            }
            else-> super.onOptionsItemSelected(item)
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }


     fun logout() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Do you really want to Log out?")
            .setNegativeButton("No"){dialog, posicao ->}
            .setPositiveButton("Yes"){dialog, posicao->
                val sharedPreferences = application.getSharedPreferences("login", Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("username",null)
                editor.apply()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()

            }
            .create().show()
    }
}