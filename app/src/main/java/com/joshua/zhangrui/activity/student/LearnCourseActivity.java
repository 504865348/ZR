package com.joshua.zhangrui.activity.student;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.BaseActivity;
import com.joshua.zhangrui.activity.core.StudentBaseActivity;
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


@ContentView(R.layout.activity_learn_course)
public class LearnCourseActivity extends StudentBaseActivity {
    @ViewInject(R.id.lv_course)
    ListView lv_course;

    private List<Subject> list_course;
    private String mCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCourse = getIntent().getStringExtra("course");
        initListView();


    }

    private void initListView() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        Log.d(TAG, "getDataFromServer: " + mCourse);
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_QUERY_COURSE_SUBJECT);
        params.addBodyParameter("para", mCourse);

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
//                holder.tv_answer = (TextView) convertView
//                        .findViewById(R.id.tv_answer);
//                holder.tv_course_name = (TextView) convertView
//                        .findViewById(R.id.tv_course_name);
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

            final Subject item = getItem(position);
            holder.tv_subject_name.setText("题目:" + item.getSubjectName());
//            holder.tv_answer.setText("正确答案:" + item.getAnswer());
//            holder.tv_course_name.setText("所属课程:" + item.getCourseName());
            holder.tv_A.setText("A:" + item.getA());
            holder.tv_B.setText("B:" + item.getB());
            holder.tv_C.setText("C:" + item.getC());
            holder.tv_D.setText("D:" + item.getD());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mBaseActivity, "正确答案:" + item.getAnswer(), Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

    }

    private static class ViewHolder {

        TextView tv_subject_name;
        //        TextView tv_answer;
//        TextView tv_course_name;
        TextView tv_A;
        TextView tv_B;
        TextView tv_C;
        TextView tv_D;
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromServer();
    }
}