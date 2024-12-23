package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import androidx.fragment.app.Fragment

class AddStudentFragment : Fragment() {

    private lateinit var studentNameEditText: EditText
    private lateinit var studentIdEditText: EditText
    private lateinit var addButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_student, container, false)

        studentNameEditText = rootView.findViewById(R.id.edit_text_student_name)
        studentIdEditText = rootView.findViewById(R.id.edit_text_student_id)
        addButton = rootView.findViewById(R.id.button_add_student)

        addButton.setOnClickListener {
            val studentName = studentNameEditText.text.toString()
            val studentId = studentIdEditText.text.toString()

            if (studentName.isNotEmpty() && studentId.isNotEmpty()) {
                val newStudent = StudentModel(studentName, studentId)

                // Gọi addStudent() từ MainActivity để thêm sinh viên
                (activity as? MainActivity)?.addStudent(newStudent)

                // Quay lại màn hình trước
                activity?.supportFragmentManager?.popBackStack()
            }
        }

        return rootView
    }
}
