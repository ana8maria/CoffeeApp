package com.example.coffeeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.fragment.*
import com.example.coffeeapp.web_services.*
import org.koin.android.ext.android.get
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val homeFragment = HomeFragment()
    private val addDrinksFragment= AddDrinksFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> {
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                }

                R.id.ic_barMenu -> {

                    val intent = Intent(this@MainActivity, Nav_drinks::class.java)
                    startActivity(intent)

                }

                R.id.ic_contact -> {

                    val intent = Intent(this@MainActivity, ActivityWebServices::class.java)
                    startActivity(intent)
                }

                R.id.ic_map -> {

                    val intent = Intent(this@MainActivity, MapsActivity::class.java)
                    startActivity(intent)
                }


                R.id.floatingActionButton2 -> {
                    val myFrag = supportFragmentManager.findFragmentById(R.id.fragmentNavHostMenuBar);
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

    fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, fragment).addToBackStack("")
            transaction.commit()

        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentNavHostMenuBar)
        return navController.navigateUp() || super.onSupportNavigateUp()
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