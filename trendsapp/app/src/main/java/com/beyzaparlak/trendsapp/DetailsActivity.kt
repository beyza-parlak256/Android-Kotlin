package com.beyzaparlak.trendsapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.beyzaparlak.trendsapp.databinding.ActivityDetailsBinding
import com.beyzaparlak.trendsapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        drawerLayout = binding.drawerLayout

        // SharedPreferences tanımlaması
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")

        // NavHostFragment ve NavController'ı ayarlar
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // fragment configrasyonu
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.categoryFragment, R.id.productSearchFragment,
                R.id.favoriteProductsFragment, R.id.ordersFragment, R.id.logoutFragment
            ), drawerLayout
        )

        setSupportActionBar(binding.toolbar3)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navigationView, navController)


        // remote config ile backgroundColor change islemi
        FirebaseApp.initializeApp(this)

        // initialize Firebase Remote Config
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        // veri cekme ve etkinlestirme islemleri
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.e("Datax", "Config params updated: $updated")
                    // toast mesajı
                    Toast.makeText(this, "Fetch and activate succeeded", Toast.LENGTH_SHORT,).show()
                } else { // toast mesajı
                    Toast.makeText(this, "Fetch failed", Toast.LENGTH_SHORT,).show()
                }
            }

        // guncellenen backgroundColor degerini alıyorum
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                Log.e("onUpdate", "Updated keys: " + configUpdate.updatedKeys)
                if (configUpdate.updatedKeys.contains("backgroundColor")) {
                    remoteConfig.activate().addOnCompleteListener {
                        if (it.isSuccessful) {
                            val color = FirebaseRemoteConfig.getInstance().getString("backgroundColor")
                            Log.d("Pull Color", color)
                        }
                    }
                }
            }
            override fun onError(error: FirebaseRemoteConfigException) {
                Log.w("onError", "Config update error with code: " + error.code, error)
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
    }
}