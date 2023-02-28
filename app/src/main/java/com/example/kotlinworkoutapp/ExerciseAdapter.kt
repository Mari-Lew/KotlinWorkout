package com.example.kotlinworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()

        //
        when
        {
            model.getIsSelected() ->
            {
                holder.tvItem.background =
                    ContextCompat.getDrawable(
                        holder.itemView.context,
                    R.drawable.item_circular_thin_color_accent_border)

                holder.tvItem.setTextColor(Color.parseColor("#212121"))

            }
            model.getIsComplete() ->
            {
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_bg)

                holder.tvItem.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else ->
            {
                //
                holder.tvItem.background =
                    ContextCompat.getDrawable(holder.itemView.context,
                        R.drawable.item_circular_color_grey_bg)

                holder.tvItem.setTextColor(Color.parseColor("#212121"))

            }
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

}