package com.upiiz.jscb_examen_2.Activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.upiiz.jscb_examen_2.Adapters.UserAdapter
import com.upiiz.jscb_examen_2.Database.AppDatabase

import com.upiiz.jscb_examen_2.Models.User
import com.upiiz.jscb_examen_2.Network.ApiService

import com.upiiz.jscb_examen_2.databinding.ActivityUserBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding
    private lateinit var userAdapter: UserAdapter
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(
            onLongClick = { user ->
                saveUserToLocalDatabase(user)
            },
            onClick = { user ->
                openUserPosts(user)
            }
        )
        binding.recyclerViewUsers.adapter = userAdapter
        database = AppDatabase.getDatabase(this)

        loadUsers()
    }



    private suspend fun fetchUsersFromApi(): List<User> {
        return withContext(Dispatchers.IO) {
            val apiService = ApiService.create()
            val response = apiService.getUsers()
            response
        }
    }

    private suspend fun fetchUsersFromRoom(): List<User> {
        return withContext(Dispatchers.IO) {
            database.userDao().getAllUsers()
        }
    }

    private suspend fun saveUsersToLocalDatabase(users: List<User>) {
        withContext(Dispatchers.IO) {
            database.userDao().insertUsers(users)
        }
    }



    private fun openUserPosts(user: User) {
        val intent = Intent(this, PostActivity::class.java).apply {
            putExtra("USER_ID", user.id)
        }
        startActivity(intent)
    }


    private fun saveUserToLocalDatabase(user: User) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                database.userDao().insertUser(user)
            }
            Toast.makeText(this@UserActivity, "${user.name} guardado en la base de datos", Toast.LENGTH_SHORT).show()
        }
    }
    private fun loadUsers() {
        lifecycleScope.launch {
            val users = if (isOnline()) {
                try {
                    val apiUsers = fetchUsersFromApi()
                    saveUsersToLocalDatabase(apiUsers)
                    apiUsers
                } catch (e: Exception) {
                    fetchUsersFromRoom()
                }
            } else {
                fetchUsersFromRoom()
            }
            userAdapter.submitList(users)
        }
    }

    private fun isOnline(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


}
