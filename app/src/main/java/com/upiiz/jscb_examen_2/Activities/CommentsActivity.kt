package com.upiiz.jscb_examen_2.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.upiiz.jscb_examen_2.Adapters.CommentAdapter

import com.upiiz.jscb_examen_2.Network.ApiService
import com.upiiz.jscb_examen_2.databinding.ActivityCommentsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CommentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCommentsBinding
    private lateinit var adapter: CommentAdapter
    private var postId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        postId = intent.getIntExtra("POST_ID", 0)

        adapter = CommentAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        fetchComments()
    }

    private fun fetchComments() {
        CoroutineScope(Dispatchers.Main).launch {
            val apiService = ApiService.create()
            val comments = apiService.getCommentsByPostId(postId)
            adapter.submitList(comments)
        }
    }
}
