package com.upiiz.jscb_examen_2.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.upiiz.jscb_examen_2.Models.User
import com.upiiz.jscb_examen_2.databinding.ItemUserBinding

class UserAdapter(
    private val onLongClick: (User) -> Unit,
    private val onClick: (User) -> Unit
) : ListAdapter<User, UserAdapter.UserViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position), onLongClick, onClick)
    }

    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User, onLongClick: (User) -> Unit, onClick: (User) -> Unit) {
            binding.userName.text = user.name
            binding.userEmail.text = user.email


            binding.root.setOnLongClickListener {
                onLongClick(user)
                true
            }

            binding.root.setOnClickListener {
                onClick(user)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}

