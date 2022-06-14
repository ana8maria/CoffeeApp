package com.example.coffeeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coffeeapp.fragment.AddDrinksFragment
import com.example.coffeeapp.fragment.HomeFragment
import com.example.coffeeapp.web_services.ActivityWebServices
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get

class HomeActivity: AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val addDrinksFragment = AddDrinksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> {
                    val intent = Intent(this@HomeActivity, HomeActivity::class.java)
                    startActivity(intent)
                }

                R.id.ic_barMenu -> {

                    val intent = Intent(this@HomeActivity, Nav_drinks::class.java)
                    startActivity(intent)

                }

                R.id.ic_contact -> {

                    val intent = Intent(this@HomeActivity, ActivityWebServices::class.java)
                    startActivity(intent)
                }

                R.id.ic_map -> {

                    val intent = Intent(this@HomeActivity, MapsActivity::class.java)
                    startActivity(intent)
                }


                R.id.floatingActionButton2 -> {
                    val myFrag =
                        supportFragmentManager.findFragmentById(R.id.fragmentNavHostMenuBar);
                    if (myFrag != null) {
                        supportFragmentManager.beginTransaction().hide(myFrag).commit()
                    };
                    replaceFragment(addDrinksFragment)
                }
            }
            true
        }

        // Dependency Injection
        val menu = get<OurMenu>()
        menu.getOrder()

    }

    fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, fragment).addToBackStack("")
            transaction.commit()

        }


    }

    // share content
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.share){
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
                val randomString = (1..6)
                    .map { allowedChars.random() }
                    .joinToString("")
                this.putExtra(Intent.EXTRA_TEXT, "Your discount code for your friends is " + randomString )
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }else{
            return super.onOptionsItemSelected(item)
        }
        return true
    }
}