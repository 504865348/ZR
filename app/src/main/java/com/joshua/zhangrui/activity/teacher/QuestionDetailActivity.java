package com.joshua.zhangrui.activity.teacher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.BaseActivity;
import com.joshua.zhangrui.activity.core.TeacherBaseActivity;
import com.joshua.zhangrui.entity.Question;
import com.joshua.zhangrui.global.Server;
import com.joshua.zhangrui.utils.JudgeUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static android.R.attr.name;
import static android.R.attr.type;
import static com.joshua.zhangrui.R.id.btn_answer;
import static com.joshua.zhangrui.R.id.et_name;
import static com.joshua.zhangrui.R.id.et_type;
import static com.joshua.zhangrui.R.id.tv_answer;

@ContentView(R.layout.activity_question_detail2)
public class QuestionDetailActivity extends TeacherBaseActivity {
    @ViewInject(R.id.tv_student)
    TextView tv_student;
    @ViewInject(R.id.tv_question)
    TextView tv_question;
    @ViewInject(R.id.tv_teacher)
    TextView tv_teacher;
    @ViewInject(R.id.tv_course)
    TextView tv_course;
    @ViewInject(R.id.et_answer)
    EditText et_answer;
    @ViewInject(R.id.btn_answer)
    Button btn_answer;
    private Question mQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = (Question) getIntent().getSerializableExtra("question");
        tv_student.setText("提问者：" + mQuestion.getStudentName());
        tv_question.setText("问题：" + mQuestion.getQuestion());
        tv_teacher.setText("回答者：" + mQuestion.getTeacherName());
        tv_course.setText("所属课程：" + mQuestion.getCourse());
        btn_answer.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_answer:
                answerQuestion();
        }
    }

    private void answerQuestion() {
        Log.d(TAG, "addCourse: ");
        String answer=et_answer.getText().toString();

        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_ANSWER_QUESTION);
        params.addBodyParameter("para", getCurrentUser() + "&" + mQuestion.getQuestion()
                + "&" + answer);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "addCourse: " + result);
                try {
                    JSONObject jo = new JSONObject(result);
                    if (JudgeUtils.isTrue(jo.getString("flag"))) {
                        Toast.makeText(mBaseActivity, "回复成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(mBaseActivity, "回复失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mBaseActivity, "回复失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
}
