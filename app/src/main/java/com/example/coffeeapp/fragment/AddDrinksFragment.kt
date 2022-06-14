package com.example.coffeeapp.fragment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.coffeeapp.MainActivity
import com.example.coffeeapp.R
import com.example.coffeeapp.model.Drink
import com.example.coffeeapp.ViewModel.DrinkViewModel
import com.example.coffeeapp.adapter.DrinkRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_add_drinks.*
import kotlinx.android.synthetic.main.fragment_add_drinks.view.*
import kotlinx.coroutines.launch


class AddDrinksFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = AddDrinksFragment()
    }

    private lateinit var mDrinkViewModel: DrinkViewModel
    private lateinit var drinkRecyclerViewAdapter: DrinkRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      //  super.onCreate(savedInstanceState)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_drinks, container, false)

        mDrinkViewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)

        view.btn_add_drink.setOnClickListener{


//            myViewModel.readPerson.observe(this, {
//                adapter.setData(it)
//            })

            insertDataToDatabase()

        }

        return view;
    }

    private fun insertDataToDatabase() {
        val title = et_add_title.text.toString()
        val description = et_add_description.text.toString()
        val price = et_add_price.text.toString()

        if(inputCheck(title, description, et_add_price.text)){
            //Create drink object
            val drink = Drink(0, title, description, price.toString())
            //Add Data to Database
            mDrinkViewModel.addDrink(drink)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()

//                lifecycleScope.launch {
//                    val drink = Drink(0, title, description, price.toString(), getBitmap())
//                    mDrinkViewModel.addDrink(drink)
//                    Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
//                }
//                mDrinkViewModel.readAllData.observe(viewLifecycleOwner, {
//                    drinkRecyclerViewAdapter.setData(it)
//                })

            findNavController().navigate(R.id.action_add_drinks_to_barMenuFragment)
//            val frag=BarMenuFragment.newInstance()
//            (activity as MainActivity).replaceFragment(frag)
        }
        else{
            Toast.makeText(requireContext(), "Please fill out all fields!", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(title: String, description: String, price: Editable): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(description) && TextUtils.isEmpty(price))
    }

//    private suspend fun getBitmap(): Bitmap {
//        val loading = ImageLoader(requireContext())
//        val request = ImageRequest.Builder(requireContext())
//            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
//            .build()
//
//        val result = (loading.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }
}