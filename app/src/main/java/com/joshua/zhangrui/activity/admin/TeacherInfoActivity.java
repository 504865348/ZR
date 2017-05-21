package com.joshua.zhangrui.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.AdminBaseActivity;
import com.joshua.zhangrui.activity.student.ChangePwdActivity;
import com.joshua.zhangrui.global.Server;
import com.joshua.zhangrui.utils.JudgeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static com.joshua.zhangrui.R.id.tv_student_class;
import static com.joshua.zhangrui.R.id.tv_student_contact;
import static com.joshua.zhangrui.R.id.tv_student_name;
import static com.joshua.zhangrui.R.id.tv_student_number;
import static com.joshua.zhangrui.R.id.tv_teacher_contact;
import static com.joshua.zhangrui.R.id.tv_teacher_name;

@ContentView(R.layout.activity_teacher_info)
public class TeacherInfoActivity extends AdminBaseActivity {
    @ViewInject(R.id.tv_teacher_name)
    EditText tv_teacher_name;
    @ViewInject(R.id.tv_teacher_contact)
    EditText tv_teacher_contact;
    private String mStudent_name;
    private String mTeacher_contact;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mName = getIntent().getStringExtra("name");
        getDataFromServer();

    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_GET_TEACHER_INFO);
        params.addBodyParameter("para", mName);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "getDataFromServer: " + result);
                try {
                    JSONObject jo = new JSONObject(result);
                    String jsonList = JudgeUtils.getResult(jo.getString("flag"));
                    JSONArray users=new JSONArray(jsonList);
                    JSONObject user= (JSONObject) users.get(0);
                    mStudent_name= (String) user.get("realName");
                    mTeacher_contact =(String) user.get("tel");
                    tv_teacher_name.setText(mStudent_name);
                    tv_teacher_contact.setText(mTeacher_contact);

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


    public void changeInfo(View view) {
        mStudent_name = tv_teacher_name.getText().toString();
        mTeacher_contact = tv_teacher_contact.getText().toString();


        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_UPDATE_TEACHER_INFO);

        params.addBodyParameter("para", mName + "&" + mStudent_name + "&"  +mTeacher_contact);
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

