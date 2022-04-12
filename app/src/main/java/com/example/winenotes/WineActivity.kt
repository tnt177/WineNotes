package com.example.winenotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.winenotes.databinding.ActivityWineBinding

class WineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWineBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}