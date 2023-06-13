package com.example.ssutask.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ssutask.databinding.RecyclerViewBookListItemBinding
import com.example.ssutask.domain.entity.BookEntity

class BooksAdapter(
    private val projectEntities: List<BookEntity>
) : RecyclerView.Adapter<BooksAdapter.ViewHolder>() {

    class ViewHolder(val binding: RecyclerViewBookListItemBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewBookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val project = projectEntities[position]

            bookTitle.text = project.title
        }
    }

    override fun getItemId(position: Int): Long {
        return projectEntities[position].id
    }

    override fun getItemCount(): Int {
        return projectEntities.count()
    }
}