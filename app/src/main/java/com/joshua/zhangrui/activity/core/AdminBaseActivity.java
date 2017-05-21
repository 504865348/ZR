package com.joshua.zhangrui.activity.core;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.admin.StudentManagerActivity;
import com.joshua.zhangrui.activity.admin.SubjectManagerActivity;
import com.joshua.zhangrui.activity.admin.TeacherManagerActivity;
import com.joshua.zhangrui.activity.student.ExamActivity;
import com.joshua.zhangrui.activity.student.JoinCourseActivity;
import com.joshua.zhangrui.activity.student.LearnActivity;
import com.joshua.zhangrui.activity.student.QueryScoreActivity;
import com.joshua.zhangrui.activity.student.QuestionActivity;
import com.joshua.zhangrui.activity.admin.StudentInfoActivity;
import com.joshua.zhangrui.activity.admin.GradeManagerActivity;
import com.joshua.zhangrui.activity.teacher.GradeManageActivity;

public class AdminBaseActivity extends BaseActivity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_student_manager:
                startActivity(new Intent(mBaseActivity, StudentManagerActivity.class));
                finish();
                break;
            case R.id.btn_teacher_manager:
                startActivity(new Intent(mBaseActivity, TeacherManagerActivity.class));
                finish();
                break;
            case R.id.btn_course_manager:
                startActivity(new Intent(mBaseActivity, GradeManagerActivity.class));
                finish();
                break;
            case R.id.btn_subject_manager:
                startActivity(new Intent(mBaseActivity, SubjectManagerActivity.class));
                finish();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
