package com.example.bai2;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private RadioButton rbEven, rbOdd, rbSquare;
    private Button btnShow;
    private ListView listView;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần giao diện
        etNumber = findViewById(R.id.etNumber);
        rbEven = findViewById(R.id.rbEven);
        rbOdd = findViewById(R.id.rbOdd);
        rbSquare = findViewById(R.id.rbSquare);
        btnShow = findViewById(R.id.btnShow);
        listView = findViewById(R.id.listView);
        tvError = findViewById(R.id.tvError);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvError.setVisibility(View.GONE);  // Ẩn lỗi khi nhấn Show

                // Kiểm tra dữ liệu nhập vào
                String input = etNumber.getText().toString();
                if (input.isEmpty() || Integer.parseInt(input) <= 0) {
                    tvError.setText("Vui lòng nhập số nguyên dương.");
                    tvError.setVisibility(View.VISIBLE);
                    return;
                }

                int n = Integer.parseInt(input);
                ArrayList<String> result = new ArrayList<>();

                // Xác định loại số
                if (rbEven.isChecked()) {
                    for (int i = 0; i <= n; i += 2) {
                        result.add(String.valueOf(i));
                    }
                } else if (rbOdd.isChecked()) {
                    for (int i = 1; i <= n; i += 2) {
                        result.add(String.valueOf(i));
                    }
                } else if (rbSquare.isChecked()) {
                    for (int i = 0; i * i <= n; i++) {
                        result.add(String.valueOf(i * i));
                    }
                } else {
                    tvError.setText("Vui lòng chọn loại số.");
                    tvError.setVisibility(View.VISIBLE);
                    return;
                }

                // Hiển thị kết quả trong ListView
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, result);
                listView.setAdapter(adapter);
            }
        });
    }
}
