package com.example.greengpt.presentation.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.greengpt.databinding.FragmentSavedBinding
import com.example.greengpt.presentation.saved.adapter.SavedAdapter
import com.example.greengpt.util.gone
import com.example.greengpt.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SavedViewModel>()
    private val savedAdapter by lazy { SavedAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedBinding.inflate(inflater, container, false)
        viewModel.getFavorites()
        binding.savedRv.adapter = savedAdapter
        binding.savedRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val itemHelperCallback = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val favorite = savedAdapter.differ.currentList[position]
                viewModel.deleteFavorite(favorite)
                Toast.makeText(requireContext(),"Deleted",Toast.LENGTH_LONG).show()
            }
        }

        ItemTouchHelper(itemHelperCallback).apply {
            attachToRecyclerView(binding.savedRv)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeLiveData()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun observeLiveData() {
        viewModel.savedResult.observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()){
                binding.savedRv.gone()
            } else {
                binding.savedRv.visible()
                savedAdapter.differ.submitList(it)
            }
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}