package vn.edu.hust.studentman

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class MainActivity : AppCompatActivity() {

    private val students = mutableListOf<StudentModel>(
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

    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentAdapter = StudentAdapter(students, object : StudentAdapter.OnItemClickListener {
            override fun onEditClick(position: Int) {
                val student = students[position]
                openEditStudentFragment(student, position)
            }

            override fun onRemoveClick(position: Int) {
                students.removeAt(position)
                studentAdapter.notifyItemRemoved(position)
            }
        })

        findViewById<RecyclerView>(R.id.recycler_view_students).apply {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            openAddStudentFragment()
        }
    }

    private fun openEditStudentFragment(student: StudentModel, position: Int) {
        val editStudentFragment = EditStudentFragment.newInstance(student, position)
        supportFragmentManager.beginTransaction() // Sử dụng beginTransaction() thay vì commit()
            .replace(R.id.main, editStudentFragment) // Thay thế fragment hiện tại
            .addToBackStack(null) // Đưa giao dịch vào back stack
            .commit() // Xác nhận thay đổi
    }

    fun updateStudent(position: Int, updatedStudent: StudentModel) {
        students[position] = updatedStudent
        studentAdapter.notifyItemChanged(position)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_new -> {
                openAddStudentFragment()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openAddStudentFragment() {
        supportFragmentManager.beginTransaction() // Sử dụng beginTransaction() thay vì commit()
            .replace(R.id.main, AddStudentFragment()) // Thay thế fragment hiện tại
            .addToBackStack(null) // Đưa giao dịch vào back stack
            .commit() // Xác nhận thay đổi
    }

    // Thêm sinh viên vào danh sách trong MainActivity
    fun addStudent(newStudent: StudentModel) {
        students.add(newStudent)  // Thêm sinh viên mới vào danh sách
        studentAdapter.notifyItemInserted(students.size - 1)  // Cập nhật giao diện
    }

}
