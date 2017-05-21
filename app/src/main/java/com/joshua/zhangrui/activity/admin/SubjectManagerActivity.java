package com.joshua.zhangrui.activity.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import com.joshua.zhangrui.activity.teacher.AddSubjectActivity;
import com.joshua.zhangrui.activity.teacher.EditSubjectActivity;
import com.joshua.zhangrui.entity.Subject;
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

import static com.joshua.zhangrui.R.id.btn_add_course;
import static com.joshua.zhangrui.R.layout.list_exam;

@ContentView(R.layout.activity_subject_manager)
public class SubjectManagerActivity extends AdminBaseActivity implements AdapterView.OnItemLongClickListener {

    @ViewInject(R.id.lv_course)
    ListView lv_course;

    private List<Subject> list_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
    }

    private void initListView() {
        getDataFromServer();
        lv_course.setDivider(null);
        lv_course.setOnItemLongClickListener(this);

    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_QUERY_SUBJECT_ADMIN);

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
        list_course = gson.fromJson(result, new TypeToken<List<Subject>>() {
        }.getType());

        lv_course.setAdapter(new CourseAdapter());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final String courseName = list_course.get(position).getCourseName();
        AlertDialog.Builder builder=new AlertDialog.Builder(mBaseActivity);
        builder.setTitle("确认")
                .setMessage("是否删除题目")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteSubject(courseName);
                    }
                }).show();
        return true;
    }
    private void deleteSubject(String courseName) {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_DELETE_SUBJECT_ADMIN);
        params.addBodyParameter("para", courseName );
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
            return list_course.size();
        }

        @Override
        public Subject getItem(int position) {
            return list_course.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mBaseActivity, R.layout.list_subject,
                        null);
                holder = new ViewHolder();
                holder.tv_subject_name = (TextView) convertView
                        .findViewById(R.id.tv_subject_name);
                holder.tv_A = (TextView) convertView
                        .findViewById(R.id.tv_A);
                holder.tv_B = (TextView) convertView
                        .findViewById(R.id.tv_B);
                holder.tv_C = (TextView) convertView
                        .findViewById(R.id.tv_C);
                holder.tv_D = (TextView) convertView
                        .findViewById(R.id.tv_D);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Subject item = getItem(position);
            holder.tv_subject_name.setText("题目:" + item.getSubjectName());
            holder.tv_A.setText("A:" + item.getA());
            holder.tv_B.setText("B:" + item.getB());
            holder.tv_C.setText("C:" + item.getC());
            holder.tv_D.setText("D:" + item.getD());

            return convertView;
        }

    }

    private static class ViewHolder {

        TextView tv_subject_name;
        TextView tv_A;
        TextView tv_B;
        TextView tv_C;
        TextView tv_D;
    }




    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromServer();
    }
}

