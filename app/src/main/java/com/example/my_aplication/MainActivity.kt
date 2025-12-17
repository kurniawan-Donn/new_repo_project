package com.example.my_aplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mengatur tata letak menggunakan layout XML
        setContentView(R.layout.activity_main)

        // Mengambil Fragment yang berperan sebagai NavHostFragment dari Activity
        // NavHostFragment adalah container yang menampilkan fragment sesuai NavGraph
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.catatan_fragment) as NavHostFragment

        // Mengambil NavController dari NavHostFragment
        // NavController bertugas mengatur navigasi antar Fragment
        // (pindah fragment, back stack, startDestination, dll)
        val navController = navHostFragment.navController

        // Menghubungkan BottomNavigationView dari layout XML ke kode Kotlin
        // BottomNavigationView adalah menu navigasi di bagian bawah layar
        val bottomnav =findViewById<BottomNavigationView>(R.id.bottom_nav_view)

        // Menghubungkan BottomNavigationView dengan NavController
        // Agar saat menu bottom navigation ditekan:
        // - Fragment otomatis berpindah
        // - Back stack dikelola otomatis oleh Navigation Component

        bottomnav.setupWithNavController(navController)
    }
}