package com.yunis.simpletodo
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView


class TaskAdapter     //Takes mutable list, clickListener and onLongClickListener as parameters
    (private val items: MutableList<TaskList>, var longClickListener: OnLongClickListener, var clickListener: OnClickListener)
    : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    interface OnClickListener {
        fun onItemClicked(position: Int)
    }

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val items = inflater.inflate(R.layout.custom_view, parent, false)
        // Return a new holder instance
        return ViewHolder(items)
    }

    //Bind data to particular view holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Grab item from position
        val item = items[position]
        //Bind the item
        holder.textViewName.text = item.taskName.toString()
        holder.textViewDate.text = item.taskDate
        holder.textViewDescription.text = item.taskDescription.toString()



    }

    override fun getItemCount(): Int {
        return items.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView = itemView.findViewById(R.id.itemName)
        var textViewDate: TextView = itemView.findViewById(R.id.itemDate)
        var textViewDescription: TextView = itemView.findViewById(R.id.itemDescription)



        init {

            itemView.setOnClickListener { clickListener.onItemClicked(absoluteAdapterPosition)
            }

            //User can remove items when they long click on the item.
            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(absoluteAdapterPosition)
                true
            }
        }
    }
}







