package com.joshua.zhangrui.activity.student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.BaseActivity;
import com.joshua.zhangrui.activity.core.StudentBaseActivity;
import com.joshua.zhangrui.entity.Question;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_question_detail)
public class QuestionDetailActivity extends StudentBaseActivity {
    @ViewInject(R.id.tv_student)
    TextView tv_student;
    @ViewInject(R.id.tv_question)
    TextView tv_question;
    @ViewInject(R.id.tv_teacher)
    TextView tv_teacher;
    @ViewInject(R.id.tv_course)
    TextView tv_course;
    @ViewInject(R.id.tv_answer)
    TextView tv_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Question question= (Question) getIntent().getSerializableExtra("question");
        tv_student.setText("提问者："+question.getStudentName());
        tv_question.setText("问题："+question.getQuestion());
        tv_teacher.setText("回答者："+question.getTeacherName());
        tv_course.setText("所属课程："+question.getCourse());
        tv_answer.setText("答复："+question.getAnswer());
    }

    @Override
    public void onClick(View v) {

    }
}
