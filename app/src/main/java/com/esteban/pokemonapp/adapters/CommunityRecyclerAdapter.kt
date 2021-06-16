package com.esteban.pokemonapp.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.data.community.Community
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
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
            if (item.pokemon.pokemonDetails != null && item.pokemon.pokemonDetails?.sprites?.frontDefault != null) {
                Glide.with(context)
                    .load(item.pokemon.pokemonDetails?.sprites?.frontDefault)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
//                            if (progressBar != null) {
//                                progressBar.visibility = View.GONE
//                            }
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
//                            if (progressBar != null) {
//                                progressBar.visibility = View.GONE
//                            }
                            return false
                        }
                    })
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(pokemonImage)
            }
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

    fun updatePokemonDetail(pokemon: PokemonEntity) {
        communityList.forEachIndexed {index, community ->
            if (community?.pokemon?.id == pokemon.id) {
                community?.pokemon?.pokemonDetails = pokemon
                this.notifyItemChanged(index)
            }
        }
    }

    interface CommunityOnClickListener {
        fun onItemClick(item: Community)
    }
}