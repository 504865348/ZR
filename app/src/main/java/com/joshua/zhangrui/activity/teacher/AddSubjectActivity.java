package com.joshua.zhangrui.activity.teacher;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.BaseActivity;
import com.joshua.zhangrui.activity.core.TeacherBaseActivity;
import com.joshua.zhangrui.global.Server;
import com.joshua.zhangrui.utils.JudgeUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_add_subject)
public class AddSubjectActivity extends TeacherBaseActivity {
    @ViewInject(R.id.et_subject_name)
    EditText et_subject_name;
    @ViewInject(R.id.et_answer)
    EditText et_answer;
    @ViewInject(R.id.et_course)
    EditText et_course;
    @ViewInject(R.id.et_A)
    EditText et_A;
    @ViewInject(R.id.et_B)
    EditText et_B;
    @ViewInject(R.id.et_C)
    EditText et_C;
    @ViewInject(R.id.et_D)
    EditText et_D;
    @ViewInject(R.id.btn_confirm)
    Button btn_confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_confirm:
                addSubject();
                break;
        }

    }

    private void addSubject() {
        Log.d(TAG, "addCourse: ");
        String subject = et_subject_name.getText().toString();
        String answer = et_answer.getText().toString();
        String course = et_course.getText().toString();
        String A = et_A.getText().toString();
        String B = et_B.getText().toString();
        String C = et_C.getText().toString();
        String D = et_D.getText().toString();

        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_ADD_COURSE_QUESTION);
        params.addBodyParameter("para", getCurrentUser() + "&" + course + "&" +subject
                + "&" + A + "&" +B+ "&"+C + "&" +D+ "&" + answer);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "addCourse: "+result);
                try {
                    JSONObject jo = new JSONObject(result);
                    if (JudgeUtils.isTrue(jo.getString("flag"))) {
                        Toast.makeText(mBaseActivity, "添加成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(mBaseActivity, "添加失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mBaseActivity, "添加失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }}
