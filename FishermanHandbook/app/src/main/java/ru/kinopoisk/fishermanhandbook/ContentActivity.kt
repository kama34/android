package ru.kinopoisk.fishermanhandbook

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import ru.kinopoisk.fishermanhandbook.databinding.ActivityMainBinding
import ru.kinopoisk.fishermanhandbook.databinding.ContentLayoutBinding

class ContentActivity : AppCompatActivity() {
    private lateinit var binding: ContentLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvTitle2.text = intent.getStringExtra("title")
        binding.tvContent2.text = intent.getStringExtra("content")
        binding.im2.setImageResource(intent.getIntExtra("image", R.drawable.som))
    }
}