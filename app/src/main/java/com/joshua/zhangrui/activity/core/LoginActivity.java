package com.joshua.zhangrui.activity.core;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.admin.StudentManagerActivity;
import com.joshua.zhangrui.activity.student.LearnActivity;
import com.joshua.zhangrui.activity.teacher.CourseActivity;
import com.joshua.zhangrui.global.Server;
import com.joshua.zhangrui.utils.JudgeUtils;
import com.joshua.zhangrui.utils.PrefUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class LoginActivity extends BaseActivity {
    private EditText et_username;
    private EditText et_password;
    private RadioGroup rg_type;
    private String username = "";
    private String password = "";
    //学生 教师 管理员
    private String type = "学生";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rg_type = (RadioGroup) this.findViewById(R.id.rg_type);
        et_username = (EditText) this.findViewById(R.id.et_username);
        et_password = (EditText) this.findViewById(R.id.et_password);

        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                int radioButtonId = arg0.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(radioButtonId);
                type = rb.getText().toString();
            }
        });

    }


    public void login(View view) {
        username = et_username.getText().toString();
        password = et_password.getText().toString();


        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_LOGIN);
        params.addBodyParameter("para", username + "&" + password + "&" + type);


        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jo = new JSONObject(result);
                    Log.d(TAG, "onSuccess: "+result);
                    if (JudgeUtils.isTrue(jo.getString("flag"))) {
                        //0没授权 1授权
                        String status = JudgeUtils.getStatu(jo.getString("flag"));
                        Toast.makeText(mBaseActivity, "登录成功", Toast.LENGTH_SHORT).show();
                        //保存用户信息
                        PrefUtils.setString(mBaseActivity,"username",username);
                        PrefUtils.setString(mBaseActivity,"password",password);
                        switch (type) {
                            case "学生":
                                Intent intent1 = new Intent(mBaseActivity, LearnActivity.class);
                                intent1.putExtra("status", status);
                                startActivity(intent1);
                                break;
                            case "教师":
                                Intent intent2 = new Intent(mBaseActivity, CourseActivity.class);
                                intent2.putExtra("status", status);
                                startActivity(intent2);
                                break;
                            case "管理员":
                                Intent intent3 = new Intent(mBaseActivity, StudentManagerActivity.class);
                                intent3.putExtra("status", status);
                                startActivity(intent3);
                                break;
                        }
                    } else {
                        Toast.makeText(mBaseActivity, "登录失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(mBaseActivity, "登录失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void register(View view) {
        Intent intent = new Intent(mBaseActivity, RegisterActivity.class);
        startActivity(intent);
    }
}
