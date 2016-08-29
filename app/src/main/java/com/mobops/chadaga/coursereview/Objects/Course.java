package com.mobops.chadaga.coursereview.Objects;

/**
 * Created by Sathwik on 11-08-2016.
 */
public class Course {
    String courseName, courseNo, courseProfs;

    public Course() {
    }

    public Course(String courseName, String courseNo, String courseProfs) {
        this.courseName = courseName;
        this.courseNo = courseNo;
        this.courseProfs = courseProfs;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseProfs() {
        return courseProfs;
    }

    public void setCourseProfs(String courseProfs) {
        this.courseProfs = courseProfs;
    }
}
