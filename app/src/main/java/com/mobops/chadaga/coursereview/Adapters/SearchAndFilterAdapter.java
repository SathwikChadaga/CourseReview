package com.mobops.chadaga.coursereview.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobops.chadaga.coursereview.Activities.AddReviewActivity;
import com.mobops.chadaga.coursereview.Activities.DetailsActivity;
import com.mobops.chadaga.coursereview.Activities.ReadReviewActivity;
import com.mobops.chadaga.coursereview.Databases.DatabaseForSearch;
import com.mobops.chadaga.coursereview.Activities.MainActivity;
import com.mobops.chadaga.coursereview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sathwik on 11-08-2016.
 */
public class SearchAndFilterAdapter extends RecyclerView.Adapter<SearchAndFilterAdapter.ViewHolder> {

    Context context;
    DatabaseForSearch db;

    List<String> name = new ArrayList<>();
    List<String> number = new ArrayList<>();
    int cnt = 0;

    public void setCount(int cnt) {
        this.cnt = cnt;
    }

    public void setCourseName(List<String> name) {
        this.name = name;
    }

    public void setCourseCode(List<String> number) {
        this.number = number;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView courseName, courseNo/*, courseProfs*/;
        public final ImageView info, review;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            courseName = (TextView) itemView.findViewById(R.id.tv_course_name);
            courseNo = (TextView) itemView.findViewById(R.id.tv_course_no);
            info = (ImageView) itemView.findViewById(R.id.b_info);
            review = (ImageView) itemView.findViewById(R.id.b_review);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.ll_course_item);
//            courseProfs = (TextView) itemView.findViewById(R.id.tv_course_profs_details);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        context = parent.getContext();
        db = new DatabaseForSearch(context);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.courseName.setText(name.get(position));
        holder.courseNo.setText(number.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ReadReviewActivity.class);
                String[] names = {"Abhishek Rao", "Romil Sonigra", "Shishir Bhat", "Rohan Singh", "Rohan Kaulgekar", "Divyashish Choudry", "Karteek Dhara"};
                String[] reviewGrading = {
                        "Grading involves two quizzes and endsem.",
                        "Assignments carry 20 marks out of 100 otherwise 2 quizzes and endsem as usual.",
                        "2 quizzes and endsem but assignments carry 20 marks, but they are peace considering the course credits.",
                        "I almost cupped because of quizzes but recovered in endsem, grades are manageable",
                        "Assignments are a headache. Every week new assignment, it really annoyed me.",
                        "Quiz 1 is easy, quiz 2 is tougher compared to quiz 1, and endsem are the toughest. So my advice: put fight in quizzes :)",
                        "Assignments are to be submitted every week which overall carry 20% weightage."
                };
                String[] reviewXp = {"Instructor is really clear, precise, and funny at times.",
                        "I liked the course, problems are structured to make the student think.",
                        "I enjoyed this course.",
                        "We were required to do more than just going to the class: assignments :(.",
                        "This is one of those courses which makes you learn things beyond what is being taught.",
                        "Not the best course to follow as an introduction to the subject.",
                        "Assignments gave very good practice."
                };

                shuffleArray(names);
                shuffleArray(reviewGrading);
                shuffleArray(reviewXp);

                Random r = new Random();
                int anInt = r.nextInt(5) + 2;

                intent.putExtra("names", names);
                intent.putExtra("grading", reviewGrading);
                intent.putExtra("xp", reviewXp);
                intent.putExtra("count", anInt);
                intent.putExtra("furtherIntent", name.get(position));
                intent.putExtra("number", number.get(position));

                context.startActivity(intent);
            }
        });

        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent1 = new Intent(context, AddReviewActivity.class);
                intent1.putExtra(MainActivity.EXTRA_MESSAGE1, name.get(position));
                intent1.putExtra(MainActivity.EXTRA_MESSAGE2, number.get(position));
                context.startActivity(intent1);
            }
        });
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Context context = view.getContext();
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("NAME", name.get(position));
                intent.putExtra("NO", number.get(position));
                intent.putExtra("PROF", "Bijoy Krishna Das, Anjan Chakraborty, Devendra Jalihal");
                context.startActivity(intent);

            }
        });

    }

    public void shuffleArray(String[] array) {
        Random rgen = new Random();  // Random number generator
        for (int i = 0; i < array.length; i++) {
            int randomPosition = rgen.nextInt(array.length - i) + i;
            String temp = array[i];
            array[i] = array[randomPosition];
            array[randomPosition] = temp;
        }
    }

    @Override
    public int getItemCount() {
        return cnt;
    }

}
