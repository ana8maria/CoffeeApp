package com.example.coffeeapp.fragment

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.coffeeapp.MainActivity
import com.example.coffeeapp.R
import com.example.coffeeapp.fragment.UpdateDrinksFragmentArgs
import com.example.coffeeapp.ViewModel.DrinkViewModel
import com.example.coffeeapp.model.Drink
import kotlinx.android.synthetic.main.fragment_update_drinks.*
import kotlinx.android.synthetic.main.fragment_update_drinks.view.*
import kotlinx.coroutines.launch


class UpdateDrinksFragment : Fragment() {

    private val args by navArgs<UpdateDrinksFragmentArgs>()

    private lateinit var mDrinkViewModel: DrinkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update_drinks, container, false)

        mDrinkViewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)

        view.et_update_title.setText(args.currentDrink.title)
        view.et_update_description.setText(args.currentDrink.description)
        view.et_update_price.setText(args.currentDrink.price)

        view.btn_update_drink.setOnClickListener{
            updateDrink()
        }

        // add delete menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateDrink(){
        val title = et_update_title.text.toString()
        val description = et_update_description.text.toString()
        val price = et_update_price.text.toString()

        if(inputCheck(title, description, et_update_price.text)){
            // create drink object
            val updatedDrink = Drink(args.currentDrink.id, title, description, price.toString())
            // update current drink
            mDrinkViewModel.updateDrink(updatedDrink)
            Toast.makeText(requireContext(), "Updated successfully!", Toast.LENGTH_SHORT).show()

//            lifecycleScope.launch {
//                val updatedDrink = Drink(args.currentDrink.id, title, description, price.toString(), getBitmap())
//                // update current drink
//                mDrinkViewModel.updateDrink(updatedDrink)
//                Toast.makeText(requireContext(), "Updated successfully!", Toast.LENGTH_SHORT).show()
//            }


            //navigate back
            findNavController().navigate(R.id.action_updateDrinksFragment_to_barMenuFragment)
//            val frag=BarMenuFragment.newInstance()
//            (activity as MainActivity).replaceFragment(frag)

        }else{
            Toast.makeText(requireContext(), "Please fill out all the fields!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputCheck(title: String, description: String, price: Editable): Boolean{
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(description) && TextUtils.isEmpty(price))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater){
        inflater.inflate(R.menu.delete_drink_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_drink){
            deleteDrink()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteDrink(){
        val builder  = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mDrinkViewModel.deleteDrink(args.currentDrink)
            Toast.makeText(requireContext(),
            "Successfully removed: ${args.currentDrink.title}",
            Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateDrinksFragment_to_barMenuFragment)
        }
        builder.setNegativeButton("No"){_, _ -> }
        builder.setTitle("Delete ${args.currentDrink.title}?")
        builder.setMessage("Are you sure you want to delete ${args.currentDrink.title}?")
        builder.create().show()
    }

//
//    private suspend fun getBitmap(): Bitmap {
//        val loading = ImageLoader(requireContext())
//        val request = ImageRequest.Builder(requireContext())
//            .data("https://avatars3.githubusercontent.com/u/14994036?s=400&u=2832879700f03d4b37ae1c09645352a352b9d2d0&v=4")
//            .build()
//
//        val result = (loading.execute(request) as SuccessResult).drawable
//        return (result as BitmapDrawable).bitmap
//    }

    companion object {
        @JvmStatic
        fun newInstance() = UpdateDrinksFragment()
    }
}