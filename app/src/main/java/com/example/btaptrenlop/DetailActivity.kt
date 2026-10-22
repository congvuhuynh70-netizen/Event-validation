package com.example.btaptrenlop

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btaptrenlop.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Nhận dữ liệu từ Intent
        val name = intent.getStringExtra("EXTRA_NAME") ?: ""
        val email = intent.getStringExtra("EXTRA_EMAIL") ?: ""
        val phone = intent.getStringExtra("EXTRA_PHONE") ?: ""
        val msv = intent.getStringExtra("EXTRA_MSV") ?: ""
        val className = intent.getStringExtra("EXTRA_CLASS") ?: ""

        // Hiển thị thông tin
        binding.tvInfo.text = """
            Họ và tên: $name
            Email: $email
            Số điện thoại: $phone
            Mã sinh viên: $msv
            Lớp: $className
        """.trimIndent()

        // Xử lý nút quay lại
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}