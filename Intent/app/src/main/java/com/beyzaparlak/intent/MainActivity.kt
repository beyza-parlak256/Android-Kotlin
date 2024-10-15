package com.beyzaparlak.intent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.beyzaparlak.intent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtName.setText("New data")

        binding.btnSend.setOnClickListener {
            // tıklanınca hangi activityden hangisine gecilecek
            val i = Intent(this, ProfileActivity :: class.java)
            // name bilgisini profileActivity e aktarmak istiyorum
            i.putExtra("dataKey", binding.txtName.text.toString())

            startActivity(i)
            // bu nesneyi öldür. geriye dönünce eski bilgiler silinir
            finish()
        }
    }
}