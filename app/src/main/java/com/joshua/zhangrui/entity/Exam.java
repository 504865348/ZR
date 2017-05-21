package com.joshua.zhangrui.entity;

/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2017
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2017/4/14 21:11
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class Exam {
    private String studentName;
    private String courseName;
    private String grade;
    private String time;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Exam(String studentName, String courseName, String grade, String time) {

        this.studentName = studentName;
        this.courseName = courseName;
        this.grade = grade;
        this.time = time;
    }
}
