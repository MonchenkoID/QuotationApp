package com.github.monchenkoid.quotationapp.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.monchenkoid.quotationapp.R
import com.github.monchenkoid.quotationapp.data.CurrencyModel
import com.github.monchenkoid.quotationapp.utils.StringUtil

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
            private val rate: TextView = itemView.findViewById(R.id.first_line),
            private val name: TextView = itemView.findViewById(R.id.second_line),
            upButton: View = itemView.findViewById(R.id.up),
            downButton: View = itemView.findViewById(R.id.down),
            private val context: Context = itemView.context
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
            rate.text = StringUtil.format(context.getString(R.string.first_line), data.charCode, data.rate.toString());
            name.text = StringUtil.format(context.getString(R.string.second_line), data.name, data.scale.toString());
        }
    }
}
