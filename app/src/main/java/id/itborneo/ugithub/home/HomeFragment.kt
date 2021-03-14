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
import id.itborneo.ugithub.core.enums.Status
import id.itborneo.ugithub.core.factory.ViewModelFactory
import id.itborneo.ugithub.core.local.AppDatabase
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.databinding.FragmentHomeBinding
import id.itborneo.ugithub.detail.DetailActivity.Companion.EXTRA_USER

open class HomeFragment : Fragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    private lateinit var adapter: HomeAdapter
    private lateinit var binding: FragmentHomeBinding
    private lateinit var listUser: List<UserModel>
    private lateinit var navController: NavController

    private val viewModel: HomeViewModel by viewModels {
        val dao = AppDatabase.getInstance(requireContext()).favoriteDao()
        ViewModelFactory(MainRepository(dao))
    }

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
        observerSearch()
    }

    private fun initSearch() {
        binding.sbUsers.apply {
            setOnClickListener {
                onActionViewExpanded()
            }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!::listUser.isInitialized) return false
                    if (newText != null && newText.isNotEmpty()) {

                        viewModel.query.postValue(newText)
                        viewModel.searchUsers(newText)
//                        val newList = listUser.filter { user ->
//                            user.login?.contains(newText, true) ?: false
//                        }
//                        adapter.set(newList)
//
//                        if (newList.isNullOrEmpty()) {
//                            binding.iclEmpty.apply {
//                                root.visibility = View.VISIBLE
//                                tvTitle.text = requireContext().getString(R.string.user_not_found)
//                            }
//                        } else {
//                            binding.iclEmpty.root.visibility = View.GONE
//                        }

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
                    showLoading(false)
                }
                Status.LOADING -> {
                    showLoading(true)
                    binding.incLoading.root.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Log.e(TAG, "${it.status}, ${it.message} and ${it.data}")
                    showError()
                    showLoading(false)
                }
            }
        }
    }

    private fun observerSearch() {
        viewModel.seachedUsers.observe(viewLifecycleOwner) {
            Log.d(TAG, "observerSearch ${it.status}, ${it.message} and ${it.data}")

            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
                        listUser = it.data.items
                        it.data.items.toList().let { it1 -> adapter.set(it1) }
                    }
                    showLoading(false)
                }
                Status.LOADING -> {
                    showLoading(true)
                    binding.incLoading.root.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Log.e(TAG, "observerSearch ${it.status}, ${it.message} and ${it.data}")
                        showError()
                    showLoading(false)
                }
            }
        }
//        viewModel.query.observe(viewLifecycleOwner) {
//            Log.d(TAG, "observerSearch $it")
//
//            viewModel.seachedUsers = viewModel.searchUsers()
//        }
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


    private fun initNav(view: View) {
        navController = Navigation.findNavController(view)
    }

    private fun showError() {
        binding.incError.root.visibility = View.VISIBLE
    }

    private fun showLoading(showIt: Boolean) {
        binding.incLoading.root.apply {
            visibility = if (showIt) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

}