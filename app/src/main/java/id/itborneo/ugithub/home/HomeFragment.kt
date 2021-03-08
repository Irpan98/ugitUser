package id.itborneo.ugithub.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import id.itborneo.ugithub.R
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.utils.enums.Status
import id.itborneo.ugithub.databinding.FragmentHomeBinding
import id.itborneo.ugithub.detail.DetailActivity.Companion.EXTRA_USER

open class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private lateinit var adapter: HomeAdapter
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        initNav(view)
        initSearch()
        observerData()
    }

    private fun initSearch() {
        binding.apply {
            sbCities.setOnClickListener {
                sbCities.onActionViewExpanded()
            }
            sbCities.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null && newText.length >= 3) {
                        Log.d(TAG, "onQueryTextChange "+newText)
                        viewModel.searchUser(newText)
                    }
                    return true
                }
            })
        }
    }

    private fun observerData() {
        viewModel.users.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.toList()?.let { it1 -> adapter.set(it1) }
                }
                Status.LOADING -> {

                }

                Status.ERROR -> {
                    Log.d(TAG, "${it.status}, ${it.message} and ${it.data}")

                }
            }


        }
    }


    private fun initList() {
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
        adapter = HomeAdapter {
            actionToDetail(it)
        }
        binding.rvHome.layoutManager = GridLayoutManager(context, 2)
        binding.rvHome.adapter = adapter


    }

    private fun actionToDetail(userModel: UserModel) {

        val bundle = bundleOf(EXTRA_USER to userModel)
        navController.navigate(
            R.id.action_homeFragment_to_detailActivity,
            bundle
        )

    }

    open lateinit var navController: NavController


    private fun initNav(view: View) {
        navController = Navigation.findNavController(view)

    }
}