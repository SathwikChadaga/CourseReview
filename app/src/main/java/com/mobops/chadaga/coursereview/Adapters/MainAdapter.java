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
import com.mobops.chadaga.coursereview.Databases.DatabaseMain;
import com.mobops.chadaga.coursereview.Activities.MainActivity;
import com.mobops.chadaga.coursereview.Objects.Course;
import com.mobops.chadaga.coursereview.R;

import java.util.List;
import java.util.Random;

/**
 * Created by Sathwik on 11-08-2016.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Context context;
    DatabaseMain db;
    String query;
    int cnt;

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public void setQuery(String query) {
        this.query = query;
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
        db = new DatabaseMain(context);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final List<Course> courses = db.getCourses(query);

        holder.courseName.setText(courses.get(position).getCourseName());
        holder.courseNo.setText(courses.get(position).getCourseNo());
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
                intent.putExtra("furtherIntent", courses.get(position).getCourseName());
                intent.putExtra("number", courses.get(position).getCourseNo());

                context.startActivity(intent);
            }
        });

        holder.review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AddReviewActivity.class);
                intent.putExtra(MainActivity.EXTRA_MESSAGE1, courses.get(position).getCourseName());
                intent.putExtra(MainActivity.EXTRA_MESSAGE2, courses.get(position).getCourseNo());
                context.startActivity(intent);
            }
        });
        holder.info.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view){
            Context context = view.getContext();
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("NAME", courses.get(position).getCourseName());
            intent.putExtra("NO", courses.get(position).getCourseNo());
            intent.putExtra("PROF", courses.get(position).getCourseProfs());
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
