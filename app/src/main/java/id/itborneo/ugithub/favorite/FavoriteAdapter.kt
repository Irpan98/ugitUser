package id.itborneo.ugithub.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.databinding.ItemUserBinding

class FavoriteAdapter(private val listener: (FavoriteModel) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var cities = listOf<FavoriteModel>()
    fun set(cities: List<FavoriteModel>) {
        this.cities = cities

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int = cities.size

    inner class ViewHolder(private val itemBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: FavoriteModel) {
            itemBinding.apply {
                tvName.text = user.login
                tvSubtitle.text = user.htmlUrl.removeRange(0, 8)

                Picasso.get()
                    .load(user.avatarUrl)
                    .into(ivImage)
                clItem.setOnClickListener {
                    listener(user)
                }

            }
        }
    }


}