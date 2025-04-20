package com.rbs.pokemonapps.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.rbs.pokemonapps.data.ResultState
import com.rbs.pokemonapps.databinding.FragmentProfileBinding
import com.rbs.pokemonapps.domain.model.UserDomain
import com.rbs.pokemonapps.presentation.activity.LoginActivity
import com.rbs.pokemonapps.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUI()
        subscribeObserver()
    }

    private fun subscribeUI() {
        logout()
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener { viewModel.logout() }
    }

    private fun subscribeObserver() {
        lifecycleScope.launch {
            viewModel.viewState.collectLatest {
                when (it) {
                    ResultState.Loading -> Unit
                    is ResultState.Success -> setData(it.data)
                    is ResultState.Error -> showError(it.message)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.logoutState.collectLatest {
                if (it) {
                    LoginActivity.launch(requireContext())
                    requireActivity().finish()
                }
            }
        }
    }

    private fun setData(data: UserDomain) {
        with(binding) {
            data.apply {
                tvName.text = name
                tvUsername.text = username
            }
        }
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}