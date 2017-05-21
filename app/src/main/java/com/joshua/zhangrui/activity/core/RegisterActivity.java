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
import com.joshua.zhangrui.global.Server;
import com.joshua.zhangrui.utils.JudgeUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class RegisterActivity extends BaseActivity {

    private EditText et_username;
    private EditText et_password;
    private RadioGroup rg_type;
    private String username = "";
    private String password = "";
    private String type = "学生";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        rg_type = (RadioGroup) this.findViewById(R.id.rg_type);
        et_username = (EditText) this.findViewById(R.id.et_username);
        et_password = (EditText) this.findViewById(R.id.et_password);

        rg_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                int radioButtonId = arg0.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) RegisterActivity.this.findViewById(radioButtonId);
                type = rb.getText().toString();
            }
        });

    }

    public void register(View view) {
        username = et_username.getText().toString();
        password = et_password.getText().toString();

        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_REGISTER);
        params.addBodyParameter("para",username +"&"+password+"&"+type+"&1");

        x.http().post(params, new Callback.CommonCallback<String>() {


            @Override
            public void onSuccess(String result) {
                Log.i(TAG, "onSuccess: "+result);
                try {
                    JSONObject jo = new JSONObject(result);
                    if(JudgeUtils.isTrue(jo.getString("flag"))){
                        Toast.makeText(mBaseActivity, "注册成功，请登录", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(mBaseActivity,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(mBaseActivity, "注册失败", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: "+ex);
                Toast.makeText(mBaseActivity, "注册失败", Toast.LENGTH_SHORT).show();
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
}
