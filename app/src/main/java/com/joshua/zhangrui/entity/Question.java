package com.joshua.zhangrui.entity;

import java.io.Serializable;

/**
 * ============================================================
 * <p>
 * 版 权 ： 吴奇俊  (c) 2017
 * <p>
 * 作 者 : 吴奇俊
 * <p>
 * 版 本 ： 1.0
 * <p>
 * 创建日期 ： 2017/4/23 20:22
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class Question implements Serializable {
    private String studentName;
    private String teacherName;
    private String course;
    private String question;
    private String answer;

    public Question(String studentName, String teacherName, String course, String question, String answer) {
        this.studentName = studentName;
        this.teacherName = teacherName;
        this.course = course;
        this.question = question;
        this.answer = answer;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
