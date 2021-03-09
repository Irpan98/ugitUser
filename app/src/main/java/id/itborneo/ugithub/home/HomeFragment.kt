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
    private lateinit var listUser: List<UserModel>

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
//        observerDataSearch()
//        observerSearched()
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
                    if (newText != null && newText.isNotEmpty()) {

                        val newList = listUser.filter {
                            it.login?.contains(newText, true)!!
                        }

                        adapter.set(newList)

                    } else {
                        adapter.set(listUser)
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
//
//    private fun observerDataSearch() {
//        viewModel.query.observe(viewLifecycleOwner) {
//            Log.d(TAG, "observerDataSearch $it")
////            viewModel.searchUser()
//        }
//    }
//
//
//    private fun observerSearched() {
//        viewModel.userSearched.observe(viewLifecycleOwner) {
//            when (it.status) {
//                Status.SUCCESS -> {
//                    Log.d(TAG, "${it.status}, ${it.message} and ${it.data}")
//
//                    it.data?.items.let { it1 ->
//                        if (it1 != null) {
//                            adapter.set(it1)
//                        }
//                    }
//                }
//                Status.LOADING -> {
//
//                }
//
//                Status.ERROR -> {
//                    Log.d(TAG, "${it.status}, ${it.message} and ${it.data}")
//
//                }
//            }
//
//
//        }
//    }

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