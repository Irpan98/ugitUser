package id.itborneo.ugithub.detail.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.databinding.ItemInDetailBinding

class ListInDetailAdapter(private val listener: (UserModel) -> Unit) :
    RecyclerView.Adapter<ListInDetailAdapter.ViewHolder>() {

    private var cities = listOf<UserModel>()
    fun set(cities: List<UserModel>) {
        this.cities = cities

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemInDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int = cities.size

    inner class ViewHolder(private val itemBinding: ItemInDetailBinding) :
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