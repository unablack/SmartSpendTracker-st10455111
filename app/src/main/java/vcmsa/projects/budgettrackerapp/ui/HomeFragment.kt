package vcmsa.projects.budgettrackerapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vcmsa.projects.budgettrackerapp.R
import vcmsa.projects.budgettrackerapp.data.ExpenseDatabase
import vcmsa.projects.budgettrackerapp.data.Goal
import vcmsa.projects.budgettrackerapp.databinding.FragmentHomeBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "SmartSpendHome"
    }

    private val goalDao by lazy {
        ExpenseDatabase.getDatabase(requireContext()).goalDao()
    }

    private val expenseDao by lazy {
        ExpenseDatabase.getDatabase(requireContext()).expenseDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        Log.d(TAG, "SmartSpend Dashboard Loaded")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddExpense.setOnClickListener {
            Log.d(TAG, "Navigating to Add Expense")
            findNavController().navigate(R.id.action_homeFragment_to_addExpenseFragment)
        }

        binding.btnViewSummary.setOnClickListener {
            Log.d(TAG, "Viewing Expense Summary")
            findNavController().navigate(R.id.action_homeFragment_to_summaryFragment)
        }

        binding.btnViewBudgetGraph.setOnClickListener {
            Log.d(TAG, "Viewing Spending Analytics")
            findNavController().navigate(R.id.action_homeFragment_to_budgetGraphFragment)
        }

        binding.btnSearchExpenses.setOnClickListener {
            Log.d(TAG, "Searching Expenses")
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        binding.saveGoalButton.setOnClickListener {

            val min = binding.minGoalEditText.text.toString().toDoubleOrNull()
            val max = binding.maxGoalEditText.text.toString().toDoubleOrNull()

            if (min != null && max != null && min <= max) {

                val goal = Goal(
                    minGoal = min,
                    maxGoal = max
                )

                lifecycleScope.launch(Dispatchers.IO) {

                    goalDao.insertGoal(goal)

                    withContext(Dispatchers.Main) {

                        Toast.makeText(
                            requireContext(),
                            "SmartSpend goals saved successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        evaluateMonthlySpending()
                    }
                }

            } else {

                Toast.makeText(
                    requireContext(),
                    "Enter valid goal values (Min must be less than Max)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        goalDao.getLatestGoal().observe(viewLifecycleOwner) { goal ->

            binding.goalPreviewText.text =
                if (goal != null) {
                    "SmartSpend Goal\nMinimum: ${goal.minGoal}\nMaximum: ${goal.maxGoal}"
                } else {
                    "No spending goals set yet."
                }

            lifecycleScope.launch {
                evaluateMonthlySpending()
            }
        }

        lifecycleScope.launch {
            showDailySpendingSummary()
        }
    }

    private suspend fun evaluateMonthlySpending() {

        withContext(Dispatchers.IO) {

            val goal = goalDao.getLatestGoalOnce()
            val expenses = expenseDao.getAllExpensesOnce()

            val calendar = Calendar.getInstance()

            val currentMonth = calendar.get(Calendar.MONTH)
            val currentYear = calendar.get(Calendar.YEAR)

            val sdf = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            )

            val expensesThisMonth = expenses.filter { expense ->

                expense.date?.let { dateStr ->

                    val date = sdf.parse(dateStr)

                    date?.let {

                        val cal = Calendar.getInstance()
                        cal.time = it

                        cal.get(Calendar.MONTH) == currentMonth &&
                                cal.get(Calendar.YEAR) == currentYear

                    } ?: false

                } ?: false
            }

            val totalThisMonth =
                expensesThisMonth.sumOf { it.amount }

            val result = when {

                goal == null ->
                    "No Goal Set"

                totalThisMonth < goal.minGoal ->
                    "Below Minimum Goal"

                totalThisMonth > goal.maxGoal ->
                    "Above Maximum Goal"

                else ->
                    "Within Budget Goal"
            }

            withContext(Dispatchers.Main) {

                binding.statusTextView.text =
                    "Monthly Status\n$result\nTotal Spent: R$totalThisMonth"
            }
        }
    }

    private suspend fun showDailySpendingSummary() {

        withContext(Dispatchers.IO) {

            val sdf =
                SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )

            val today =
                sdf.format(Calendar.getInstance().time)

            val expensesToday =
                expenseDao.getAllExpensesOnce().filter {
                    it.date == today
                }

            val totalToday =
                expensesToday.sumOf { it.amount }

            withContext(Dispatchers.Main) {

                binding.dailySpendingTextView.text =
                    "Today's Spending: R$totalToday"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Dashboard Closed")

        _binding = null
    }
}