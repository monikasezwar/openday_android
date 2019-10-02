package com.app.openday.mainpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.openday.R;
import com.app.openday.network.response.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentsNameAdapter extends RecyclerView.Adapter<StudentsNameAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Student> studentList;
    private List<Student> studentListFiltered;
   // private ContactsAdapterListener listener;

    public StudentsNameAdapter(Context context, List<Student> studentList/*, ContactsAdapterListener listener*/) {
        this.context = context;
        //this.listener = listener;
        this.studentList = studentList;
        this.studentListFiltered = studentList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.name_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Student student = studentListFiltered.get(position);
        holder.studentName.setText(student.getName());
        holder.rollNo.setText(student.getRollNo());
    }

    @Override
    public int getItemCount() {
        return studentListFiltered.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        Button rollNo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.student_name);
            rollNo = itemView.findViewById(R.id.roll_no_icon);
        }
/*
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(studentListFiltered.get(getAdapterPosition()));
                }
            });*/
        }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    studentListFiltered = studentList;
                } else {
                    List<Student> filteredList = new ArrayList<>();
                    for (Student row : studentList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getRollNo().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    studentListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = studentListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                studentListFiltered = (ArrayList<Student>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
