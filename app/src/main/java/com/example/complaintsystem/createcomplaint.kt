@file:Suppress("DEPRECATION")

package com.example.complaintsystem

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore
import android.widget.AdapterView
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Properties
import javax.mail.*
import android.graphics.BitmapFactory
import java.io.*
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class DataStorage private constructor(private val context: Context) {
    private val dataMap: MutableMap<String, String> = mutableMapOf()

    fun storeData(id: String, data: String) {
        dataMap[id] = data
    }

    fun retrieveData(id: String): String? {
        return dataMap[id]
    }

    private val storageDir: File by lazy {
        context.getDir("data_storage", Context.MODE_PRIVATE)
    }


    fun storeImage(id: String, image: Bitmap) {
        val file = File(storageDir, "$id.jpg")
        val outputStream: OutputStream
        try {
            outputStream = FileOutputStream(file)
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun retrieveImage(id: String): Bitmap? {
        val file = File(storageDir, "$id.jpg")
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: DataStorage? = null

        fun getInstance(context:Context): DataStorage {
            if (instance == null) {
                instance = DataStorage(context)
            }
            return instance!!
        }
    }
}





class Createcomplaint:AppCompatActivity() {


    lateinit var name: EditText
    lateinit var number: EditText
    lateinit var pole: EditText
    lateinit var summary: EditText
    lateinit var upload: Button
    lateinit var session: Session
    lateinit var p:ProgressBar
    private var CAMERA_REQUEST = 1
    lateinit var proerties: Properties
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val division = findViewById<Spinner>(R.id.Division)
        val adapter = ArrayAdapter.createFromResource(this, R.array.division, R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(R.layout.simple_spinner_item)
        division.adapter = adapter
        division.prompt = "DIVISION"
        division.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedDivision = parent?.getItemAtPosition(position).toString()
                val s=DataStorage.getInstance(this@Createcomplaint)
                s.storeData("c1.div",selectedDivision)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }




        val sub = findViewById<Spinner>(R.id.SUB)
        val subadp =
            ArrayAdapter.createFromResource(this, R.array.sub, R.layout.simple_spinner_item)
        subadp.setDropDownViewResource(R.layout.simple_spinner_item)
        sub.adapter = subadp
        sub.prompt = "SUB-DIVISION"
        sub.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val subdivision = parent?.getItemAtPosition(position).toString()
                val s=DataStorage.getInstance(this@Createcomplaint)
                s.storeData("c1.sub",subdivision)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


        val cat = findViewById<Spinner>(R.id.CATEGORY)
        val catadp =
            ArrayAdapter.createFromResource(this, R.array.CATEGORY, R.layout.simple_spinner_item)
        subadp.setDropDownViewResource(R.layout.simple_spinner_item)
        cat.adapter = catadp
        cat.prompt = "CATEGORY"
        cat.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val cat1 = parent?.getItemAtPosition(position).toString()
                val s=DataStorage.getInstance(this@Createcomplaint)
                s.storeData("c1.cat",cat1)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }




        name = findViewById(R.id.name)
        number = findViewById(R.id.number)
        pole = findViewById(R.id.pole)
        summary = findViewById(R.id.summary)





        upload = findViewById(R.id.btnUploadImage)
        upload.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST)


        }


        p=findViewById(R.id.progressBar3)
        p.visibility=View.INVISIBLE
        val reg: Button = findViewById(R.id.btnSend)
        reg.setOnClickListener {
            p.visibility = View.VISIBLE

            Handler().postDelayed({
                p.visibility=View.INVISIBLE
                Toast.makeText(this, "Complaint registered successfully", Toast.LENGTH_SHORT).show()

                val nameEditText = name.text.toString()
                val numberEditText = number.text.toString()
                val poleEditText = pole.text.toString()
                val summaryEditText = summary.text.toString()
                val s = DataStorage.getInstance(this)
                s.storeData("c1.name", nameEditText)
                s.storeData("c1.no", numberEditText)
                s.storeData("c1.pole", poleEditText)
                s.storeData("c1.summary", summaryEditText)

                val i=Intent(this,Dashboard::class.java)
                startActivity(i)


            },3000)



        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_REQUEST) {
            val imageBitmap: Bitmap? = data?.extras?.get("data") as? Bitmap
            if (imageBitmap != null) {
                val s = DataStorage.getInstance(this)
                s.storeImage("image1", imageBitmap)
            }
        }
    }


}



