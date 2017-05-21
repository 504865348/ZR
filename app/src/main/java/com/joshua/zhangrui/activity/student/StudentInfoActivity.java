package com.joshua.zhangrui.activity.student;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.StudentBaseActivity;
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


@ContentView(R.layout.activity_student_info)
public class StudentInfoActivity extends StudentBaseActivity {
    @ViewInject(R.id.tv_student_name)
    EditText tv_student_name;
    @ViewInject(R.id.tv_student_class)
    EditText tv_student_class;
    @ViewInject(R.id.tv_student_number)
    EditText tv_student_number;
    @ViewInject(R.id.tv_student_contact)
    EditText tv_student_contact;
    private String mStudent_name;
    private String mStudent_class;
    private String mStudent_number;
    private String mStudent_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStudent_name = PrefUtils.getString(mBaseActivity, "student_name", "");
        mStudent_class = PrefUtils.getString(mBaseActivity, "student_class", "");
        mStudent_number = PrefUtils.getString(mBaseActivity, "student_number", "");
        mStudent_contact = PrefUtils.getString(mBaseActivity, "student_contact", "");
        tv_student_name.setText(mStudent_name);
        tv_student_class.setText(mStudent_class);
        tv_student_number.setText(mStudent_number);
        tv_student_contact.setText(mStudent_contact);
    }


    public void changeInfo(View view) {
        mStudent_name = tv_student_name.getText().toString();
        mStudent_class = tv_student_class.getText().toString();
        mStudent_number = tv_student_number.getText().toString();
        mStudent_contact = tv_student_contact.getText().toString();

        PrefUtils.setString(mBaseActivity, "student_name", mStudent_name);
        PrefUtils.setString(mBaseActivity, "student_class", mStudent_class);
        PrefUtils.setString(mBaseActivity, "student_number", mStudent_number);
        PrefUtils.setString(mBaseActivity, "student_contact", mStudent_contact);


        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_UPDATE_STUDENT_INFO);

        params.addBodyParameter("para", getCurrentUser() + "&" + mStudent_name + "&" + mStudent_class + "&" +
                "" + mStudent_number + "&" + mStudent_contact);
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

    public void changePassword(View view) {
        startActivity(new Intent(mBaseActivity, ChangePwdActivity.class));
    }
}
