package id.itborneo.ugithub.favorite

import android.os.Bundle
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
import id.itborneo.ugithub.core.factory.ViewModelFactory
import id.itborneo.ugithub.core.local.AppDatabase
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.DataMapperModel
import id.itborneo.ugithub.databinding.FragmentFavoriteBinding
import id.itborneo.ugithub.detail.DemoCollectionAdapter
import id.itborneo.ugithub.detail.DetailActivity

class FavoriteFragment : Fragment() {

    private lateinit var adapter: FavoriteAdapter
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var navController: NavController
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
        observerFavorite()
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

    private fun observerFavorite() {
        viewModel.favorites.observe(viewLifecycleOwner) {
            adapter.set(it)

            if (it.isEmpty()) {
                emptyListUI(true)
            } else {
                emptyListUI(false)

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

    private fun emptyListUI(isEmpty: Boolean) {
        if (isEmpty) {
            binding.incEmpty.apply {
                root.visibility = View.VISIBLE
                tvTitle.text = getString(R.string.empty_list_favorite)
            }

        }
    }



}