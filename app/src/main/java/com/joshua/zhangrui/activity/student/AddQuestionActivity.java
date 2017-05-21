package com.joshua.zhangrui.activity.student;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.BaseActivity;
import com.joshua.zhangrui.activity.core.StudentBaseActivity;
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
import static com.joshua.zhangrui.R.id.et_name;
import static com.joshua.zhangrui.R.id.et_type;

@ContentView(R.layout.activity_add_qusetion)
public class AddQuestionActivity extends StudentBaseActivity {
    @ViewInject(R.id.et_question)
    EditText et_question;
    @ViewInject(R.id.et_course)
    EditText et_course;
    @ViewInject(R.id.et_teacher)
    EditText et_teacher;
    @ViewInject(R.id.btn_confirm)
    Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                addCourse();
                break;
        }

    }

    private void addCourse() {
        Log.d(TAG, "addCourse: ");
        String question = et_question.getText().toString();
        String teacher = et_teacher.getText().toString();
        String course = et_course.getText().toString();
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_ASK_QUESTION);
        params.addBodyParameter("para", getCurrentUser() + "&" + teacher + "&" + course+"&"+question);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "addCourse: " + result);
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

    }
}
