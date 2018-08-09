package com.github.monchenkoid.quotationapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class CurrencyAdapter(private val data: List<CurrencyModel>) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {
    private val items: MutableList<CurrencyModel> = data as MutableList<CurrencyModel>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                    .run {
                        CurrencyViewHolder(this)
                    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        with(holder) {
            bind(items[position])
        }
    }

    inner class CurrencyViewHolder(
            itemView: View,
            private val rate: TextView = itemView.findViewById(R.id.rate),
            private val name: TextView = itemView.findViewById(R.id.name),
            private val scale: TextView = itemView.findViewById(R.id.scale),
            private val charCode: TextView = itemView.findViewById(R.id.charCode),
            upButton: View = itemView.findViewById(R.id.up),
            downButton: View = itemView.findViewById(R.id.down)
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            upButton.setOnClickListener(moveUp())
            downButton.setOnClickListener(moveDown())
        }

        private fun moveUp(): (View) -> Unit = {
            layoutPosition.takeIf { it > 0 }?.also { currentPosition ->
                items.removeAt(currentPosition).also {
                    items.add(currentPosition - 1, it)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown(): (View) -> Unit = { it ->
            layoutPosition.takeIf { it < items.size - 1 }?.also { currentPosition ->
                items.removeAt(currentPosition).also {
                    items.add(currentPosition + 1, it)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        fun bind(data: CurrencyModel) {
            rate.text = data.rate.toString()
            name.text = data.name
            charCode.text = data.charCode
            scale.text = data.scale.toString()
        }
    }
}
