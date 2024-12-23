package vn.edu.hust.studentman

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSQL = "CREATE TABLE $TABLE_STUDENTS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_STUDENT_ID TEXT)"
        db?.execSQL(createTableSQL)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENTS")
        onCreate(db)
    }

    // Thêm sinh viên mới
    fun addStudent(student: StudentModel) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, student.studentName)
            put(COLUMN_STUDENT_ID, student.studentId)
        }
        db.insert(TABLE_STUDENTS, null, values)
        db.close()
    }

    // Lấy tất cả sinh viên
    @SuppressLint("Range")
    fun getAllStudents(): List<StudentModel> {
        val students = mutableListOf<StudentModel>()
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_STUDENTS, arrayOf(COLUMN_NAME, COLUMN_STUDENT_ID),
            null, null, null, null, null
        )

        // Kiểm tra xem cursor có di chuyển đến dòng đầu tiên không
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val studentName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                val studentId = cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_ID))
                students.add(StudentModel(studentName, studentId))
            } while (cursor.moveToNext())
        }

        cursor.close()  // Đảm bảo đóng cursor sau khi sử dụng
        db.close()

        return students
    }


    // Xóa sinh viên
    fun deleteStudent(position: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_STUDENTS, "$COLUMN_ID = ?", arrayOf(position.toString()))
        db.close()
    }

    // Cập nhật sinh viên
    fun updateStudent(position: Int, updatedStudent: StudentModel) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, updatedStudent.studentName)
            put(COLUMN_STUDENT_ID, updatedStudent.studentId)
        }
        db.update(TABLE_STUDENTS, values, "$COLUMN_ID = ?", arrayOf(position.toString()))
        db.close()
    }

    companion object {
        private const val DATABASE_NAME = "student_db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_STUDENTS = "students"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_STUDENT_ID = "student_id"
    }
}
