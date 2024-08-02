package com.example.listatelefonica.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listatelefonica.R
import com.example.listatelefonica.database.ContactDAO
import com.example.listatelefonica.databinding.ActivityMainBinding
import com.example.listatelefonica.model.ContactModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var contactList:ArrayList<ContactModel>
    private lateinit var adapter: ArrayAdapter<ContactModel>

    private val contactDAO by lazy {
        ContactDAO(this)
    }
    private lateinit var result:ActivityResultLauncher<Intent>


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

        configListView()

        binding.lvContact.setOnItemClickListener { _, _, position, _ ->
            val intent= Intent(this, ContactActivity::class.java)
            intent.putExtra("id", contactList[position].id)
            result.launch(intent)
        }

        binding.fabAdd.setOnClickListener {
           result.launch(Intent(this,NewContactActivity::class.java))
        }

        result = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.data!=null && it.resultCode == Activity.RESULT_OK){
                updateListView()
            }else if(it.data!=null && it.resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(this,"Operation Canceled", Toast.LENGTH_LONG).show()
            }else{
                //caso Null
                Toast.makeText(this,"Not Possible", Toast.LENGTH_LONG).show()
            }
        }

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

    private fun configListView(){
        //ListView
        contactList = contactDAO.findAll()
        adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,contactList)
        binding.lvContact.adapter = adapter
    }
    private fun updateListView(){
        contactList.clear()
        contactList.addAll(contactDAO.findAll())
        adapter.notifyDataSetChanged()
    }


    private fun logout() {
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