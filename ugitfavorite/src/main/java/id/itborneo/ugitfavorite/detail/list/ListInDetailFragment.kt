package id.itborneo.ugitfavorite.detail.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import id.itborneo.ugitfavorite.core.model.UserModel
import id.itborneo.ugitfavorite.databinding.FragmentDetailListBinding
import id.itborneo.ugitfavorite.core.factory.ViewModelFactory
import id.itborneo.ugitfavorite.core.repository.MainRepository
import id.itborneo.ugitfavorite.core.enums.Status


class ListInDetailFragment : Fragment() {

    companion object {
        const val EXTRA_URL = "extra_url"
        const val EXTRA_TYPE = "extra_type"
        const val TAG = "ChildDetailFragment"

        fun newInstance(param1: UserModel?, param2: String?): ListInDetailFragment {

            val fragment = ListInDetailFragment()
            val args = Bundle()
            args.putParcelable(EXTRA_URL, param1)
            args.putString(EXTRA_TYPE, param2)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var binding: FragmentDetailListBinding
    private lateinit var adapter: ListInDetailAdapter
    private var type: String? = null
    private var userModel: UserModel? = null
    private val viewModel: ListInDetailViewModel by viewModels {
        ViewModelFactory(MainRepository(), userModel?.login, type)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerview()
        retrieveData()
        observerData()
    }

    private fun retrieveData() {
        if (arguments != null) {
            userModel = arguments?.getParcelable(EXTRA_URL)
            type = arguments?.getString(EXTRA_TYPE)
        }
    }

    private fun initRecyclerview() {
        adapter = ListInDetailAdapter {
            actionToGithubPage(it)
        }

        binding.rvList.apply {
            layoutManager = LinearLayoutManager(context)

        }
        binding.rvList.adapter = this.adapter
    }

    private fun actionToGithubPage(user: UserModel) {
        Toast.makeText(requireContext(), "Opening Github ${user.login}", Toast.LENGTH_SHORT).show()
        val browserIntent = Intent(
            Intent.ACTION_VIEW, Uri.parse(user.htmlUrl)
        )
        startActivity(browserIntent)
    }


    private fun observerData() {
        viewModel.users.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data != null) {
                        it.data.toList().let { it1 -> adapter.set(it1) }
                    }
                    showLoading(false)
                }
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.ERROR -> {
                    Log.e(TAG, "${it.status}, ${it.message} and ${it.data}")
                    showError()
                    showLoading(false)
                }
            }
        }
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

        binding.rvList.apply {
            visibility = if (showIt) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
    }
}

