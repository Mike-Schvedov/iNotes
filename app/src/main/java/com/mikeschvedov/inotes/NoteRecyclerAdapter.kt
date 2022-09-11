package com.mikeschvedov.inotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


class NoteRecyclerAdapter (private val listener: OnItemClickListener) : RecyclerView.Adapter<NoteRecyclerAdapter.NotesViewHolder>() {

    var list: MutableList<Note> = mutableListOf()

    fun interface OnItemClickListener{
        fun onItemClicked(
            note: Note
        )
    }

    // add new data
    fun setNewData(newData: List<Note>) {
        // passing the new and old list into the callback
        val diffCallback = DiffUtilCallback(list, newData)
        // we get the result
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        // we clear the old list
        list.clear()
        // and replace it with the new list
        list.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var content: TextView =
            itemView.findViewById(R.id.content_text_view)
        var date: TextView =
            itemView.findViewById(R.id.date_text_view)
        var itemLayout: ConstraintLayout =
            itemView.findViewById(R.id.item_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view_holder,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        val item = list[position]

        // --- Setting the image --- //
        holder.content.text = item.content
        // --- Setting the title --- //
        holder.date.text = item.date

        // -- Sending the clicked item as callback -- //
        holder.itemLayout.setOnClickListener {
            listener.onItemClicked(item)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class DiffUtilCallback(
    private val oldList: List<Note>,
    private val newList: List<Note>
) :
    DiffUtil.Callback() {

    // old size
    override fun getOldListSize(): Int = oldList.size

    // new list size
    override fun getNewListSize(): Int = newList.size

    // if items are same
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.noteID == newItem.noteID
    }

    // check if contents are same
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem == newItem
    }
}
