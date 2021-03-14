package id.itborneo.ugithub.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
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

        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var userDetail: UserDetailModel
    private val viewModel: DetailViewModel by viewModels {
        val dao = AppDatabase.getInstance(this).favoriteDao()
        ViewModelFactory(MainRepository(dao), getIntentData)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        retrieveData()
        initViewModelData()
        initToolbar()
        initTabLayout()
        buttonListener()
        observerDetailUser()
        observerFavoriteStatus()

    }

    private fun initToolbar() {

        var isShow = false
        val collapsingToolbarLayout = binding.collapsingToolbar
        var scrollRange = -1

        setSupportActionBar(binding.toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset < 120) {
                if (::userDetail.isInitialized) {
                    collapsingToolbarLayout.title = userDetail.name
                    isShow = true
                }

            } else if (isShow) {
                collapsingToolbarLayout.title =
                    " " //careful there should a space between double quote otherwise it wont work
                isShow = false
            }
        })
    }

    private fun initViewModelData() {
//        viewModel.userModel.let {
////            viewModel.getDetailUser(it.login ?: "")
////            viewModel.checkIsFavorite(it.id ?: 0)
//        }
    }

    private var getIntentData: UserModel? = null

    private fun retrieveData() {
        getIntentData = intent.extras?.getParcelable(EXTRA_USER)
//        val getData = getIntentData
//        if (getData != null) {
//            viewModel.UserModel = getData
//        } else {
//            Log.e(TAG, "Something's Wrong with retrieveData")
//            finish()
//        }
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
        binding.incDetailInfo.apply {
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
        binding.incError.root.visibility = View.VISIBLE
    }

    private fun showLoading(isLoading: Boolean) {
        binding.incLoading.apply {
            root.visibility = if (isLoading) {
                parentShimmer.startShimmer()
                View.VISIBLE
            } else {
                parentShimmer.stopShimmer()
                View.GONE
            }
        }

        binding.incDetailInfo.root.apply {
            visibility = if (isLoading) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        binding.tabs.apply {
            visibility = if (isLoading) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        binding.viewPager.apply {
            visibility = if (isLoading) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }


    private fun initTabLayout() {
        val user = viewModel.userModel
        val sectionsPagerAdapter = DetailPagerAdapter(this, user)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}