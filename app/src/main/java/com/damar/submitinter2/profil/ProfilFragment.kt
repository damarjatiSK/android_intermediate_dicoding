package com.damar.submitinter2.profil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.damar.submitinter2.R
import com.damar.submitinter2.login.LoginActivity
import android.provider.Settings

class ProfilFragment : Fragment() {
    private lateinit var profilViewModel: ProfilViewModel
    private lateinit var myNameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        myNameTextView = view.findViewById(R.id.my_name)

        profilViewModel = ViewModelProvider(requireActivity()).get(ProfilViewModel::class.java)

        profilViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let {
                myNameTextView.text = it.name
            }
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
                val sharedPref = requireContext().getSharedPreferences(
                    getString(R.string.pref_name),
                    Context.MODE_PRIVATE
                )
                val editor = sharedPref.edit()
                editor.putString(getString(R.string.user_token), "")
                editor.apply()

                val logoutIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(logoutIntent)
                requireActivity().finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}