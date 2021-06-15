package com.esteban.pokemonapp.utilities

import androidx.recyclerview.widget.DiffUtil
import com.esteban.pokemonapp.data.community.Community

class CommunityDiffCallback(
    private var newList: ArrayList<Community?>,
    private var oldList: ArrayList<Community?>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]?.name == newList[newItemPosition]?.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        var oldItem = oldList[oldItemPosition]
        var newItem = newList[newItemPosition]
        try {
            oldList[oldItemPosition]?.equals(newList[newItemPosition])!!
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return oldList[oldItemPosition]?.equals(newList[newItemPosition])!!
    }
}