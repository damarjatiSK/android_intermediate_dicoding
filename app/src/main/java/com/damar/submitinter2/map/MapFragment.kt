package com.damar.submitinter2.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.damar.submitinter2.R
import com.damar.submitinter2.databinding.FragmentMapBinding
import com.damar.submitinter2.home.HomeFragment
import com.damar.submitinter2.login.LoginActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapsViewModel: MapViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        mapsViewModel = ViewModelProvider(this)[MapViewModel::class.java]
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun addManyMarker() {
        mapsViewModel.getAllStory(HomeFragment.USER_TOKEN!!)

        mapsViewModel.storyInfo.observe(viewLifecycleOwner) { storyList ->
            mMap.clear()
            storyList?.let { stories ->
                stories.forEach { story ->
                    val latitude: Double = story?.lat as Double
                    val longitude: Double = story?.lon as Double
                    val latLng = LatLng(latitude, longitude)
                    mMap.addMarker(MarkerOptions().position(latLng).title(story.name))
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        //Indonesia
        val defaultLocation = LatLng(-0.7893, 113.9213)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 3f))

        addManyMarker()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_language_setting -> {
                val languageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(languageIntent)
                return true
            }
            R.id.btn_logout -> {
                val sharedPref = requireContext().getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(getString(R.string.user_token), "")
                editor.apply()

                val logoutIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(logoutIntent)
                requireActivity().finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}