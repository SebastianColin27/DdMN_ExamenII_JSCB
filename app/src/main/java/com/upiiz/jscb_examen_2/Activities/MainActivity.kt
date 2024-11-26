package com.upiiz.jscb_examen_2.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.upiiz.jscb_examen_2.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       findViewById<Button>(R.id.btnListaHeroes).setOnClickListener {
            startActivity(Intent(this, UserActivity::class.java))
        }

        findViewById<Button>(R.id.btnCreditos).setOnClickListener {
            startActivity(Intent(this, CreditosActivity::class.java))
        }

        findViewById<Button>(R.id.btnSalir).setOnClickListener {
            finish()
        }
    }
}