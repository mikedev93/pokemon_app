package com.esteban.pokemonapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.data.pokemon.Move
import com.esteban.pokemonapp.data.pokemon.NestedMove
import com.esteban.pokemonapp.utilities.Utils
import kotlinx.android.synthetic.main.item_moves.view.*

class MovesRecyclerAdapter(private var movesList: ArrayList<Move>): RecyclerView.Adapter<MovesRecyclerAdapter.MovesViewHolder>() {

    class MovesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val moveName = itemView.textview_move_title
        val moveLevel = itemView.textview_move_level

        fun bindViewHolder(move: NestedMove) {
            moveName.text = "${move.name?.toLowerCase()?.capitalize()}:"
            moveLevel.text = move.level
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_moves, parent, false)
        return MovesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movesList.size
    }

    override fun onBindViewHolder(holder: MovesViewHolder, position: Int) {
        holder.bindViewHolder(movesList[position].nestedMove)
    }
}