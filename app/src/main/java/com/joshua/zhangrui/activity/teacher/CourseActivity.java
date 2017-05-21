package com.joshua.zhangrui.activity.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.BaseActivity;
import com.joshua.zhangrui.activity.core.TeacherBaseActivity;
import com.joshua.zhangrui.entity.Course;
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

import static com.joshua.zhangrui.R.layout.list_question;


@ContentView(R.layout.activity_course)
public class CourseActivity extends TeacherBaseActivity implements AdapterView.OnItemLongClickListener {
    @ViewInject(R.id.btn_add_course)
    FloatingActionButton btn_add_course;
    @ViewInject(R.id.lv_course)
    ListView lv_course;

    private List<Course> list_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn_add_course.setOnClickListener(this);
        initListView();
    }

    private void initListView() {
        getDataFromServer();
        lv_course.setDivider(null);
        lv_course.setOnItemLongClickListener(this);
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_QUERY_COURSE);
        Log.d(TAG, "getCurrentUser: "+getCurrentUser());
        params.addBodyParameter("para",getCurrentUser());

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "getDataFromServer: "+result);
                try {
                    JSONObject jo = new JSONObject(result);
                    String jsonList= JudgeUtils.getResult(jo.getString("flag"));
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
        list_course = gson.fromJson(result, new TypeToken<List<Course>>() {
        }.getType());

        lv_course.setAdapter(new CourseAdapter());
    }



    private class CourseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list_course.size();
        }

        @Override
        public Course getItem(int position) {
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
                convertView = View.inflate(mBaseActivity, R.layout.list_course,
                        null);
                holder = new ViewHolder();
                holder.tv_course_name = (TextView) convertView
                        .findViewById(R.id.tv_course_name);
                holder.tv_course_brief = (TextView) convertView
                        .findViewById(R.id.tv_course_brief);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Course item = getItem(position);

            holder.tv_course_name.setText(item.getCourseName());
            holder.tv_course_brief.setText(item.getCourseBrief());


            return convertView;
        }

    }

    private static class ViewHolder {
        TextView tv_course_name;
        TextView tv_course_brief;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_course:
                startActivity(new Intent(mBaseActivity, AddCourseActivity.class));
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final String course = list_course.get(position).getCourseName();
        AlertDialog.Builder builder=new AlertDialog.Builder(mBaseActivity);
        builder.setTitle("确认")
                .setMessage("是否删除课程")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCourse(course);
                    }
                }).show();
        return true;
    }

    private void deleteCourse(String course) {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_DELETE_COURSE_TEACHER);
        params.addBodyParameter("para", getCurrentUser() + "&" + course );
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
    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromServer();
    }
}
