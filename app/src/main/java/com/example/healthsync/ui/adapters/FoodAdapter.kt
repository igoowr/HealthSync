package com.example.healthsync.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthsync.data.remote.models.Food
import com.example.healthsync.databinding.ItemFoodBinding

class FoodAdapter : ListAdapter<Food, FoodAdapter.FoodViewHolder>(FoodDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FoodViewHolder(private val binding: ItemFoodBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isExpanded = false

        fun bind(food: Food) {
            binding.textViewFoodName.text = food.label
            binding.textViewFoodCalories.text = "Calorias: %.0f kcal".format(food.nutrients.ENERC_KCAL)

            binding.layoutDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE
            binding.textViewExpand.text = if (isExpanded) "Ver menos" else "Ver mais"

            binding.textViewFoodProteins.text = "Proteínas: %.1f g".format(food.nutrients.PROCNT)
            binding.textViewFoodCarbs.text = "Carboidratos: %.1f g".format(food.nutrients.CHOCDF)
            binding.textViewFoodFats.text = "Gorduras: %.1f g".format(food.nutrients.FAT)

            binding.textViewExpand.setOnClickListener {
                isExpanded = !isExpanded
                binding.layoutDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE
                binding.textViewExpand.text = if (isExpanded) "Ver menos" else "Ver mais"
            }
        }
    }

    class FoodDiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.foodId == newItem.foodId
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }
    }
}