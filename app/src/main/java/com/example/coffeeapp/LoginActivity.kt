package com.example.coffeeapp

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager


@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {

    lateinit var ivLogo: ImageView
    lateinit var callbackManager: CallbackManager

    var id = ""
    var firstName = ""
    var middleName = ""
    var lastName = ""
    var name = ""
    var picture = ""
    var email = ""
    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_login)

        ivLogo = findViewById(R.id.iv_logo)
        callbackManager = CallbackManager.Factory.create()

        if (isLoggedIn()) {
            Log.d("LoggedIn? :", "YES")
            startActivity(Intent(this@LoginActivity, PageActivity::class.java))
        } else {
            Log.d("LoggedIn? :", "NO")

        }

        ivLogo.setOnClickListener{
            val tY = ObjectAnimator.ofFloat(ivLogo, View.ROTATION_Y, ivLogo.rotationY, ivLogo.rotationY+360f)

            tY.duration = 500
            tY.interpolator = LinearInterpolator()
            tY.start()



        }

        tv_register.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        btn_login.setOnClickListener {
            when {
                TextUtils.isEmpty(username_input.text.toString().trim {
                    it <= ' '
                }) -> {
                    Toast.makeText(this@LoginActivity, "Please enter email.", Toast.LENGTH_SHORT)
                        .show()
                }

                TextUtils.isEmpty(pass.text.toString().trim {
                    it <= ' '
                }) -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val email: String = username_input.text.toString().trim { it <= ' ' }
                    val password: String = pass.text.toString().trim { it <= ' ' }

                    //Create an instance and create a register user with email and password
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            //If the registration is successfully done
                            if (task.isSuccessful) {
                                //Firebase registered user


                                Toast.makeText(
                                    this@LoginActivity,
                                    "You were logged in successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()

//                                Here the new user registered is automatically signed-id so we just sign-out
//                                and send him to Main Screed with user id and email that user have used for registration

                                val intent =
                                    Intent(this@LoginActivity, PageActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra(
                                    "user_id",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()

                            } else {
                                // If the registering is not successful then show error message.
                                Toast.makeText(
                                    this@LoginActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }

        callbackManager = CallbackManager.Factory.create()


        val loginButton = findViewById<LoginButton>(R.id.login_button)
        loginButton.setReadPermissions(listOf("public_profile", "email"))

        loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                Log.d("TAG", "Success Login")
                getUserProfile(loginResult?.accessToken, loginResult?.accessToken?.userId)
                startActivity(Intent(this@LoginActivity, PageActivity::class.java))
            }

            override fun onCancel() {
                Toast.makeText(this@LoginActivity, "Login Cancelled", Toast.LENGTH_LONG).show()
            }

            override fun onError(exception: FacebookException) {
                Toast.makeText(this@LoginActivity, exception.message, Toast.LENGTH_LONG).show()
            }
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    @SuppressLint("LongLogTag")
    fun getUserProfile(token: AccessToken?, userId: String?) {

        val parameters = Bundle()
        parameters.putString(
            "fields",
            "id, first_name, middle_name, last_name, name, picture, email"
        )
        GraphRequest(token,
            "/$userId/",
            parameters,
            HttpMethod.GET,
            GraphRequest.Callback { response ->
                val jsonObject = response.jsonObject


                if (BuildConfig.DEBUG) {
                    FacebookSdk.setIsDebugEnabled(true)
                    FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
                }

                // Facebook Id
                if (jsonObject.has("id")) {
                    val facebookId = jsonObject.getString("id")
                    Log.i("Facebook Id: ", facebookId.toString())
                } else {
                    Log.i("Facebook Id: ", "Not exists")
                }


                // Facebook First Name
                if (jsonObject.has("first_name")) {
                    val facebookFirstName = jsonObject.getString("first_name")
                    Log.i("Facebook First Name: ", facebookFirstName)
                } else {
                    Log.i("Facebook First Name: ", "Not exists")
                }


                // Facebook Middle Name
                if (jsonObject.has("middle_name")) {
                    val facebookMiddleName = jsonObject.getString("middle_name")
                    Log.i("Facebook Middle Name: ", facebookMiddleName)
                } else {
                    Log.i("Facebook Middle Name: ", "Not exists")
                }


                // Facebook Last Name
                if (jsonObject.has("last_name")) {
                    val facebookLastName = jsonObject.getString("last_name")
                    Log.i("Facebook Last Name: ", facebookLastName)
                } else {
                    Log.i("Facebook Last Name: ", "Not exists")
                }


                // Facebook Name
                if (jsonObject.has("name")) {
                    val facebookName = jsonObject.getString("name")
                    Log.i("Facebook Name: ", facebookName)
                } else {
                    Log.i("Facebook Name: ", "Not exists")
                }


                // Facebook Profile Pic URL
                if (jsonObject.has("picture")) {
                    val facebookPictureObject = jsonObject.getJSONObject("picture")
                    if (facebookPictureObject.has("data")) {
                        val facebookDataObject = facebookPictureObject.getJSONObject("data")
                        if (facebookDataObject.has("url")) {
                            val facebookProfilePicURL = facebookDataObject.getString("url")
                            Log.i("FB Profile Pic URL:", facebookProfilePicURL)
                        }
                    }
                } else {
                    Log.i("FB Pic URL: ", "Not exists")
                }

                // Facebook Email
                if (jsonObject.has("email")) {
                    val facebookEmail = jsonObject.getString("email")
                    Log.i("Facebook Email: ", facebookEmail)
                } else {
                    Log.i("Facebook Email: ", "Not exists")
                }
            }).executeAsync()
    }

    fun isLoggedIn(): Boolean {
        val accessToken = AccessToken.getCurrentAccessToken()
        val isLoggedIn = accessToken != null && !accessToken.isExpired
        return isLoggedIn
    }

    fun logOutUser() {
        LoginManager.getInstance().logOut()
    }




}






