package com.example.change;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText sourceAmount;
    private EditText destinationAmount;
    private Spinner sourceCurrency;
    private Spinner destinationCurrency;

    // Tỉ lệ chuyển đổi tiền tệ (ví dụ)
    private final double USD_TO_VND = 23000;
    private final double VND_TO_USD = 1.0 / USD_TO_VND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceAmount = findViewById(R.id.source_amount);
        destinationAmount = findViewById(R.id.destination_amount);
        sourceCurrency = findViewById(R.id.source_currency);
        destinationCurrency = findViewById(R.id.destination_currency);

        // Thiết lập spinner cho tiền tệ
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceCurrency.setAdapter(adapter);
        destinationCurrency.setAdapter(adapter);

        // Thêm listener cho EditText và Spinner
        sourceAmount.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                convertCurrency();
            }
        });

        sourceCurrency.setOnItemSelectedListener(new CurrencySelectionListener());
        destinationCurrency.setOnItemSelectedListener(new CurrencySelectionListener());
    }

    private void convertCurrency() {
        String amountStr = sourceAmount.getText().toString();
        if (amountStr.isEmpty()) {
            destinationAmount.setText("");
            return;
        }

        double amount = Double.parseDouble(amountStr);
        String source = sourceCurrency.getSelectedItem().toString();
        String destination = destinationCurrency.getSelectedItem().toString();

        double convertedAmount = amount;
        if (source.equals("USD") && destination.equals("VND")) {
            convertedAmount = amount * USD_TO_VND;
        } else if (source.equals("VND") && destination.equals("USD")) {
            convertedAmount = amount * VND_TO_USD;
        }

        destinationAmount.setText(String.valueOf(convertedAmount));
    }

    private class CurrencySelectionListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            convertCurrency();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}
