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
 * 创建日期 ： 2017/4/13 20:47
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class Subject implements Serializable{
    private String subjectName;
    private String A;
    private String B;
    private String C;
    private String D;
    private String answer;
    private String courseName;
    private String teacherName;


    public Subject(String subjectName, String a, String b, String c, String d, String answer, String courseName, String teacherName) {
        this.subjectName = subjectName;
        A = a;
        B = b;
        C = c;
        D = d;
        this.answer = answer;
        this.courseName = courseName;
        this.teacherName = teacherName;
    }



    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }



}
