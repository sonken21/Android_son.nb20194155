package com.example.bai3;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etSearch;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.etSearch);
        recyclerView = findViewById(R.id.recyclerView);

        // Dữ liệu mẫu
        studentList = new ArrayList<>();
        studentList.add(new Student("Nguyen Van Minh", "12345"));
        studentList.add(new Student("Tran Thi Bich", "23456"));
        studentList.add(new Student("Le Van Luyen", "34567"));
        studentList.add(new Student("Pham Thi Dung", "45678"));
        studentList.add(new Student("Hoang Van Duc", "56789"));
        studentList.add(new Student("Nong Van Den", "12129"));
        studentList.add(new Student("Nguyen Thi Mai", "24359"));

        // Cài đặt RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(adapter);

        // Xử lý sự kiện nhập liệu
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
}
