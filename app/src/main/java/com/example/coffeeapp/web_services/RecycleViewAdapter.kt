package com.example.coffeeapp.web_services

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeeapp.R
import kotlinx.android.synthetic.main.recycler_row_list.view.*

class RecycleViewAdapter(val clickListener: OnItemClickListener): RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>() {
    var userList = mutableListOf<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row_list, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: RecycleViewAdapter.MyViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            clickListener.onItemEditCLick(userList[position])
        }
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textViewName = view.textViewName
        val textViewEmail = view.textViewEmail
        val textViewStats = view.textViewStats

        fun bind(data : User) {
            textViewName.text = data.name
            textViewEmail.text = data.email
            textViewStats.text = data.status
        }
    }

    interface OnItemClickListener {
        fun onItemEditCLick(user : User)
    }
}