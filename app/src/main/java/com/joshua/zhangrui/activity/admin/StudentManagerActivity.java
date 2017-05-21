package com.joshua.zhangrui.activity.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.AdminBaseActivity;
import com.joshua.zhangrui.entity.Student;
import com.joshua.zhangrui.entity.User;
import com.joshua.zhangrui.global.Server;
import com.joshua.zhangrui.utils.JudgeUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
@ContentView(R.layout.activity_student_manager)
public class StudentManagerActivity extends AdminBaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    @ViewInject(R.id.lv_student)
    ListView lv_student;

    private List<User> list_student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
    }

    private void initListView() {
        getDataFromServer();
        lv_student.setDivider(null);
        lv_student.setOnItemClickListener(this);
        lv_student.setOnItemLongClickListener(this);
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.QUERY_ALL_STUDENT);

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "getDataFromServer: " + result);
                try {
                    JSONObject jo = new JSONObject(result);
                    String jsonList = JudgeUtils.getResult(jo.getString("flag"));

                    parseData(jsonList);
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

    private void parseData(String result) {
        Gson gson = new Gson();
        list_student = gson.fromJson(result, new TypeToken<List<User>>() {
        }.getType());

        lv_student.setAdapter(new CourseAdapter());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final String student = list_student.get(position).getUserName();
        AlertDialog.Builder builder=new AlertDialog.Builder(mBaseActivity);
        builder.setTitle("确认")
                .setMessage("是否删除学生")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteStudent(student);
                    }
                }).show();
        return true;
    }
    private void deleteStudent(String student) {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_DELETE_STUDENT_ADMIN);
        params.addBodyParameter("para", student );
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jo;
                try {
                    jo = new JSONObject(result);
                    Log.d(TAG, "onSuccess: " + result);
                    if (JudgeUtils.isTrue(jo.getString("flag"))) {
                        Toast.makeText(mBaseActivity, "修改成功", Toast.LENGTH_SHORT).show();
                        getDataFromServer();
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

    private class CourseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list_student.size();
        }

        @Override
        public User getItem(int position) {
            return list_student.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mBaseActivity, R.layout.list_user,
                        null);
                holder = new ViewHolder();
                holder.tv_name = (TextView) convertView
                        .findViewById(R.id.tv_name);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            User item = getItem(position);

            holder.tv_name.setText(item.getUserName());

            return convertView;
        }

    }

    private static class ViewHolder {
        TextView tv_name;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        User student=list_student.get(position);
        String name=student.getUserName();
        Intent intent=new Intent(mBaseActivity,StudentInfoActivity.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromServer();
    }
}