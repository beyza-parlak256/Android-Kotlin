package com.beyzaparlak.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.beyzaparlak.sharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // Shared Preferences - XML e kaydeder - key/value
        sharedPref = this.getSharedPreferences("com.beyzaparlak.sharedpreferences", Context.MODE_PRIVATE)

        // shared a kayıtlı birşey yoksa -1, varsa değeri döndürür
        val userAgePref = sharedPref.getInt("age",-1)
        if(userAgePref == -1){
            binding.textView.text = "Your age :"
        }else{
            binding.textView.text = "Your age : ${userAgePref}"
        }
    }

    fun save (view: View){
        val myAge = binding.age.text.toString().toIntOrNull()
        if (myAge != null){
            binding.textView.text = "Your age : ${myAge}"
            sharedPref.edit().putInt("age", myAge).apply()
        }
    }

    fun delete (view: View){
        val userAgePref = sharedPref.getInt("age",-1)
        if(userAgePref != -1){
            sharedPref.edit().remove("age").apply()
            binding.textView.text = "Your age : "
        }

    }
}
