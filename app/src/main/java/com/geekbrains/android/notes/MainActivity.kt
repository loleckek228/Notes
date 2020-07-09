package com.geekbrains.android.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setOnButtonClickBehavior()
    }

    private fun setOnButtonClickBehavior() {
        button.setOnClickListener {
            textView.text = getString(R.string.pressed_button)
        }
    }
}