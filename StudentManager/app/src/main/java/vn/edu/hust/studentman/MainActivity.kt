package vn.edu.hust.studentman


import StudentAdapter
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004")
  )

  private lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Initialize the adapter with the student list
    studentAdapter = StudentAdapter(students)

    // Set up the RecyclerView
    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    // Handle the "Add new" button click
    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      val intent = Intent(this, AddStudentActivity::class.java)
      startActivityForResult(intent, REQUEST_CODE_ADD)
    }
  }

  // Inflate the options menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  // Handle the item selection in the options menu
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.add_student -> {
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_ADD)
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }

  // Handle the result from AddStudentActivity
  @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
      val newStudent = data?.getSerializableExtra("new_student") as? StudentModel
      newStudent?.let {
        students.add(it)
        studentAdapter.notifyItemInserted(students.size - 1)
      }
    }
  }

  companion object {
    private const val REQUEST_CODE_ADD = 1
  }
}
