package com.esteban.pokemonapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.data.community.Community
import com.esteban.pokemonapp.data.team.MyTeamEntity
import com.esteban.pokemonapp.utilities.CommunityDiffCallback
import com.esteban.pokemonapp.utilities.MyTeamDiffCallback
import com.esteban.pokemonapp.utilities.Utils
import kotlinx.android.synthetic.main.item_my_team.view.*

class MyTeamRecyclerAdapter(
    private var context: Context,
    private var myTeamList: ArrayList<MyTeamEntity?>,
    private var clickListener: OnMyTeamClickListener
) : RecyclerView.Adapter<MyTeamRecyclerAdapter.MyTeamViewHolder>() {

    class MyTeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.textview_pokemon_name
        val hp = itemView.textview_hp
        val type = itemView.textview_type
        val capturedAt = itemView.textview_captured_at
        val avatar = itemView.pokemonAvatar

        fun bindViewHolder(context: Context, item: MyTeamEntity, clickListener: OnMyTeamClickListener) {
            name.text = item.chosenName
            hp.text = "100/100"
            //TODO: replace after with service call
            type.text = "Some type"
            capturedAt.text = Utils.formatToReadableDate(item.capturedAt)
            itemView.setOnClickListener { clickListener.onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTeamViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_team, parent, false)
        return MyTeamViewHolder(view)
    }

    override fun getItemCount(): Int {
        return myTeamList.size
    }

    override fun onBindViewHolder(holder: MyTeamViewHolder, position: Int) {
        holder.bindViewHolder(context, myTeamList[position]!!, clickListener)
    }

    fun updateList(newItems: ArrayList<MyTeamEntity?>) {
        var diffResult = DiffUtil.calculateDiff(MyTeamDiffCallback(newItems, this.myTeamList))
        diffResult.dispatchUpdatesTo(this)
        this.myTeamList.clear()
        this.myTeamList.addAll(newItems)
    }

    interface OnMyTeamClickListener {
        fun onItemClick(item: MyTeamEntity)
    }
}