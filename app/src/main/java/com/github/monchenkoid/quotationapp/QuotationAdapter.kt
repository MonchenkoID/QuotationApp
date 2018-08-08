package com.github.monchenkoid.quotationapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class QuotationAdapter(private val string: String) : RecyclerView.Adapter<QuotationAdapter.QuotationViewHolder>() {
    private val items: MutableList<Pair<String, Boolean>> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotationViewHolder =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item, parent, false)
                    .run {
                        QuotationViewHolder(this)
                    }

    override fun getItemCount(): Int = items.size

    fun appendItem(newString: String) =
            items.add(uniqueString(newString) to false).also {
                notifyItemInserted(itemCount - 1)
            }

    override fun onBindViewHolder(holder: QuotationViewHolder, position: Int) {
        with(holder) {
            bind(items[position])
        }
    }

    private fun uniqueString(base: String) =
            "$base ${(Math.random() * 1000).toInt()}"

    inner class QuotationViewHolder(
            itemView: View,
            private val textView1: TextView = itemView.findViewById(R.id.rate),
            private val textView2: TextView = itemView.findViewById(R.id.name),
            upButton: View = itemView.findViewById(R.id.up),
            downButton: View = itemView.findViewById(R.id.down)
    ) : RecyclerView.ViewHolder(itemView) {

        init {
            upButton.setOnClickListener(moveUp())
            downButton.setOnClickListener(moveDown())
            textView1.setOnClickListener(toggleText())
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

        private fun toggleText(): (View) -> Unit = {
           items[layoutPosition] = items[layoutPosition].let {
               it.first to !it.second
           }
            notifyItemChanged(layoutPosition)
        }

        fun bind(data: Pair<String, Boolean>) {
            textView1.text = data.first
            textView2.visibility = if (data.second) View.VISIBLE else View.GONE
        }
    }
}
