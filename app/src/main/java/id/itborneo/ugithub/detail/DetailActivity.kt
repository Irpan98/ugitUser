package id.itborneo.ugithub.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import id.itborneo.ugithub.R
import id.itborneo.ugithub.core.enums.Status
import id.itborneo.ugithub.core.factory.ViewModelFactory
import id.itborneo.ugithub.core.local.AppDatabase
import id.itborneo.ugithub.core.model.UserDetailModel
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.ToastTop
import id.itborneo.ugithub.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"
        private const val TAG = "DetailActivity"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userDetail: UserDetailModel
    private var intentData: UserModel? = null
    private val viewModel: DetailViewModel by viewModels {
        val dao = AppDatabase.getInstance(this).favoriteDao()
        ViewModelFactory(MainRepository(dao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        retrieveData()
        initViewModel()
        buttonListener()
        observerDetailUser()
        observerFavoriteStatus()

    }

    private fun initViewModel() {
        intentData.let {
            viewModel.getDetailUser(it?.login ?: "")
            viewModel.checkIsFavorite(it?.id ?: 0)
        }

    }

    private fun retrieveData() {
        intentData = intent.extras?.getParcelable(EXTRA_USER)
    }

    private fun initBinding() {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    private fun buttonListener() {
        binding.btnFavorite.setOnClickListener {
            viewModel.apply {
                if (isFavorite.value == true) {
                    viewModel.removeFavorite()
                    showToastFavoriteStatus(false)
                } else {
                    if (!::userDetail.isInitialized) return@setOnClickListener
                    viewModel.addToFavorite(userDetail)
                    showToastFavoriteStatus(true)
                }
            }
        }

        binding.btnToGithub.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW, Uri.parse(viewModel.detailUser.value?.data?.htmlUrl)
            )
            startActivity(browserIntent)
        }

        binding.ibBack.setOnClickListener {
            finish()
        }
    }

    private fun observerDetailUser() {
        viewModel.detailUser.observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    showLoading(false)

                    if (it.data != null) {
                        updateUI(it.data)
                        userDetail = it.data
                        Log.d(TAG, "${it.status}, ${it.message} and ${it.data}")
                    } else {
                        showError()
                    }

                }
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.ERROR -> {
                    showLoading(false)

                    //something wrong
                    Log.e(TAG, "${it.status}, ${it.message} and ${it.data}")
                    showError()
                }
            }
        }
    }

    private fun observerFavoriteStatus() {
        viewModel.isFavorite.observe(this) {
            updateFavoriteStatusUI(it)
        }
    }

    private fun updateFavoriteStatusUI(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_true)
        } else {
            binding.btnFavorite.setImageResource(R.drawable.ic_favorite_false)
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

        binding.apply {
            btnToGithub.visibility = View.VISIBLE
            btnFavorite.visibility = View.VISIBLE
        }

        Picasso.get()
            .load(userDetail?.avatarUrl)
            .fit()
            .centerCrop()
            .into(binding.ivAvatar)
    }

    private fun showToastFavoriteStatus(isFavorite: Boolean) {
        if (isFavorite) {
            ToastTop.show(this, getString(R.string.added_to_favorite))
        } else {
            ToastTop.show(this, getString(R.string.removed_from_favorite))
        }
    }

    private fun showError() {
        binding.incLoading.root.visibility = View.VISIBLE
    }

    private fun showLoading(showIt: Boolean) {
        binding.incLoading.apply {
            root.visibility = if (showIt) {
                parentShimmer.startShimmer()
                View.VISIBLE
            } else {
                parentShimmer.stopShimmer()
                View.GONE
            }
        }

        binding.incInfo.root.apply {
            visibility = if (showIt) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}