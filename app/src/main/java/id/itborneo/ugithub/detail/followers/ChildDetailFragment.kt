package id.itborneo.ugithub.detail.followers

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.itborneo.ugithub.core.enums.Status
import id.itborneo.ugithub.core.factory.ViewModelFactory
import id.itborneo.ugithub.core.local.AppDatabase
import id.itborneo.ugithub.core.model.UserModel
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.databinding.FragmentChildDetailBinding


class ChildDetailFragment private constructor() : Fragment() {

    companion object {
        const val EXTRA_URL = "extra_url"
        const val EXTRA_TYPE = "extra_type"
        const val TAG = "ChildDetailFragment"

        fun newInstance(param1: UserModel?, param2: String?): ChildDetailFragment {

            val fragment = ChildDetailFragment()
            val args = Bundle()
            args.putParcelable(EXTRA_URL, param1)
            args.putString(EXTRA_TYPE, param2)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentChildDetailBinding
    private lateinit var adapter: FollowersAdapter
    private var type: String? = null
    private var userModel: UserModel? = null
    private val viewModel: FollowersViewModel by viewModels {
        val dao = AppDatabase.getInstance(requireContext()).favoriteDao()
        ViewModelFactory(MainRepository(dao))
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChildDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerview()
        retrieveData()
        initViewModelData()
        observerData()
    }

    private fun initViewModelData() {
        viewModel.user = userModel?.login ?: ""
        viewModel.type = type ?: ""
        Log.d(TAG, "initViewModelData ${viewModel.type}")
    }


    private fun retrieveData() {
        if (arguments != null) {
            userModel = arguments?.getParcelable(EXTRA_URL)
            type = arguments?.getString(EXTRA_TYPE);
        }
    }

    private fun initRecyclerview() {
        adapter = FollowersAdapter {
//            actionToDetail(DataMapperModel.singleFavoriteToUser(it))
        }

        binding.rvList.apply {
            layoutManager = LinearLayoutManager(context)

        }
        binding.rvList.adapter = this.adapter
    }



    private fun observerData() {
        viewModel.users.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
//                        listUser = it.data
                        it.data.toList().let { it1 -> adapter.set(it1) }
                        Log.d(TAG, "observerData ${it.data}")

                    }
//                    showLoading(false)
                }
                Status.LOADING -> {
//                    showLoading(true)
//                    binding.incLoading.root.visibility = View.VISIBLE
                }
                Status.ERROR -> {
//                    Log.e(TAG, "${it.status}, ${it.message} and ${it.data}")
//                    showError()
//                    showLoading(false)
                }
            }
        }
    }


}