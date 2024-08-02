package com.example.listatelefonica.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.listatelefonica.R
import com.example.listatelefonica.database.ContactDAO
import com.example.listatelefonica.databinding.ActivityNewContactBinding
import com.example.listatelefonica.model.ContactModel

class NewContactActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityNewContactBinding.inflate(layoutInflater)
    }
    private val  contactDAO by lazy {
        ContactDAO(this)
    }
    private val i by lazy {
        intent
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


        binding.btnAdd.setOnClickListener {
            if (checkFields()) {
                addContact(
                    binding.editName.text.toString(),
                    binding.editName.text.toString(),
                    binding.editEmail.text.toString(),
                    binding.editPhone.text.toString().toInt(),
                    selectGenderToPhoto()
                )

            }
        }


        binding.btnCancel.setOnClickListener {
            setResult(Activity.RESULT_CANCELED, i)
            finish()
        }
    }


    private fun addContact(name: String, address: String, email: String, phone: Int, img: String) {
        if (contactDAO.insert(
                ContactModel(
                    name = name,
                    address = address,
                    email = email,
                    phone = phone,
                    imageId = img
                )
            )
        ) {
            Toast.makeText(this, "Contact added successfully", Toast.LENGTH_LONG).show()
            setResult(Activity.RESULT_OK, i)
            finish()
        } else {
            Toast.makeText(this, "Error, Contact not added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkFields(): Boolean {

        val name = binding.editName.text.toString()
        val address = binding.editAddress.text.toString()
        val email = binding.editEmail.text.toString()
        val phone = binding.editPhone.text.toString()


        if (name.isNotEmpty() && address.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && selectGenderToPhoto()!= "") {
            return true
        } else {
            //Para tirar a mensagem de erro quando digitar novamente
            binding.tfName.error = null
            binding.tfAddress.error = null
            binding.tfEmail.error = null
            binding.tfPhone.error = null

            if(selectGenderToPhoto() == ""){
                Toast.makeText(this,"Select a Gender", Toast.LENGTH_SHORT).show()
            }
            if (name.isEmpty()) {
                binding.tfName.error = "Name cannot be empty!"
            }
            if (address.isEmpty()) {
                binding.tfAddress.error = "Address cannot be empty!"
            }
            if (email.isEmpty()) {
                binding.tfEmail.error = "Email cannot be empty!"
            }
            if (phone.isEmpty()) {
                binding.tfPhone.error = "Phone cannot be empty!"
            }
            return false

        }

    }

    private fun selectGenderToPhoto():String{
        if(binding.rbMale.isChecked){
            return "1"
        }else if(binding.rbFemale.isChecked){
            return "2"
        }else{
            return ""
        }
    }


}