package com.beyzaparlak.trendsapp.uiFragment

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.beyzaparlak.trendsapp.R

class ProfileFragment : Fragment() {

    private lateinit var progressBar: ProgressBar
    private lateinit var btnUpdate: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // SharedPreferences tanımlaması
        var sharedPreferences = activity?.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        progressBar = view.findViewById(R.id.progressBar)
        btnUpdate = view.findViewById(R.id.btnUpdate)

        // SharedPreferences üzerinden kullanıcı bilgilerini alma
        val firstName = sharedPreferences?.getString("firstName", "")
        val lastName = sharedPreferences?.getString("lastName", "")
        val gender = sharedPreferences?.getString("gender", "")
        val username = sharedPreferences?.getString("username", "")
        val password = sharedPreferences?.getString("password", "")

        // TextView'lere kullanıcı bilgilerini yerleştiriyorum
        view.findViewById<EditText>(R.id.tvFirstName).setText(firstName)
        view.findViewById<EditText>(R.id.tvLastName).setText(lastName)
        view.findViewById<EditText>(R.id.tvGender).setText(gender)
        view.findViewById<EditText>(R.id.tvUsername).setText(username)
        view.findViewById<EditText>(R.id.tvPassword).setText(password)

        // update butona tıklandığındaki islemler
        btnUpdate.setOnClickListener {
            // Güncelleme işlemi başladığında progress bar'ı gösterir
            progressBar.visibility = View.VISIBLE

            // Yeni değerleri alıyorum
            val updatedFirstName = view.findViewById<EditText>(R.id.tvFirstName).text.toString()
            val updatedLastName = view.findViewById<EditText>(R.id.tvLastName).text.toString()
            val updatedGender = view.findViewById<EditText>(R.id.tvGender).text.toString()
            val updatedUsername = view.findViewById<EditText>(R.id.tvUsername).text.toString()
            val updatedPassword = view.findViewById<EditText>(R.id.tvPassword).text.toString()

            // SharedPreferences üzerinden kullanıcı bilgilerini günceller
            val sharedPreferences = activity?.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            sharedPreferences?.edit()?.apply {
                putString("firstName", updatedFirstName)
                putString("lastName", updatedLastName)
                putString("gender", updatedGender)
                putString("username", updatedUsername)
                putString("password", updatedPassword)
                apply()
            }

            // Simülasyon amaçlı 2 saniye sonra progress bar'ı gizler
            Handler(Looper.getMainLooper()).postDelayed({
                progressBar.visibility = View.GONE
                Toast.makeText(activity, "User information updated!", Toast.LENGTH_SHORT).show()
            }, 2000)
        }

        return view
    }

}