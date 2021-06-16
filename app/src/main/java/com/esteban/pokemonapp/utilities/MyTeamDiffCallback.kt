package com.esteban.pokemonapp.utilities

import androidx.recyclerview.widget.DiffUtil
import com.esteban.pokemonapp.data.team.MyTeamEntity

class MyTeamDiffCallback (
    private var newList: ArrayList<MyTeamEntity?>,
    private var oldList: ArrayList<MyTeamEntity?>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]?.id == newList[newItemPosition]?.id
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