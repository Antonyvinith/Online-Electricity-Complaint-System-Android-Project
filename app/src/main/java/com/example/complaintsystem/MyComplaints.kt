package com.example.complaintsystem

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRECATION")
class MyComplaints:AppCompatActivity()
{
        lateinit var progressBar: ProgressBar
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mycomplaints)
        var comp1:TextView=findViewById(R.id.comp1)
        var b:Button=findViewById(R.id.buttonClickToList)
        val comp1pic:ImageView=findViewById(R.id.comppic)
        progressBar=findViewById(R.id.progressBar2)
        progressBar.visibility=View.INVISIBLE
        b.setOnClickListener{
            progressBar.visibility=View.VISIBLE
            val delay:Long=3000
            val s=DataStorage.getInstance(this)

            Handler().postDelayed({

              progressBar.visibility = View.INVISIBLE
                comp1.text="Division:"+s.retrieveData("c1.div")+"\n\nSub-Division:"+s.retrieveData("c1.sub")+"\n\nCategory:"+s.retrieveData("c1.cat")+"\n\n"+"Name:"+s.retrieveData("c1.name")+"\n\n"+"Number:"+s.retrieveData("c1.no")+"\n\nPole No:"+s.retrieveData("c1.pole")+"\n\nSummary:"+s.retrieveData("c1.summary")

                var image1=s.retrieveImage("image1")
                comp1pic.setImageBitmap(image1)
            }, delay)


        }
        val del:ImageView=findViewById(R.id.buttonDelete)
        del.setOnClickListener {
            comp1.text=""
            comp1pic.setImageResource(0)
        }













    }


}
