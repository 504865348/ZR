package com.joshua.zhangrui.activity.core;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.teacher.QuestionActivity;
import com.joshua.zhangrui.activity.teacher.AddStudentActivity;
import com.joshua.zhangrui.activity.teacher.CourseActivity;
import com.joshua.zhangrui.activity.teacher.GradeManageActivity;
import com.joshua.zhangrui.activity.teacher.SubjectActivity;
import com.joshua.zhangrui.activity.teacher.TeacherInfoActivity;

public class TeacherBaseActivity extends BaseActivity {

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.teacher, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_teacher_info:
                startActivity(new Intent(mBaseActivity,TeacherInfoActivity.class));
                finish();
                break;
            case R.id.btn_student_manage:
                startActivity(new Intent(mBaseActivity,AddStudentActivity.class));
                finish();
                break;
            case R.id.btn_grade_manage:
                startActivity(new Intent(mBaseActivity,GradeManageActivity.class));
                finish();
                break;
            case R.id.btn_subject_manage:
                startActivity(new Intent(mBaseActivity,SubjectActivity.class));
                finish();
                break;
            case R.id.btn_course_manage:
                startActivity(new Intent(mBaseActivity,CourseActivity.class));
                finish();
                break;
            case R.id.btn_communicate_manage:
                startActivity(new Intent(mBaseActivity,QuestionActivity.class));
                finish();
                break;

        }
        return true;
    }
}
