package com.nadikarim.androidsubmission3.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadikarim.androidsubmission3.data.remote.response.User
import com.nadikarim.androidsubmission3.databinding.ListUserBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private val listUser = ArrayList<User>()

    class ViewHolder(val binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(listUser[position].avatarUrl)
                .into(imageView)

            tvUsername.text = listUser[position].login

            cvUser.setOnClickListener {
                onItemClickCallback.onItemClicked(listUser[holder.adapterPosition])
            }
        }
    }

    override fun getItemCount(): Int = listUser.size

    @SuppressLint("NotifyDataSetChanged")
    fun setUser(users: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}