package com.labactivity.gakumate

import DatabaseHelper
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.labactivity.gakumate.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var categories: ArrayList<TheCategory>
    private lateinit var adapter: CategoryAdapter
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var auth: FirebaseAuth
    private lateinit var authListener: FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val searchView = binding.search
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText.orEmpty())
                return true
            }
        })


        val add = binding.btnAdd
        add.setOnClickListener() {
            val intent = Intent(this, AddCategory_::class.java)
            startActivity(intent)
        }

        databaseHelper = DatabaseHelper(this)
        categories = databaseHelper.getCategories()

        val recyclerView = binding.recyclerview
        adapter = CategoryAdapter(categories)
        recyclerView.adapter = adapter  // Set the adapter

        // Add this line to attach a layout manager (e.g., LinearLayoutManager)
        recyclerView.layoutManager = LinearLayoutManager(this)

        auth = FirebaseAuth.getInstance() // Initialize FirebaseAuth

        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.img1.setOnClickListener{
            navigateToUserProfile()
        }
    }

    private fun updateAdapterData() {
        val newData = databaseHelper.getCategories()
        adapter.updateData(newData)
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        updateAdapterData()
        finish()
    }
    private fun navigateToUserProfile() {
        val intent = Intent(this, UserProfileActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the auth state listener to avoid memory leaks
        auth.removeAuthStateListener(authListener)
    }

}
