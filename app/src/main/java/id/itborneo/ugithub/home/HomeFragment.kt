package id.itborneo.ugithub.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import id.itborneo.ugithub.core.repository.MainRepository
import id.itborneo.ugithub.core.utils.Resource
import id.itborneo.ugithub.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers

class HomeFragment : Fragment() {

    private val TAG = "HomeFragment"
    private val mainRepository = MainRepository()
    private lateinit var adapter: HomeAdapter
    private lateinit var binding: FragmentHomeBinding

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
        initListCity()
        getUsers().observe(viewLifecycleOwner) {
            Log.d(TAG, "${it.status}, ${it.message} and ${it.data}")
            it.data?.toList()?.let { it1 -> adapter.set(it1) }

        }
    }

    private fun getUsers() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getUsers()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private fun initListCity() {
        binding.rvHome.layoutManager = LinearLayoutManager(requireContext())
        adapter = HomeAdapter {
//            actionMoveToDetail(it)
        }
        binding.rvHome.adapter = adapter


    }
}