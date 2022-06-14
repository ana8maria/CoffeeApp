package com.example.coffeeapp

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeapp.adapter.CoffeeAdapter
import com.example.coffeeapp.model.Coffee
import com.facebook.login.LoginManager
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_pageviewer.*
import kotlinx.android.synthetic.main.item_food.*
import java.io.File
import java.util.*
import java.util.jar.Manifest



class PageActivity : AppCompatActivity() {
    lateinit var ivLogo: ImageView
    lateinit var animLogoMove: Animation
    lateinit var animTransition: Animation
    lateinit var relativeMain: RelativeLayout
    lateinit var viewPager: HorizontalInfiniteCycleViewPager
    lateinit var btnMenu : Button
    lateinit var btnLogout : Button
    private  val REQUEST_CODE = 42
    private  val FILE_NAME= "photo.jpg"
    private lateinit var photoFile : File
    var coffeeList: MutableList<Coffee> = ArrayList()

    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE  = 1001
    var image_uri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pageviewer)

        ivLogo = findViewById(R.id.iv_logo)
        viewPager = findViewById(R.id.view_pager)
        relativeMain = findViewById(R.id.relative_main)
        animLogoMove = AnimationUtils.loadAnimation(this, R.anim.logo_move)
        animTransition = AnimationUtils.loadAnimation(this, R.anim.transition)
        btnMenu = findViewById(R.id.btn_menu)
        btnLogout = findViewById(R.id.btn_logout)

        initData()
        val adapter = CoffeeAdapter(this, coffeeList)

        btnLogout.setOnClickListener {
            LoginManager.getInstance().logOut()
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@PageActivity, LoginActivity::class.java))
        }

        btnMenu.setOnClickListener {
            startActivity(Intent(this@PageActivity, HomeActivity::class.java))
        }


        viewPager.adapter = adapter
        ivLogo.setOnClickListener(View.OnClickListener {
            video.visibility= View.INVISIBLE
            ivLogo.visibility= View.VISIBLE
            ivLogo.animation=animLogoMove
            viewPager.visibility= View.VISIBLE
            viewPager.startAnimation(animTransition)
            btnMenu.visibility=View.INVISIBLE
            btnLogout.visibility=View.INVISIBLE
            btn_picture.visibility=View.INVISIBLE
            iv_picture.visibility=View.INVISIBLE

        })

        val mediaController = MediaController(this)
        mediaController.setAnchorView(video)

        val offlineUri = Uri.parse("android.resource://$packageName/${R.raw.raw}")
        video.setMediaController(mediaController)
        video.setVideoURI(offlineUri)
        video.requestFocus()
        video.start()

        btn_picture.setOnClickListener{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(android.Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){

                    val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)

                }
                else{

                    openCamera()
                }
            }
            else{

                openCamera()
            }



        }

    }



    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    openCamera()
                }
                else{
                    Toast.makeText(this, "Persmission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    private fun getPhotoFile(fileName: String): File {

        val storageDirectory=getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            iv_picture.setImageURI(image_uri)
        }


    }

    private fun initData() {
        coffeeList.add(
            Coffee(
                "Tiramisu",
                "Cappuccino",
                getString(R.string.tiramisu),
                R.drawable.cup_capucino,
                R.drawable.back_cappu,
                R.drawable.coffee,
                Color.parseColor("#2d181c")
            )
        )
        coffeeList.add(
            Coffee(
                "Greentea",
                "Latte",
                getString(R.string.matcha),
                R.drawable.cup_greentea,
                R.drawable.back_green,
                R.drawable.tea,
                Color.parseColor("#55dd66")
            )
        )
        coffeeList.add(
            Coffee(
                "Mocaccino",
                "Choco",
                getString(R.string.moca),
                R.drawable.cup_mocha,
                R.drawable.back_mocha,
                R.drawable.choco,
                Color.parseColor("#b33585")
            )
        )
    }
}