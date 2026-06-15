package vcmsa.projects.budgettrackerapp.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import vcmsa.projects.budgettrackerapp.data.Expense
import vcmsa.projects.budgettrackerapp.databinding.FragmentAddExpenseBinding
import vcmsa.projects.budgettrackerapp.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpenseFragment : Fragment() {

    private var _binding: FragmentAddExpenseBinding? = null
    private val binding get() = _binding!!

    private val expenseViewModel: ExpenseViewModel by viewModels()

    private var selectedPhotoUri: Uri? = null

    private val calendar = Calendar.getInstance()

    companion object {
        private const val TAG = "SmartSpendExpense"
    }

    // Image Picker
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            uri?.let {

                selectedPhotoUri = it

                binding.imageViewPhoto.setImageURI(it)

                binding.imageViewPhoto.visibility = View.VISIBLE

                Log.d(TAG, "Expense photo selected")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentAddExpenseBinding.inflate(
                inflater,
                container,
                false
            )

        Log.d(TAG, "Add Expense Screen Loaded")

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        setupCategorySpinner()

        binding.textDate.setOnClickListener {
            showDatePicker()
        }

        binding.textTime.setOnClickListener {
            showTimePicker()
        }

        binding.btnAddPhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.btnSaveExpense.setOnClickListener {
            saveExpense()
        }
    }

    private fun setupCategorySpinner() {

        val categories = listOf(
            "Food",
            "Transport",
            "Entertainment",
            "Bills",
            "Shopping",
            "Education",
            "Other"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )

        adapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spinnerCategory.adapter = adapter
    }

    private fun showDatePicker() {

        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->

                calendar.set(
                    year,
                    month,
                    day
                )

                val format = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.getDefault()
                )

                binding.textDate.text =
                    format.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)

        ).show()
    }

    private fun showTimePicker() {

        TimePickerDialog(
            requireContext(),
            { _, hour, minute ->

                calendar.set(
                    Calendar.HOUR_OF_DAY,
                    hour
                )

                calendar.set(
                    Calendar.MINUTE,
                    minute
                )

                val format = SimpleDateFormat(
                    "HH:mm",
                    Locale.getDefault()
                )

                binding.textTime.text =
                    format.format(calendar.time)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true

        ).show()
    }

    private fun saveExpense() {

        val description =
            binding.editDescription.text.toString().trim()

        val amountText =
            binding.editAmount.text.toString().trim()

        if (description.isEmpty()) {

            binding.editDescription.error =
                "Enter expense description"

            return
        }

        if (amountText.isEmpty()) {

            binding.editAmount.error =
                "Enter expense amount"

            return
        }

        val amount =
            amountText.toDoubleOrNull()

        if (amount == null || amount <= 0) {

            binding.editAmount.error =
                "Enter valid amount"

            return
        }

        val expense = Expense(
            description = description,
            amount = amount,
            category = binding.spinnerCategory.selectedItem.toString(),
            date = binding.textDate.text.toString(),
            time = binding.textTime.text.toString(),
            photoUri = selectedPhotoUri?.toString()
        )

        expenseViewModel.insert(expense)

        Log.d(TAG, "Expense saved: $description")

        Toast.makeText(
            requireContext(),
            "Expense saved successfully",
            Toast.LENGTH_SHORT
        ).show()

        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Add Expense Screen Closed")

        _binding = null
    }
}