package vn.edu.hust.studentman

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var studentAdapter: StudentAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var studentDatabaseHelper: StudentDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentDatabaseHelper = StudentDatabaseHelper(this)

        recyclerView = findViewById(R.id.recycler_view_students)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadStudentsFromDatabase()

        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            val newStudent = StudentModel("Tên Sinh Viên", "SV123")
            addStudent(newStudent)  // Gọi addStudent() khi nhấn nút thêm sinh viên mới
        }
    }

    // Phương thức thêm sinh viên vào cơ sở dữ liệu và làm mới danh sách
    fun addStudent(student: StudentModel) {
        studentDatabaseHelper.addStudent(student)  // Thêm sinh viên vào cơ sở dữ liệu
        loadStudentsFromDatabase()  // Làm mới danh sách sinh viên
    }

    // Phương thức cập nhật sinh viên trong cơ sở dữ liệu và danh sách
    fun updateStudent(position: Int, updatedStudent: StudentModel) {
        // Cập nhật sinh viên trong cơ sở dữ liệu
        studentDatabaseHelper.updateStudent(position, updatedStudent)

        // Làm mới danh sách sinh viên
        loadStudentsFromDatabase()
    }

    // Phương thức để tải danh sách sinh viên từ cơ sở dữ liệu
    private fun loadStudentsFromDatabase() {
        val students = studentDatabaseHelper.getAllStudents()
        studentAdapter = StudentAdapter(students, object : StudentAdapter.OnItemClickListener {
            override fun onEditClick(position: Int) {
                val student = students[position]
                openEditStudentFragment(student, position)
            }

            override fun onRemoveClick(position: Int) {
                studentDatabaseHelper.deleteStudent(position)
                loadStudentsFromDatabase()
            }
        })
        recyclerView.adapter = studentAdapter
    }

    // Phương thức để mở fragment chỉnh sửa sinh viên
    private fun openEditStudentFragment(student: StudentModel, position: Int) {
        val editStudentFragment = EditStudentFragment.newInstance(student, position)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, editStudentFragment)
            .addToBackStack(null)
            .commit()
    }
}
