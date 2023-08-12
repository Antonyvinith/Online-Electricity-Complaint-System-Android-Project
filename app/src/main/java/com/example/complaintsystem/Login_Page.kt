package com.example.complaintsystem

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.drawerlayout.widget.DrawerLayout

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {


    private lateinit var auth:FirebaseAuth
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var register: TextView
    private lateinit var databaseHelper: DatabaseHelper1
    private lateinit var google:ImageView
    private lateinit var gso:GoogleSignInOptions
    private lateinit var gsc:GoogleSignInClient
    lateinit var drawerLayout: DrawerLayout
    lateinit var logout:TextView

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)


        auth=Firebase.auth
        etUsername = findViewById(R.id.inputEmail)
        etPassword = findViewById(R.id.inputPassword)
        btnLogin = findViewById(R.id.btnLogin)
        register = findViewById(R.id.gotoRegister)
        databaseHelper = DatabaseHelper1(this)
        google=findViewById(R.id.google)

        google.setOnClickListener{
            goToSign()
        }
        gso=GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc= GoogleSignIn.getClient(this,gso)

        val account: GoogleSignInAccount?=GoogleSignIn.getLastSignedInAccount(this)


        if(account!=null){
            goToHOme()
        }
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val userData = databaseHelper.getUser(username)


             if(etUsername.text.toString()=="Antony"&&etPassword.text.toString()=="antony123"){
                val i=Intent(this,Admin::class.java)
                startActivity(i)

            }
            else{
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }



        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }


    private fun goToSign() {
        val signInIntent=gsc.signInIntent
        @Suppress("DEPRECATION")
        startActivityForResult(signInIntent,1000)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1000){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{

                val account:GoogleSignInAccount?=task.result
                task.getResult(ApiException::class.java)
                if(account!=null) updateui(account)
                goToHOme()

            }
            catch(e:java.lang.Exception){
                Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                val intent=Intent(this,Dashboard::class.java)
                startActivity(intent)
            }
        }
    }

    private fun updateui(account: GoogleSignInAccount) {

        val credential=GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                val intent=Intent(this,Dashboard::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,"Can't Login",Toast.LENGTH_SHORT).show()
            }

        }

    }



    private fun goToHOme() {
        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()



    }


}


