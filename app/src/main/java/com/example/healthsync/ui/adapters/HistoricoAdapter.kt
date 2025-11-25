package com.example.healthsync.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.healthsync.data.database.entities.Measurement
import com.example.healthsync.databinding.ItemHistoricoBinding

class HistoricoAdapter(
    private val onDeleteClick: (Measurement) -> Unit
) : ListAdapter<Measurement, HistoricoAdapter.MeasurementViewHolder>(MeasurementDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementViewHolder {
        val binding = ItemHistoricoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeasurementViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MeasurementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MeasurementViewHolder(private val binding: ItemHistoricoBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isExpanded = false

        fun bind(measurement: Measurement) {
            binding.textViewType.text = measurement.type
            binding.textViewResult.text = measurement.result
            binding.textViewDetails.text = measurement.details

            binding.layoutDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE
            binding.textViewExpand.text = if (isExpanded) "Ver menos" else "Ver mais"

            binding.textViewExpand.setOnClickListener {
                isExpanded = !isExpanded
                binding.layoutDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE
                binding.textViewExpand.text = if (isExpanded) "Ver menos" else "Ver mais"
            }

            binding.buttonDelete.setOnClickListener {
                onDeleteClick(measurement)
            }
        }
    }

    class MeasurementDiffCallback : DiffUtil.ItemCallback<Measurement>() {
        override fun areItemsTheSame(oldItem: Measurement, newItem: Measurement): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Measurement, newItem: Measurement): Boolean {
            return oldItem == newItem
        }
    }
}
