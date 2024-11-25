package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val nameEditText = findViewById<EditText>(R.id.edit_text_name)
        val idEditText = findViewById<EditText>(R.id.edit_text_id)

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            val name = nameEditText.text.toString()
            val studentId = idEditText.text.toString()

            if (name.isNotEmpty() && studentId.isNotEmpty()) {
                val newStudent = StudentModel(name, studentId)

                val resultIntent = Intent()
                resultIntent.putExtra("new_student", newStudent)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
