package id.itborneo.ugithub.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import id.itborneo.ugithub.core.model.UserDetailModel
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.utils.enums.Status
import id.itborneo.ugithub.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private val TAG = "DetailActivity"

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intentUser = intent.extras?.getParcelable<UserModel>(EXTRA_USER)

        viewModel.getDetailUser(intentUser?.login ?: "")

        observerData()
    }

    private fun observerData() {
        viewModel.detailUser.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    updateUI(it.data)
                    Log.d(TAG, "${it.status}, ${it.message} and ${it.data}")
                }
                Status.LOADING -> {

                }

                Status.ERROR -> {
                    Log.d(TAG, "${it.status}, ${it.message} and ${it.data}")

                }
            }


        }
    }

    private fun updateUI(userDetail: UserDetailModel?) {
        binding.incInfo.apply {
            tvName.text = userDetail?.name ?: "N/A"
            tvUsername.text = userDetail?.login
            tvAddress.text = userDetail?.location ?: "N/A"
            tvWorkplace.text = userDetail?.company ?: "N/A"
            tvFollower.text = userDetail?.followers.toString()
            tvFollowing.text = userDetail?.following.toString()
            tvRepository.text = userDetail?.publicRepos.toString()
        }

        Picasso.get()
            .load(userDetail?.avatarUrl)
            .fit()
            .centerCrop()
            .into(binding.ivAvatar)
    }


}