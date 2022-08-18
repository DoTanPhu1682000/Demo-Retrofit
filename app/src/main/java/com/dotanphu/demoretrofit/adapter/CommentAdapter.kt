package com.dotanphu.demoretrofit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dotanphu.demoretrofit.databinding.ItemListCommentBinding
import com.dotanphu.demoretrofit.model.CommentItem

class CommentAdapter(private var commentList : List<CommentItem>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
//    private var commentList = arrayListOf<CommentItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): CommentAdapter.CommentViewHolder {
        return CommentViewHolder(
            ItemListCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentAdapter.CommentViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount() = commentList.size

    class CommentViewHolder(private val binding: ItemListCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(commentItem: CommentItem) {
            binding.tvName.text = commentItem.name
            binding.tvBody.text = commentItem.body
            binding.tvEmail.text = commentItem.email
        }
    }
}