package com.idagio.artists.view.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.idagio.artists.R
import com.idagio.artists.model.Person

import kotlinx.android.synthetic.main.person_view_item.view.*

class RecyclerViewAdapter :
    RecyclerView.Adapter<RecyclerViewAdapter.PersonViewHolder>() {

    private val persons: MutableList<Person> = mutableListOf()


    fun setPersons(newPersons: List<Person>) {
        if (this.persons.isEmpty()) {
            this.persons.addAll(newPersons)
            notifyItemRangeInserted(0, persons.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize() = this@RecyclerViewAdapter.persons.size

                override fun getNewListSize() = newPersons.size

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    this@RecyclerViewAdapter.persons[oldItemPosition] == newPersons[newItemPosition]


                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                    newPersons[newItemPosition] == persons[oldItemPosition]

            })
            this.persons.clear()
            this.persons.addAll(newPersons)
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_view_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) = holder.bind(persons[position])

    override fun getItemCount() = persons.size


    class PersonViewHolder(personView: View) : RecyclerView.ViewHolder(personView) {
        fun bind(person: Person) = with(itemView) {
            itemView.full_name_textView.text = person.fullName

        }
    }

}

