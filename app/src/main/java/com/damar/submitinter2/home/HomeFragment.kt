package com.damar.submitinter2.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.damar.submitinter2.R
import com.damar.submitinter2.adapter.ListStoryAdapter
import com.damar.submitinter2.adapter.LoadingStateAdapter
import com.damar.submitinter2.databinding.FragmentHomeBinding
import com.damar.submitinter2.detail.DetailStoryActivity
import com.damar.submitinter2.detail.ViewModelFactory
import com.damar.submitinter2.login.LoginActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory()
    }

    private val adapter = ListStoryAdapter { story ->
        val detailIntent = Intent(requireContext(), DetailStoryActivity::class.java).apply {
            putExtra(DetailStoryActivity.NAMA, story.name)
            putExtra(DetailStoryActivity.DESKRIPSI, story.description)
            putExtra(DetailStoryActivity.FOTO, story.photoUrl)
            putExtra(DetailStoryActivity.CREATED_AT, story.createdAt)
        }
        startActivity(detailIntent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.story_list)
        setHasOptionsMenu(true)

        showRecyclerView()
        getListStory()
    }

    private fun showRecyclerView() {
        binding.rvStory.apply {
            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvStory.layoutManager = layoutManager

            val itemDecoration =
                DividerItemDecoration(requireContext(), layoutManager.orientation)
            binding.rvStory.addItemDecoration(itemDecoration)

            setHasFixedSize(true)
            adapter = this@HomeFragment.adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    this@HomeFragment.adapter.retry()
                }
            )
        }
    }

    private fun getListStory() {
        homeViewModel.story.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_language_setting -> {
                val languageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(languageIntent)
                return true
            }
            R.id.btn_logout -> {
                val sharedPref =
                    requireContext().getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(getString(R.string.user_token), "")
                editor.commit()

                val logoutIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(logoutIntent)
                requireActivity().finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var USER_TOKEN: String? = "USER_TOKEN"
    }
}