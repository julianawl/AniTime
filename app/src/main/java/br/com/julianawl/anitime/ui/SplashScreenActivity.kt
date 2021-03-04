package br.com.julianawl.anitime.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import br.com.julianawl.anitime.MainActivity
import br.com.julianawl.anitime.R


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        Handler(Looper.getMainLooper()).postDelayed({
                startApp()
        }, 2000)
    }
    private fun startApp() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}


