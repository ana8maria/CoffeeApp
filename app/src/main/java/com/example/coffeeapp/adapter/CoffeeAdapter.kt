package com.example.coffeeapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager.widget.PagerAdapter
import com.example.coffeeapp.HomeActivity
import com.example.coffeeapp.LoginActivity
import com.example.coffeeapp.Nav_drinks
import com.example.coffeeapp.R
import com.example.coffeeapp.model.Coffee
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth


internal class CoffeeAdapter(private val context: Context, private val coffeeList: List<Coffee>) :
    PagerAdapter() {
    override fun getCount(): Int {
        return coffeeList.size
    }

//    lateinit var btnMenu : Button
//    lateinit var btnLogout : Button

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeViewAt(position)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getItemPosition(`object`: Any): Int {
        return super.getItemPosition(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food, container, false)
        val imageView = view.findViewById<ImageView>(R.id.iv_image)
        val ivDecoration = view.findViewById<ImageView>(R.id.iv_decoration)
        val textViewTitle = view.findViewById<TextView>(R.id.tv_title)
        val textViewSubTitle = view.findViewById<TextView>(R.id.tv_subtitle)
        val textViewDesc = view.findViewById<TextView>(R.id.tv_desc)
        val relativeBack = view.findViewById<RelativeLayout>(R.id.relative_back)

//        btnMenu = view.findViewById(R.id.btn_menu)
//        btnLogout = view.findViewById(R.id.btn_logout)

        imageView.setImageResource(coffeeList[position].image)
        ivDecoration.setImageResource(coffeeList[position].decoration)
        relativeBack.setBackgroundResource(coffeeList[position].background)
        textViewTitle.text = coffeeList[position].title
        textViewSubTitle.text = coffeeList[position].subTitle
        textViewDesc.text = coffeeList[position].description
        view.setOnClickListener {
         //   Toast.makeText(context, "" + coffeeList, Toast.LENGTH_SHORT).show()
        }

        textViewTitle.setOnClickListener {
            context.startActivity(Intent(context, Nav_drinks::class.java))
        }
//        btnLogout.setOnClickListener {
//            LoginManager.getInstance().logOut()
//            FirebaseAuth.getInstance().signOut()
//            //startActivity(Intent(context, LoginActivity::class.java))
//
//            val intent = Intent(context, LoginActivity::class.java)
//            view.context.startActivity(intent)
//        }
//
//        btnMenu.setOnClickListener {
//
//            val intent = Intent(context, HomeActivity::class.java)
//            view.context.startActivity(intent)
//        }


        container.addView(view)
        return view
    }
}