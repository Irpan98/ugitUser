package id.itborneo.ugithub.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.databinding.ItemUserBinding


class HomeAdapter(private val listener: (UserModel) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var cities = listOf<UserModel>()

    fun set(cities: List<UserModel>) {
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

    override fun getItemCount() = cities.size

    inner class ViewHolder(private val itemBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(user: UserModel) {
            itemBinding.apply {

                tvName.text = user.login
                tvSubtitle.text = user.htmlUrl?.removeRange(0, 8)
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