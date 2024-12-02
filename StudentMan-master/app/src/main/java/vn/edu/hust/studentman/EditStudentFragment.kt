package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import androidx.fragment.app.Fragment

class EditStudentFragment : Fragment() {

    private lateinit var studentNameEditText: EditText
    private lateinit var studentIdEditText: EditText
    private lateinit var updateButton: Button
    private var studentPosition: Int = -1
    private var student: StudentModel? = null

    companion object {
        private const val ARG_STUDENT_NAME = "student_name"
        private const val ARG_STUDENT_ID = "student_id"
        private const val ARG_POSITION = "position"

        fun newInstance(student: StudentModel, position: Int): EditStudentFragment {
            val fragment = EditStudentFragment()
            val args = Bundle()
            args.putString(ARG_STUDENT_NAME, student.studentName)
            args.putString(ARG_STUDENT_ID, student.studentId)
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_edit_student, container, false)

        studentNameEditText = rootView.findViewById(R.id.edit_text_student_name)
        studentIdEditText = rootView.findViewById(R.id.edit_text_student_id)
        updateButton = rootView.findViewById(R.id.button_update_student)

        // Lấy dữ liệu sinh viên từ arguments
        arguments?.let {
            val studentName = it.getString(ARG_STUDENT_NAME)
            val studentId = it.getString(ARG_STUDENT_ID)
            studentPosition = it.getInt(ARG_POSITION)

            // Hiển thị thông tin sinh viên lên UI
            studentNameEditText.setText(studentName)
            studentIdEditText.setText(studentId)
        }

        // Xử lý sự kiện nhấn nút Cập nhật
        updateButton.setOnClickListener {
            val updatedName = studentNameEditText.text.toString()
            val updatedId = studentIdEditText.text.toString()

            if (updatedName.isNotEmpty() && updatedId.isNotEmpty() && studentPosition != -1) {
                // Cập nhật thông tin sinh viên trong danh sách của MainActivity
                val updatedStudent = StudentModel(updatedName, updatedId)
                (activity as? MainActivity)?.updateStudent(studentPosition, updatedStudent)

                // Quay lại màn hình chính
                activity?.supportFragmentManager?.popBackStack()
            }
        }

        return rootView
    }
}
