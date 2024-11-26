package com.upiiz.jscb_examen_2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.upiiz.jscb_examen_2.Models.Post
import com.upiiz.jscb_examen_2.R
import com.upiiz.jscb_examen_2.databinding.ItemPostBinding

class PostAdapter(private val onPostClick: (Post) -> Unit) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val posts = mutableListOf<Post>()

    fun submitList(newPosts: List<Post>) {
        posts.clear()
        posts.addAll(newPosts)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position], onPostClick)
    }

    override fun getItemCount() = posts.size

    class PostViewHolder(private val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post, onPostClick: (Post) -> Unit) {
            binding.title.text = post.title
            binding.body.text = post.body
            binding.root.setOnClickListener { onPostClick(post) }
        }
    }
}