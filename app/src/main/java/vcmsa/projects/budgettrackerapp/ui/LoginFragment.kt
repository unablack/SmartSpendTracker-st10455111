package vcmsa.projects.budgettrackerapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import vcmsa.projects.budgettrackerapp.R
import vcmsa.projects.budgettrackerapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val TAG = "SmartSpendLogin"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        Log.d(TAG, "Login screen loaded")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (username.isEmpty()) {
                binding.etUsername.error = "Enter username"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPassword.error = "Enter password"
                return@setOnClickListener
            }

            Log.d(TAG, "User logged into SmartSpend: $username")

            Toast.makeText(
                requireContext(),
                "Welcome to SmartSpend, $username",
                Toast.LENGTH_SHORT
            ).show()

            // Navigate to SmartSpend Dashboard
            findNavController().navigate(
                R.id.action_loginFragment_to_homeFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Login screen destroyed")

        _binding = null
    }
}
