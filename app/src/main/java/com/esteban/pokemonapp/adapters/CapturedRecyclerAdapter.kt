package com.esteban.pokemonapp.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.data.captured.CapturedEntity
import com.esteban.pokemonapp.data.pokemon.PokemonEntity
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
            itemView.setOnClickListener { clickListener.onItemClick(item, avatar) }
            if (item.pokemonDetails != null && item.pokemonDetails?.sprites?.frontDefault != null) {
                Glide.with(context)
                    .load(item.pokemonDetails?.sprites?.frontDefault)
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
                    .into(avatar)
            }
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

    fun updatePokemonDetail(pokemon: PokemonEntity) {
        capturedList.forEachIndexed { index, item ->
            if (item?.id == pokemon.id) {
                item.pokemonDetails = pokemon
                this.notifyItemChanged(index)
            }
        }
    }

    interface CapturedOnClickListener {
        fun onItemClick(item: CapturedEntity, imageView: ImageView)
    }
}