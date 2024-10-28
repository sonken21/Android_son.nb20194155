package com.example.emaill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private LinearLayout emailList;

    // Danh sách biểu tượng
    private final int[] emailIcons = {
            R.drawable.icon_1, // Thay bằng tên file biểu tượng của bạn
            R.drawable.icon_2,
            R.drawable.icon_6,
            R.drawable.icon_10,
            R.drawable.icon_5,
            R.drawable.icon_6,
            R.drawable.icon_7,
            R.drawable.icon_8,
            R.drawable.icon_9,
            R.drawable.icon_10
    };
    private final String[] emailTimes = {
            "10:00 AM",
            "11:30 AM",
            "1:15 PM",
            "2:45 PM",
            "3:30 PM",
            "4:20 PM",
            "5:10 PM",
            "6:00 PM",
            "7:50 PM",
            "8:30 PM"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Thiết lập Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emailList = findViewById(R.id.email_list);

        // Giả lập danh sách email với các biểu tượng và thời gian khác nhau
//        for (int i = 0; i < emailIcons.length; i++) {
            //addEmail("Subject " + (i + 1), "Body of email " + (i + 1), emailIcons[i], emailTimes[i]);
//        }
        addEmail("Avast.com "  , "OTP helps reset password... " , emailIcons[0], emailTimes[0]);
        addEmail("Facebook.com " , "Bạn có một thông báo mới... " , emailIcons[1], emailTimes[1]);
        addEmail("Lucy " , "Are you free this afternoon?... " , emailIcons[2], emailTimes[2]);
        addEmail("haley131@gmail.com " , "Can you help me with this one?... " , emailIcons[3], emailTimes[3]);
        addEmail("Ken " , "Come play soccer with me,bro... " , emailIcons[4], emailTimes[4]);
        addEmail("Junior Hama " , "I want to see you tonight... " , emailIcons[5], emailTimes[5]);
        addEmail("Sina12@yahoo " , "The interview will take place... " , emailIcons[6], emailTimes[6]);
        addEmail("scbjc1213 " , "agaba âfagaa fafa... " , emailIcons[7], emailTimes[7]);
        addEmail("Sam " , "Happy birthday to you, Son... " , emailIcons[8], emailTimes[8]);
        addEmail("Emma " , "Hahaa, i see you!... " , emailIcons[9], emailTimes[9]);
    }


    private void addEmail(String subject, String body, int iconResId, String time) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View emailView = inflater.inflate(R.layout.item_email, emailList, false);

        ImageView iconImageView = emailView.findViewById(R.id.email_icon);
        TextView subjectTextView = emailView.findViewById(R.id.email_subject);
        TextView bodyTextView = emailView.findViewById(R.id.email_body);
        TextView timeTextView = emailView.findViewById(R.id.email_time);

        iconImageView.setImageResource(iconResId);
        subjectTextView.setText(subject);
        bodyTextView.setText(body);
        timeTextView.setText(time); // Đặt thời gian cho email

        emailList.addView(emailView);
    }
}