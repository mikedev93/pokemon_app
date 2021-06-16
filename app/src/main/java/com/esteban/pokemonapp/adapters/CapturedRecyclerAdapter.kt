package com.esteban.pokemonapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.data.captured.CapturedEntity
import com.esteban.pokemonapp.utilities.CapturedDiffCallback
import com.esteban.pokemonapp.utilities.CommunityDiffCallback
import kotlinx.android.synthetic.main.item_captured.view.*

class CapturedRecyclerAdapter(
    private var context: Context,
    private var capturedList: ArrayList<CapturedEntity?>,
    private var clickListener: CapturedOnClickListener
): RecyclerView.Adapter<CapturedRecyclerAdapter.CapturedViewHolder>() {

    class CapturedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val avatar = itemView.capturedAvatar

        fun bindViewHolder(context: Context, item: CapturedEntity, clickListener: CapturedOnClickListener) {
            itemView.setOnClickListener { clickListener.onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CapturedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_captured, parent, false)
        return CapturedViewHolder(view)
    }

    override fun getItemCount(): Int {
        return capturedList.size
    }

    override fun onBindViewHolder(holder: CapturedViewHolder, position: Int) {
        holder.bindViewHolder(context, capturedList[position]!!, this.clickListener)
    }

    fun updateList(newItems: ArrayList<CapturedEntity?>) {
        var diffResult = DiffUtil.calculateDiff(CapturedDiffCallback(newItems, this.capturedList))
        diffResult.dispatchUpdatesTo(this)
        this.capturedList.clear()
        this.capturedList.addAll(newItems)
    }

    interface CapturedOnClickListener {
        fun onItemClick(item: CapturedEntity)
    }
}