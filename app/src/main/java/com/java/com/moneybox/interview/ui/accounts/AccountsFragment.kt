package com.moneybox.interview.ui.accounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.moneybox.interview.R
import com.moneybox.interview.adapter.AccountsAdapter
import com.moneybox.interview.databinding.AccountsFragmentBinding
import com.moneybox.interview.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment enabling a user view posts that are retrieved from remote/local data source.
 */
@AndroidEntryPoint
class AccountsFragment : Fragment() {

    private val viewModel: AccountsViewModel by viewModels()
    private lateinit var adapter: AccountsAdapter
    private lateinit var binding: AccountsFragmentBinding
    private val args: AccountsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountsFragmentBinding.inflate(inflater, container, false)
        setupViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupViews() {
        adapter = AccountsAdapter()
        binding.adapter = adapter
        if (args.name.isEmpty()) {
            binding.userAccountsHelloTitle.visibility = View.GONE
        } else {
            binding.userAccountsHelloTitle.visibility = View.VISIBLE
            binding.userAccountsHelloTitle.text = getString(R.string.user_accounts_hello_title,args.name)

        }
    }

    private fun setupRecyclerView() {
        binding.accountsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.accountsRv.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.accountUserFriendly.observe(viewLifecycleOwner, Observer {
            it.let(adapter::submitList)
        })

        viewModel.investorProductResponse.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.ERROR   -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireView().context, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.accountsRv.visibility = View.VISIBLE
                    binding.userAccountsTotalPlanValue.text = getString(R.string.user_accounts_total_plan_value, it.data?.body()?.totalPlanValue)
                }
                else -> {
                }
            }
        })
    }
}
