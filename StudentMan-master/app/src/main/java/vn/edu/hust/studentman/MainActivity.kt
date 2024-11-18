package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var students: MutableList<StudentModel>
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

  val students = mutableListOf(
      StudentModel("Nguyễn Văn An", "SV001"),
      StudentModel("Trần Thị Bảo", "SV002"),
      StudentModel("Lê Hoàng Cường", "SV003"),
      StudentModel("Phạm Thị Dung", "SV004"),
      StudentModel("Đỗ Minh Đức", "SV005"),
      StudentModel("Vũ Thị Hoa", "SV006"),
      StudentModel("Hoàng Văn Hải", "SV007"),
      StudentModel("Bùi Thị Hạnh", "SV008"),
      StudentModel("Đinh Văn Hùng", "SV009"),
      StudentModel("Nguyễn Thị Linh", "SV010"),
      StudentModel("Phạm Văn Long", "SV011"),
      StudentModel("Trần Thị Mai", "SV012"),
      StudentModel("Lê Thị Ngọc", "SV013"),
      StudentModel("Vũ Văn Nam", "SV014"),
      StudentModel("Hoàng Thị Phương", "SV015"),
      StudentModel("Đỗ Văn Quân", "SV016"),
      StudentModel("Nguyễn Thị Thu", "SV017"),
      StudentModel("Trần Văn Tài", "SV018"),
      StudentModel("Phạm Thị Tuyết", "SV019"),
      StudentModel("Lê Văn Vũ", "SV020")
    )

        // Khởi tạo adapter và truyền vào hàm xử lý sự kiện Edit
        studentAdapter = StudentAdapter(students) { position ->
            showEditStudentDialog(position)
        }

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // Sự kiện cho nút "Add new"
        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            showAddStudentDialog()
        }
    }

    private fun showAddStudentDialog() {
        val dialogBuilder = AlertDialog.Builder(this)

        // Tạo Layout cho dialog
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val studentNameEditText = EditText(this).apply {
            hint = "Enter Student Name"
        }

        val studentIdEditText = EditText(this).apply {
            hint = "Enter Student ID"
        }

        layout.addView(studentNameEditText)
        layout.addView(studentIdEditText)

        dialogBuilder.setTitle("Add New Student")
            .setView(layout)
            .setPositiveButton("Add") { _, _ ->
                val studentName = studentNameEditText.text.toString().trim()
                val studentId = studentIdEditText.text.toString().trim()

                if (studentName.isNotEmpty() && studentId.isNotEmpty()) {
                    // Thêm sinh viên vào danh sách
                    val newStudent = StudentModel(studentName, studentId)
                    studentAdapter.addStudent(newStudent)
                } else {
                    Toast.makeText(this, "Please enter both name and ID", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)

        dialogBuilder.create().show()
    }

    private fun showEditStudentDialog(position: Int) {
        val student = students[position]

        // Tạo dialog cho việc chỉnh sửa thông tin sinh viên
        val dialogBuilder = AlertDialog.Builder(this)

        // Tạo Layout cho dialog
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val studentNameEditText = EditText(this).apply {
            setText(student.studentName)  // Điền sẵn tên sinh viên
        }

        val studentIdEditText = EditText(this).apply {
            setText(student.studentId)  // Điền sẵn mã sinh viên
        }

        layout.addView(studentNameEditText)
        layout.addView(studentIdEditText)

        dialogBuilder.setTitle("Edit Student Information")
            .setView(layout)
            .setPositiveButton("Save") { _, _ ->
                val newStudentName = studentNameEditText.text.toString().trim()
                val newStudentId = studentIdEditText.text.toString().trim()

                if (newStudentName.isNotEmpty() && newStudentId.isNotEmpty()) {
                    // Cập nhật thông tin sinh viên trong danh sách
                    students[position] = StudentModel(newStudentName, newStudentId)

                    // Cập nhật RecyclerView
                    studentAdapter.notifyItemChanged(position)
                } else {
                    Toast.makeText(this, "Please enter both name and ID", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)

        dialogBuilder.create().show()
    }
}