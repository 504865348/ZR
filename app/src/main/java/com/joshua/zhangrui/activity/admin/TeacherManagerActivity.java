package com.joshua.zhangrui.activity.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

import static com.joshua.zhangrui.R.layout.list_student;
import static com.joshua.zhangrui.R.menu.student;

@ContentView(R.layout.activity_teacher_manager)
public class TeacherManagerActivity extends AdminBaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    @ViewInject(R.id.lv_teacher)
    ListView lv_teacher;

    private List<User> list_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
    }

    private void initListView() {
        getDataFromServer();
        lv_teacher.setDivider(null);
        lv_teacher.setOnItemClickListener(this);
        lv_teacher.setOnItemLongClickListener(this);
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.QUERY_ALL_TEACHER);

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
        list_teacher = gson.fromJson(result, new TypeToken<List<User>>() {
        }.getType());

        lv_teacher.setAdapter(new CourseAdapter());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final String teacher = list_teacher.get(position).getUserName();
        AlertDialog.Builder builder=new AlertDialog.Builder(mBaseActivity);
        builder.setTitle("确认")
                .setMessage("是否删除教师")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTeacher(teacher);
                    }
                }).show();
        return true;
    }
    private void deleteTeacher(String teacher) {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_DELETE_TEACHER_ADMIN);
        params.addBodyParameter("para", teacher );
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
            return list_teacher.size();
        }

        @Override
        public User getItem(int position) {
            return list_teacher.get(position);
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
        User teacher=list_teacher.get(position);
        String name=teacher.getUserName();
        Intent intent=new Intent(mBaseActivity,TeacherInfoActivity.class);
        intent.putExtra("name",name);
        startActivity(intent);
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromServer();
    }
}