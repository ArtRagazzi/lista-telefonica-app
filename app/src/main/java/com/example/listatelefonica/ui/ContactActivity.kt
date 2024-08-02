package com.example.listatelefonica.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.listatelefonica.R
import com.example.listatelefonica.database.ContactDAO
import com.example.listatelefonica.databinding.ActivityContactBinding
import com.example.listatelefonica.model.ContactModel

class ContactActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }
    private val contactDao by lazy {
        ContactDAO(this)
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

        val i = intent
        val idContact = i.extras?.getInt("id")

        if(idContact != null){
           var contact = findUser(idContact)
            loadFields(contact)
        }else{
            finish()
        }

        binding.fabSave.setOnClickListener {
            if(idContact != null){
                updateContact(idContact)
                setResult(Activity.RESULT_OK,i)
                finish()
            }else{
                setResult(Activity.RESULT_CANCELED,i)
                Toast.makeText(this,"Cannot be Saved",Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabDelete.setOnClickListener {
            if(idContact != null){
                deleteContact(idContact)
                setResult(Activity.RESULT_OK,i)
                finish()
            }else{
                setResult(Activity.RESULT_CANCELED,i)
                Toast.makeText(this,"Cannot be Deleted",Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun findUser(id:Int):ContactModel{
        val contact = contactDao.findById(id)
        return contact
    }

    private fun loadFields(contact:ContactModel){
        loadPhoto(contact)
        binding.editName.setText(contact.name)
        binding.editAddress.setText(contact.address)
        binding.editEmail.setText(contact.email)
        binding.editPhone.setText(contact.phone)
    }

    private fun loadPhoto(contact:ContactModel){
        if(contact.imageId == "1"){
            Glide.with(this)
                .load(R.drawable.male)
                .into(binding.ivContact)
        }else if(contact.imageId == "2"){
            Glide.with(this)
                .load(R.drawable.female)
                .into(binding.ivContact)
        }else{
            Glide.with(this)
                .load(R.drawable.male)
                .into(binding.ivContact)
        }
    }

    private fun deleteContact(id:Int){
        contactDao.remove(id)
    }

    private fun updateContact(id:Int){
        var contactOld = findUser(id)
        contactOld.name = binding.editName.text.toString()
        contactOld.address = binding.editAddress.text.toString()
        contactOld.email = binding.editEmail.text.toString()
        contactOld.phone = binding.editPhone.text.toString()
        contactDao.update(contactOld)
    }
}