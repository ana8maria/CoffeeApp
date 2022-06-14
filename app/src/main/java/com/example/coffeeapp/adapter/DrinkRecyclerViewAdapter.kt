package com.example.coffeeapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.ListFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.coffeeapp.MainActivity
import com.example.coffeeapp.R
import com.example.coffeeapp.fragment.AddDrinksFragment
import com.example.coffeeapp.fragment.BarMenuFragmentDirections
import com.example.coffeeapp.fragment.UpdateDrinksFragment
import com.example.coffeeapp.model.Drink
import kotlinx.android.synthetic.main.coffee_layout.view.*
import java.util.Collections.emptyList

class DrinkRecyclerViewAdapter: RecyclerView.Adapter<DrinkRecyclerViewAdapter.MyViewHolder>() {

    private var drinkList = emptyList<Drink>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.coffee_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = drinkList[position]
        holder.itemView.tv_title.text = currentItem.title.toString()
        holder.itemView.tv_description.text = currentItem.description.toString()
        holder.itemView.tv_price.text = currentItem.price.toString()
        //holder.itemView.iv_image.load(currentItem.photo)

        // id ul de la coffee layout.xml
        holder.itemView.drinkLayout.setOnClickListener{
//            val frag= UpdateDrinksFragment.newInstance()
//            (activity as MainActivity).replaceFragment(frag)
            val action = BarMenuFragmentDirections.actionBarMenuFragmentToUpdateDrinksFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(drink: List<Drink>){
        this.drinkList = drink
        notifyDataSetChanged()
    }
}