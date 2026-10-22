package com.example.btaptrenlop

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.example.btaptrenlop.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRealtimeValidation()
        setupSubmitButton()
    }

    /**
     * Lắng nghe qua event doOnTextChanged -> kiểm tra tính hợp lệ ngay khi người dùng gõ phím
     */
    private fun setupRealtimeValidation() {
        binding.etName.doOnTextChanged { text, _, _, _ ->
            validateName(text?.toString() ?: "")
        }

        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            validateEmail(text?.toString() ?: "")
        }

        binding.etPhone.doOnTextChanged { text, _, _, _ ->
            validatePhone(text?.toString() ?: "")
        }

        binding.etMsv.doOnTextChanged { text, _, _, _ ->
            validateMsv(text?.toString() ?: "")
        }

        binding.etClass.doOnTextChanged { text, _, _, _ ->
            validateClass(text?.toString() ?: "")
        }
    }

    /**
     * Kiểm tra tính hợp lệ của Họ và tên
     */
    private fun validateName(name: String): Boolean {
        return if (name.trim().isEmpty()) {
            binding.tilName.error = getString(R.string.error_name_empty)
            false
        } else {
            binding.tilName.error = null
            true
        }
    }

    /**
     * Kiểm tra tính hợp lệ của Email
     */
    private fun validateEmail(email: String): Boolean {
        val trimmedEmail = email.trim()
        return when {
            trimmedEmail.isEmpty() -> {
                binding.tilEmail.error = getString(R.string.error_email_empty)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches() -> {
                binding.tilEmail.error = getString(R.string.error_email_invalid)
                false
            }
            else -> {
                binding.tilEmail.error = null
                true
            }
        }
    }

    /**
     * Kiểm tra tính hợp lệ của Số điện thoại
     */
    private fun validatePhone(phone: String): Boolean {
        val trimmedPhone = phone.trim()
        val phoneRegex = Regex("^0\\d{9,10}$")
        return when {
            trimmedPhone.isEmpty() -> {
                binding.tilPhone.error = getString(R.string.error_phone_empty)
                false
            }
            !trimmedPhone.matches(phoneRegex) -> {
                binding.tilPhone.error = getString(R.string.error_phone_invalid)
                false
            }
            else -> {
                binding.tilPhone.error = null
                true
            }
        }
    }

    /**
     * Kiểm tra tính hợp lệ của Mã sinh viên (MSV)
     */
    private fun validateMsv(msv: String): Boolean {
        return if (msv.trim().isEmpty()) {
            binding.tilMsv.error = getString(R.string.error_msv_empty)
            false
        } else {
            binding.tilMsv.error = null
            true
        }
    }

    /**
     * Kiểm tra tính hợp lệ của Lớp
     */
    private fun validateClass(className: String): Boolean {
        return if (className.trim().isEmpty()) {
            binding.tilClass.error = getString(R.string.error_class_empty)
            false
        } else {
            binding.tilClass.error = null
            true
        }
    }

    /**
     * Xác nhận khi bấm nút Đăng ký ngay
     * Kiểm tra tổng thể toàn Form
     * Nếu tất cả hợp lệ, chuyển sang DetailActivity và truyền dữ liệu
     */
    private fun setupSubmitButton() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etName.text?.toString() ?: ""
            val email = binding.etEmail.text?.toString() ?: ""
            val phone = binding.etPhone.text?.toString() ?: ""
            val msv = binding.etMsv.text?.toString() ?: ""
            val className = binding.etClass.text?.toString() ?: ""

            val isNameValid = validateName(name)
            val isEmailValid = validateEmail(email)
            val isPhoneValid = validatePhone(phone)
            val isMsvValid = validateMsv(msv)
            val isClassValid = validateClass(className)

            if (isNameValid && isEmailValid && isPhoneValid && isMsvValid && isClassValid) {
                // Chuyển màn hình sang DetailActivity qua Intent
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("EXTRA_NAME", name)
                    putExtra("EXTRA_EMAIL", email)
                    putExtra("EXTRA_PHONE", phone)
                    putExtra("EXTRA_MSV", msv)
                    putExtra("EXTRA_CLASS", className)
                }
                startActivity(intent)
            }
        }
    }
}