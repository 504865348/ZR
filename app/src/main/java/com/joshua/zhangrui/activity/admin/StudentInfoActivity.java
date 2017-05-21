package com.joshua.zhangrui.activity.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.AdminBaseActivity;
import com.joshua.zhangrui.activity.core.StudentBaseActivity;
import com.joshua.zhangrui.activity.student.ChangePwdActivity;
import com.joshua.zhangrui.global.Server;
import com.joshua.zhangrui.utils.JudgeUtils;
import com.joshua.zhangrui.utils.PrefUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static android.R.attr.name;

@ContentView(R.layout.activity_student_info)
public class StudentInfoActivity extends AdminBaseActivity {
    @ViewInject(R.id.tv_student_name)
    EditText tv_student_name;
    @ViewInject(R.id.tv_student_class)
    EditText tv_student_class;
    @ViewInject(R.id.tv_student_number)
    EditText tv_student_number;
    @ViewInject(R.id.tv_student_contact)
    EditText tv_student_contact;
    @ViewInject(R.id.btn_change_pwd)
    Button btn_change;
    private String mStudent_name;
    private String mStudent_class;
    private String mStudent_number;
    private String mStudent_contact;
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mName = getIntent().getStringExtra("name");
        getDataFromServer();
        btn_change.setVisibility(View.INVISIBLE);


    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_GET_STUDENT_INFO);
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
                    mStudent_class=(String) user.get("classRoom");
                    mStudent_number=(String) user.get("number");
                    mStudent_contact=(String) user.get("tel");
                    tv_student_name.setText(mStudent_name);
                    tv_student_class.setText(mStudent_class);
                    tv_student_number.setText(mStudent_number);
                    tv_student_contact.setText(mStudent_contact);

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
        mStudent_name = tv_student_name.getText().toString();
        mStudent_class = tv_student_class.getText().toString();
        mStudent_number = tv_student_number.getText().toString();
        mStudent_contact = tv_student_contact.getText().toString();


        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_UPDATE_STUDENT_INFO);

        params.addBodyParameter("para", mName + "&" + mStudent_name + "&" + mStudent_class + "&" +
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

}
