package com.joshua.zhangrui.activity.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.TeacherBaseActivity;
import com.joshua.zhangrui.global.Server;
import com.joshua.zhangrui.utils.JudgeUtils;
import com.joshua.zhangrui.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Objects;

import static com.joshua.zhangrui.R.id.tv_student_class;
import static com.joshua.zhangrui.R.id.tv_student_contact;
import static com.joshua.zhangrui.R.id.tv_student_name;
import static com.joshua.zhangrui.R.id.tv_student_number;

@ContentView(R.layout.activity_teacher_info)
public class TeacherInfoActivity extends TeacherBaseActivity {
    @ViewInject(R.id.tv_teacher_name)
    EditText tv_teacher_name;
    @ViewInject(R.id.tv_teacher_contact)
    EditText tv_teacher_contact;
    private String mTeacher_name;
    private String mTeacher_contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTeacher_name = PrefUtils.getString(mBaseActivity, "teacher_name", "");
        mTeacher_contact = PrefUtils.getString(mBaseActivity, "teacher_contact", "");
        tv_teacher_name.setText(mTeacher_name);
        tv_teacher_contact.setText(mTeacher_contact);
    }


    public void changeInfo(View view) {
        mTeacher_name = tv_teacher_name.getText().toString();
        mTeacher_contact = tv_teacher_contact.getText().toString();

        PrefUtils.setString(mBaseActivity, "teacher_name", mTeacher_name);
        PrefUtils.setString(mBaseActivity, "teacher_contact", mTeacher_contact);


        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_UPDATE_TEACHER_INFO);

        params.addBodyParameter("para", getCurrentUser() + "&" + mTeacher_name + "&" + mTeacher_contact );
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jo;
                try {
                    jo = new JSONObject(result);
                    Log.d(TAG, "onSuccess: " + result);
                    if (JudgeUtils.isTrue(jo.getString("flag"))) {
                        Toast.makeText(mBaseActivity, "修改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mBaseActivity, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mBaseActivity, "网络异常", Toast.LENGTH_SHORT).show();
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
