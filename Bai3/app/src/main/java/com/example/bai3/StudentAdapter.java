package com.example.bai3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private List<Student> students;
    private List<Student> filteredStudents;

    public StudentAdapter(List<Student> students) {
        this.students = students;
        this.filteredStudents = new ArrayList<>(students);
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = filteredStudents.get(position);
        holder.tvName.setText(student.getName());
        holder.tvMssv.setText(student.getMssv());
    }

    @Override
    public int getItemCount() {
        return filteredStudents.size();
    }

    public void filter(String keyword) {
        filteredStudents.clear();
        if (keyword.length() < 3) {
            filteredStudents.addAll(students);
        } else {
            for (Student student : students) {
                if (student.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                        student.getMssv().toLowerCase().contains(keyword.toLowerCase())) {
                    filteredStudents.add(student);
                }
            }
        }
        notifyDataSetChanged();
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMssv;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvMssv = itemView.findViewById(R.id.tvMssv);
        }
    }
}
