package com.example.coffeeapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import com.example.coffeeapp.adapter.DrinkRecyclerViewAdapter
import com.example.coffeeapp.ViewModel.DrinkViewModel
import kotlinx.android.synthetic.main.fragment_bar_menu.view.*

//import com.example.coffeeapp.adapter.RecyclerViewAdapter



/**
 * A simple [Fragment] subclass.
 * Use the [BarMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BarMenuFragment : Fragment() {
    companion object {
        fun newInstance(): BarMenuFragment = BarMenuFragment()
    }

    private lateinit var mDrinkViewModel: DrinkViewModel

    private val addDrinksFragment = AddDrinksFragment()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bar_menu, container, false)

        //Recycler View
        val adapter = DrinkRecyclerViewAdapter()
        val recyclerView = view.rv_recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //DrinkViewModel
        mDrinkViewModel = ViewModelProvider(this).get(DrinkViewModel::class.java)
        mDrinkViewModel.readAllData.observe(viewLifecycleOwner, Observer { drink ->
            adapter.setData(drink)
        })

        view.floatingActionButton2.setOnClickListener{
            findNavController().navigate(R.id.action_barMenuFragment_to_add_drinks)
        //    Navigation.findNavController(Activity(), R.id.action_barMenuFragment_to_add_drinks)
//            val frag=AddDrinksFragment.newInstance()
//            (activity as MainActivity).replaceFragment(frag)
        }



        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_drink_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_drink){
            deleteAllUsers()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers(){
        val builder  = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_ ->
            mDrinkViewModel.deleteAllDrinks()
            Toast.makeText(requireContext(),
                "Successfully removed: everything", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No"){_, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

}