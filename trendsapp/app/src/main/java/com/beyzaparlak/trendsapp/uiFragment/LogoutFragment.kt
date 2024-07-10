package com.beyzaparlak.trendsapp.uiFragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.beyzaparlak.trendsapp.LoginScreen
import com.beyzaparlak.trendsapp.R

class LogoutFragment : Fragment() {

    private lateinit var btnLogout: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logout, container, false)
        btnLogout = view.findViewById(R.id.btnLogout)

        // logout butona basılınca logout islemi gerceklesir
        btnLogout.setOnClickListener {
            logout()
        }

        return view
    }

    // Token'ı siler
    fun clearToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("jwt_token")
        editor.apply()
    }

    // logout islemi
    private fun logout() {
        // JWT token'ı temizler
        clearToken(requireContext())

        // Login ekranına yönlendirir
        val intent = Intent(activity, LoginScreen::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        activity?.finish()
    }
}

