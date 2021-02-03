package com.moneybox.interview.ui.login

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.moneybox.interview.databinding.LoginFragmentBinding
import com.moneybox.interview.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val BEARER_TOKEN_KEY = "BEARER_TOKEN_KEY"

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding

    @Inject
    lateinit var sharedPreferences : SharedPreferences

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    private fun setupObservers() {
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Resource.Status.SUCCESS -> {
                        binding.animation.visibility = View.GONE
                        binding.animation.cancelAnimation()
                        resource.data?.let { loginResponse ->
                            sharedPreferences.edit().putString(
                                    BEARER_TOKEN_KEY, "Bearer ${loginResponse.body()?.session?.bearerToken}"
                            ).apply()
                            val direction = LoginFragmentDirections.actionLoginFragmentToAccountFragment(
                                    binding.etName.text.toString()
                            )
                            requireView().findNavController().navigate(direction)
                        }
                    }
                    Resource.Status.ERROR   -> {
                        binding.animation.cancelAnimation()
                        binding.animation.visibility = View.GONE
                        Toast.makeText(requireView().context, it.message, Toast.LENGTH_LONG).show()
                    }
                    Resource.Status.LOADING -> {
                        binding.animation.visibility = View.VISIBLE
                        binding.animation.playAnimation()

                    }
                    else                    -> {}
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }
}
