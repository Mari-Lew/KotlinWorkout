package com.example.kotlinworkoutapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinworkoutapp.databinding.ItemExerStatusBinding

class ExerciseAdapter(val items: ArrayList<ExerciseModel>):
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>()
{


        class ViewHolder(binding: ItemExerStatusBinding) : RecyclerView.ViewHolder(binding.root)
        {
            val tvItem = binding.tvItem // individual item
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(ItemExerStatusBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()
    }
}