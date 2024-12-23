package com.example.filemanager

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class FileViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_viewer)

        val textView = findViewById<TextView>(R.id.textView)
        val filePath = intent.getStringExtra("filePath")

        val file = File(filePath ?: "")
        if (file.exists() && file.isFile) {
            textView.text = file.readText()
        } else {
            textView.text = "Unable to open file"
        }
    }
}
