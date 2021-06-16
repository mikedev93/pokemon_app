package com.esteban.pokemonapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.data.community.Community
import com.esteban.pokemonapp.utilities.CommunityDiffCallback
import com.esteban.pokemonapp.utilities.Utils
import kotlinx.android.synthetic.main.item_community.view.*

class CommunityRecyclerAdapter(
    private var context: Context,
    private var communityList: ArrayList<Community?>,
    private val clickListener: CommunityOnClickListener
) : RecyclerView.Adapter<CommunityRecyclerAdapter.CommunityViewHolder>() {

    class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trainer = itemView.textview_trainer_name
        val pokemonName = itemView.textview_pokemon_name
        val pokemonImage = itemView.imageView

        fun bindViewHolder(context: Context, item: Community, clickListener: CommunityOnClickListener) {
            trainer.text = item.name
            pokemonName.text = "${item.pokemon.name} ${Utils.formatTimeAgo(item.pokemon.capturedAt)}"
            itemView.setOnClickListener { clickListener.onItemClick(item) }
            pokemonImage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_community, parent, false)
        return CommunityViewHolder(view)
    }

    override fun getItemCount(): Int {
        return communityList.size
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        holder.bindViewHolder(context, communityList[position]!!, clickListener)
    }

    fun updateList(newItems: ArrayList<Community?>) {
        var diffResult = DiffUtil.calculateDiff(CommunityDiffCallback(newItems, this.communityList))
        diffResult.dispatchUpdatesTo(this)
        this.communityList.clear()
        this.communityList.addAll(newItems)
    }

    interface CommunityOnClickListener {
        fun onItemClick(item: Community)
    }
}