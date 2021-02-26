package com.jason.www.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jason.www.R
import kotlinx.android.synthetic.main.activity_kotlin_hello_world.*

class KotlinHelloWorldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_hello_world)
        tv_show.text = "hello, I am kotlin"
    }
}