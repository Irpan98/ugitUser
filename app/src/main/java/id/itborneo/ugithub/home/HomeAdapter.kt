package id.itborneo.ugithub.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.databinding.ItemUserBinding


class HomeAdapter(private val listener: (UserModel) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private val TAG = "PlacesAdapter"

    private var cities = listOf<UserModel>()

    fun set(cities: List<UserModel>) {
        this.cities = cities
        Log.d(TAG, cities.toString())
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
        fun bind(user: UserModel) {
            itemBinding.apply {
                tvName.text = user.login
                tvSubTitle.text = user.htmlUrl

                Picasso.get()
                    .load(user.avatarUrl)
                    .fit()
                    .centerCrop()
                    .into(ivImage)
                clItem.setOnClickListener {
                    listener(user)
                }

            }
        }
    }


}