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
 * 创建日期 ： 2017/4/12 20:04
 * <p>
 * 描 述 ：
 * <p>
 * ============================================================
 **/

public class Course {
    private String courseName;
    private String courseBrief;
    private String teacher;

    public Course(String courseName, String courseBrief, String teacher) {
        this.courseName = courseName;
        this.courseBrief = courseBrief;
        this.teacher = teacher;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseBrief() {
        return courseBrief;
    }

    public void setCourseBrief(String courseBrief) {
        this.courseBrief = courseBrief;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

}
