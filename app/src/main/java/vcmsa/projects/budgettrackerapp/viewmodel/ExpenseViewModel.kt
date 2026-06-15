package vcmsa.projects.budgettrackerapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vcmsa.projects.budgettrackerapp.data.Expense
import vcmsa.projects.budgettrackerapp.data.ExpenseDatabase

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val expenseDao =
        ExpenseDatabase.getDatabase(application).expenseDao()

    val allExpenses: LiveData<List<Expense>> =
        expenseDao.getAllExpenses()

    fun insert(expense: Expense) {

        viewModelScope.launch(Dispatchers.IO) {

            expenseDao.insertExpense(expense)

            Log.d(
                "SmartSpend",
                "Expense Saved: ${expense.description} - R${expense.amount}"
            )
        }
    }
}