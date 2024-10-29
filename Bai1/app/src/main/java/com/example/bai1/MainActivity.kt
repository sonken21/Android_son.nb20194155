package com.example.bai1

// MainActivity.kt
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etMSSV: EditText
    private lateinit var etHoTen: EditText
    private lateinit var rgGioiTinh: RadioGroup
    private lateinit var etEmail: EditText
    private lateinit var etSoDienThoai: EditText
    private lateinit var btnChonNgaySinh: Button
    private lateinit var calendarView: CalendarView
    private lateinit var spPhuongXa: Spinner
    private lateinit var spQuanHuyen: Spinner
    private lateinit var spTinhThanh: Spinner
    private lateinit var cbTheThao: CheckBox
    private lateinit var cbDienAnh: CheckBox
    private lateinit var cbAmNhac: CheckBox
    private lateinit var cbDongY: CheckBox
    private lateinit var btnSubmit: Button
    private var selectedDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ view
        etMSSV = findViewById(R.id.etMSSV)
        etHoTen = findViewById(R.id.etHoTen)
        rgGioiTinh = findViewById(R.id.rgGioiTinh)
        etEmail = findViewById(R.id.etEmail)
        etSoDienThoai = findViewById(R.id.etSoDienThoai)
        btnChonNgaySinh = findViewById(R.id.btnChonNgaySinh)
        calendarView = findViewById(R.id.calendarView)
        spPhuongXa = findViewById(R.id.spPhuongXa)
        spQuanHuyen = findViewById(R.id.spQuanHuyen)
        spTinhThanh = findViewById(R.id.spTinhThanh)
        cbTheThao = findViewById(R.id.cbTheThao)
        cbDienAnh = findViewById(R.id.cbDienAnh)
        cbAmNhac = findViewById(R.id.cbAmNhac)
        cbDongY = findViewById(R.id.cbDongY)
        btnSubmit = findViewById(R.id.btnSubmit)

        // Sự kiện chọn ngày sinh
        btnChonNgaySinh.setOnClickListener {
            calendarView.visibility = if (calendarView.visibility == View.GONE) View.VISIBLE else View.GONE
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
            btnChonNgaySinh.text = "Ngày sinh: $selectedDate"
            calendarView.visibility = View.GONE
        }

        // Kiểm tra Submit
        btnSubmit.setOnClickListener {
            if (validateForm()) {
                Toast.makeText(this, "Form đã được gửi thành công!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        if (etMSSV.text.isEmpty()) {
            etMSSV.error = "Vui lòng nhập MSSV"
            return false
        }
        if (etHoTen.text.isEmpty()) {
            etHoTen.error = "Vui lòng nhập Họ tên"
            return false
        }
        if (rgGioiTinh.checkedRadioButtonId == -1) {
            Toast.makeText(this, "Vui lòng chọn giới tính", Toast.LENGTH_SHORT).show()
            return false
        }
        if (etEmail.text.isEmpty()) {
            etEmail.error = "Vui lòng nhập Email"
            return false
        }
        if (etSoDienThoai.text.isEmpty()) {
            etSoDienThoai.error = "Vui lòng nhập Số điện thoại"
            return false
        }
        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày sinh", Toast.LENGTH_SHORT).show()
            return false
        }
        if (spPhuongXa.selectedItemPosition == 0 || spQuanHuyen.selectedItemPosition == 0 || spTinhThanh.selectedItemPosition == 0) {
            Toast.makeText(this, "Vui lòng chọn địa chỉ đầy đủ", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!cbDongY.isChecked) {
            Toast.makeText(this, "Vui lòng đồng ý với các điều khoản", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
// MainActivity.kt
data class XaPhuong(
    val name: String,
    val type: String,
    val slug: String,
    val name_with_type: String,
    val path: String,
    val path_with_type: String,
    val code: String,
    val parent_code: String
)

data class QuanHuyen(
    val name: String,
    val type: String,
    val slug: String,
    val name_with_type: String,
    val path: String,
    val path_with_type: String,
    val code: String,
    val parent_code: String,
    val `xa-phuong`: Map<String, XaPhuong>
)

data class TinhThanh(
    val name: String,
    val slug: String,
    val type: String,
    val name_with_type: String,
    val code: String,
    val `quan-huyen`: Map<String, QuanHuyen>
)

data class Data(val data: Map<String, TinhThanh>)
// MainActivity.kt
import com.google.gson.Gson
import java.io.InputStreamReader

private fun loadDataFromJson(): Map<String, TinhThanh>? {
    val inputStream = assets.open("data.json")
    val reader = InputStreamReader(inputStream)
    return Gson().fromJson(reader, Data::class.java).data
}
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Đọc dữ liệu từ file JSON
    val data = loadDataFromJson()

    // Lấy danh sách tên tỉnh/thành
    val tinhThanhList = data?.map { it.value.name } ?: emptyList()
    val tinhThanhAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tinhThanhList)
    tinhThanhAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spTinhThanh.adapter = tinhThanhAdapter

    spTinhThanh.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            val selectedTinhCode = data?.keys?.toList()?.get(position)
            val selectedTinhThanh = data?.get(selectedTinhCode)

            // Lấy danh sách tên quận/huyện cho tỉnh/thành đã chọn
            val quanHuyenList = selectedTinhThanh?.`quan-huyen`?.map { it.value.name } ?: emptyList()
            val quanHuyenAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, quanHuyenList)
            quanHuyenAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spQuanHuyen.adapter = quanHuyenAdapter

            spQuanHuyen.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedQuanCode = selectedTinhThanh?.`quan-huyen`?.keys?.toList()?.get(position)
                    val selectedQuanHuyen = selectedTinhThanh?.`quan-huyen`?.get(selectedQuanCode)

                    // Lấy danh sách tên xã/phường cho quận/huyện đã chọn
                    val xaPhuongList = selectedQuanHuyen?.`xa-phuong`?.map { it.value.name } ?: emptyList()
                    val xaPhuongAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, xaPhuongList)
                    xaPhuongAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spPhuongXa.adapter = xaPhuongAdapter
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>) {}
    }
}
