package id.itborneo.ugithub.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import id.itborneo.ugithub.R
import id.itborneo.ugithub.core.enums.Status
import id.itborneo.ugithub.core.factory.ViewModelFactory
import id.itborneo.ugithub.core.local.AppDatabase
import id.itborneo.ugithub.core.local.FavoriteModel
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.DataMapperModel
import id.itborneo.ugithub.databinding.FragmentFavoriteBinding
import id.itborneo.ugithub.detail.DetailActivity

class FavoriteFragment : Fragment() {

    private val TAG = "FavoriteFragment"
    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var navController: NavController
    private lateinit var listUser: List<FavoriteModel>
    private val viewModel: FavoriteViewModel by viewModels {
        val dao = AppDatabase.getInstance(requireContext()).favoriteDao()
        ViewModelFactory(MainRepository(dao))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initNav(view)
        observerData()
    }

    private fun initList() {
        adapter = FavoriteAdapter {
            actionToDetail(DataMapperModel.singleFavoriteToUser(it))
        }

        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 2)

        }
        binding.rvFavorites.adapter = this.adapter

    }

    private fun observerData() {
        viewModel.favorites.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
                        listUser = it.data
                        it.data.toList().let { it1 -> adapter.set(it1) }
                    }

                }
                Status.LOADING -> {

                }

                Status.ERROR -> {
                    Log.d(TAG, "${it.status}, ${it.message} and ${it.data}")

                }
            }


        }
    }


    private fun actionToDetail(user: UserModel) {

        val bundle = bundleOf(DetailActivity.EXTRA_USER to user)
        navController.navigate(
            R.id.action_favoriteFragment_to_detailActivity,
            bundle
        )

    }

    private fun initNav(view: View) {
        navController = Navigation.findNavController(view)

    }


}