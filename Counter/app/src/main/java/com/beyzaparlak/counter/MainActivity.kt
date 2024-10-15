package com.beyzaparlak.counter

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.beyzaparlak.counter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Abstract - Interface - Inheritance
        // kaçtan geriye, kaçar kaçar
        object  : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) { // her tikte ne yapayım ?
                binding.textView.text = "Left: ${p0 / 1000}"
            }
            override fun onFinish() { // bitince ne yapayım ?
                binding.textView.text = "Left: 0"
            }
        }.start()





    }
}