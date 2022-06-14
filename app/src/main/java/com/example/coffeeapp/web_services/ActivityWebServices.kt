package com.example.coffeeapp.web_services

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeapp.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_web_services.*

class ActivityWebServices : AppCompatActivity() , RecycleViewAdapter.OnItemClickListener{
    lateinit var recyclerViewAdapter: RecycleViewAdapter
    lateinit var viewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_services)

        // Web Services
        initRecyclerView()
        initViewModel()
        searchUser()
    }

    // Web Services
    private fun searchUser() {
        search_button.setOnClickListener {
            if(!TextUtils.isEmpty(searchEditText.text.toString())) {
                viewModel.searchUser(searchEditText.text.toString())
            } else {
                viewModel.getUsersList()
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ActivityWebServices)
            val decoration = DividerItemDecoration(this@ActivityWebServices, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            recyclerViewAdapter = RecycleViewAdapter(this@ActivityWebServices)
            adapter = recyclerViewAdapter

        }
    }

    fun initViewModel() {
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        viewModel.getUserListObserverable().observe(this, Observer<UserList> {
            if(it == null) {
                Toast.makeText(this@ActivityWebServices, "no result found...", Toast.LENGTH_LONG).show()
            } else {
                recyclerViewAdapter.userList = it.data.toMutableList()
                recyclerViewAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getUsersList()
    }

    override fun onItemEditCLick(user: User) {
        val intent = Intent(this@ActivityWebServices, CreateNewUserActivity::class.java)
        intent.putExtra("user_id", user.id)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 1000) {
            viewModel.getUsersList()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}