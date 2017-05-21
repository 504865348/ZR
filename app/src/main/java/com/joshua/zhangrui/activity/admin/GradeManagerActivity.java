package com.joshua.zhangrui.activity.admin;

import android.content.DialogInterface;
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
import com.joshua.zhangrui.entity.Exam;
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
@ContentView(R.layout.activity_grade_manage)
public class GradeManagerActivity extends AdminBaseActivity implements AdapterView.OnItemLongClickListener {
    @ViewInject(R.id.lv_exam)
    ListView lv_exam;

    private List<Exam> list_exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
    }

    private void initListView() {
        getDataFromServer();
        lv_exam.setDivider(null);
        lv_exam.setOnItemLongClickListener(this);
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_QUERY_GRADE_ADMIN);

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
        list_exam = gson.fromJson(result, new TypeToken<List<Exam>>() {
        }.getType());

        lv_exam.setAdapter(new ExamAdapter());
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final String time = list_exam.get(position).getTime();
        AlertDialog.Builder builder=new AlertDialog.Builder(mBaseActivity);
        builder.setTitle("确认")
                .setMessage("是否删除成绩")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteGrade(time);
                    }
                }).show();
        return true;
    }
    private void deleteGrade(String time) {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_DELETE_GRADE_ADMIN);
        params.addBodyParameter("para", time );
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

    private class ExamAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list_exam.size();
        }

        @Override
        public Exam getItem(int position) {
            return list_exam.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mBaseActivity, R.layout.list_exam,
                        null);
                holder = new ViewHolder();
                holder.tv_stu_name = (TextView) convertView
                        .findViewById(R.id.tv_stu_name);
                holder.tv_course_name = (TextView) convertView
                        .findViewById(R.id.tv_course_name);
                holder.tv_grade = (TextView) convertView
                        .findViewById(R.id.tv_grade);
                holder.tv_time = (TextView) convertView
                        .findViewById(R.id.tv_time);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Exam item = getItem(position);

            holder.tv_stu_name.setText("学生姓名："+item.getStudentName());
            holder.tv_course_name.setText("测试课程："+item.getCourseName());
            holder.tv_grade.setText("测试成绩："+item.getGrade());
            holder.tv_time.setText(item.getTime());

            return convertView;
        }

    }

    private static class ViewHolder {
        TextView tv_stu_name;
        TextView tv_course_name;
        TextView tv_grade;
        TextView tv_time;
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

