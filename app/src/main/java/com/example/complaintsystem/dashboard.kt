package com.example.complaintsystem

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Dashboard : AppCompatActivity() {
    lateinit var logout:ImageView
    lateinit var create:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)
        logout=findViewById(R.id.logout)
        logout.setOnClickListener{
            val i= Intent(this,LoginActivity::class.java)
            startActivity(i)
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
        }

        create=findViewById(R.id.CreateComplaint)
        create.setOnClickListener {
            val i= Intent(this,Createcomplaint::class.java)
            startActivity(i)
        }
        var contact:ImageView=findViewById(R.id.Contact)
        contact.setOnClickListener{

            val i= Intent(this,Contact_us::class.java)
            startActivity(i)
        }

        var more1:ImageView=findViewById(R.id.more)
        more1.setOnClickListener{

            val i= Intent(this,more::class.java)
            startActivity(i)
        }


    }
}
