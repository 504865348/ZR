package com.joshua.zhangrui.activity.core;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.student.ExamActivity;
import com.joshua.zhangrui.activity.student.JoinCourseActivity;
import com.joshua.zhangrui.activity.student.LearnActivity;
import com.joshua.zhangrui.activity.student.QueryScoreActivity;
import com.joshua.zhangrui.activity.student.QuestionActivity;
import com.joshua.zhangrui.activity.student.StudentInfoActivity;

public class StudentBaseActivity extends BaseActivity {

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.student, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_student_info:
                startActivity(new Intent(mBaseActivity, StudentInfoActivity.class));
                finish();
                break;
            case R.id.btn_join_course:
                startActivity(new Intent(mBaseActivity, JoinCourseActivity.class));
                finish();
                break;
            case R.id.btn_learn_course:
                startActivity(new Intent(mBaseActivity, LearnActivity.class));
                finish();
                break;
            case R.id.btn_grade_query:
                startActivity(new Intent(mBaseActivity, QueryScoreActivity.class));
                finish();
                break;
            case R.id.btn_online_test:
                startActivity(new Intent(mBaseActivity, ExamActivity.class));
                finish();
                break;
            case R.id.btn_communicate_manage:
                startActivity(new Intent(mBaseActivity, QuestionActivity.class));
                finish();
                break;
        }
        return true;
    }
}
