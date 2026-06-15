package vcmsa.projects.budgettrackerapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vcmsa.projects.budgettrackerapp.data.ExpenseDatabase
import vcmsa.projects.budgettrackerapp.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "SmartSpendSearch"
    }

    private val expenseDao by lazy {
        ExpenseDatabase.getDatabase(requireContext()).expenseDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(
            inflater,
            container,
            false
        )

        Log.d(TAG, "Search Screen Opened")

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        binding.btnSearch.setOnClickListener {

            val keyword =
                binding.etSearch.text.toString().trim()

            if (keyword.isEmpty()) {

                Toast.makeText(
                    requireContext(),
                    "Enter a keyword to search",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            lifecycleScope.launch {

                val results =
                    withContext(Dispatchers.IO) {

                        expenseDao.getAllExpensesOnce().filter {

                            it.description?.contains(
                                keyword,
                                ignoreCase = true
                            ) == true
                        }
                    }

                val adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        results.map {
                            "Description: ${it.description}\nCategory: ${it.category}\nAmount: R${it.amount}"
                        }
                    )

                binding.listResults.adapter = adapter

                Toast.makeText(
                    requireContext(),
                    "${results.size} result(s) found",
                    Toast.LENGTH_SHORT
                ).show()

                Log.d(
                    TAG,
                    "Search completed: ${results.size} results"
                )
            }
        }
    }

    override fun onDestroyView() {

        super.onDestroyView()

        Log.d(TAG, "Search Screen Closed")

        _binding = null
    }
}