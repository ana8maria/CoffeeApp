package com.example.coffeeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.coffeeapp.fragment.AddDrinksFragment
import com.example.coffeeapp.web_services.ActivityWebServices
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get

class Nav_drinks : AppCompatActivity() {

    private val addDrinksFragment= AddDrinksFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nav_drinks)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> {
                    val intent = Intent(this@Nav_drinks, HomeActivity::class.java)
                    startActivity(intent)
                }

                R.id.ic_barMenu -> {

                    val intent = Intent(this@Nav_drinks, Nav_drinks::class.java)
                    startActivity(intent)

                }

                R.id.ic_contact -> {

                    val intent = Intent(this@Nav_drinks, ActivityWebServices::class.java)
                    startActivity(intent)
                }

                R.id.ic_map -> {

                    val intent = Intent(this@Nav_drinks, MapsActivity::class.java)
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
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentNavHostMenuBar)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, fragment).addToBackStack("")
            transaction.commit()

        }
    }
}