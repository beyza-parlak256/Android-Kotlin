package com.beyzaparlak.alertdialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Toast, Alert Dialog, Snackbar
        // Context: kullanıcı etkileşimi olan yerlerde, android nerede ne iş yaptığını anlamalı

        // Activty Context: this
        // Application Context: applicationContext

        Toast.makeText(this, "Welcome",Toast.LENGTH_LONG).show()

        /*
        // lambda
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            println("button clicked")
        }
        */
    }

    fun save(view: View){
        val alert = AlertDialog.Builder(this@MainActivity)
        alert.setTitle("Title")
        alert.setMessage("Are you sure?")
        alert.setPositiveButton("Yes", object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                Toast.makeText(this@MainActivity, "Saved",Toast.LENGTH_LONG).show()
            }
        })
        alert.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                Toast.makeText(this@MainActivity, "Deleted",Toast.LENGTH_LONG).show()
            }


        })
        alert.show()
    }
}