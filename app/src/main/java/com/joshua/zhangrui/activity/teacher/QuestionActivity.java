package com.joshua.zhangrui.activity.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joshua.zhangrui.R;
import com.joshua.zhangrui.activity.core.BaseActivity;
import com.joshua.zhangrui.activity.core.TeacherBaseActivity;
import com.joshua.zhangrui.activity.student.AddQuestionActivity;
import com.joshua.zhangrui.activity.student.QuestionDetailActivity;
import com.joshua.zhangrui.entity.Question;
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

import static com.joshua.zhangrui.R.id.btn_add_question;

@ContentView(R.layout.activity_question2)
public class QuestionActivity extends TeacherBaseActivity implements AdapterView.OnItemClickListener {
    @ViewInject(R.id.lv_question)
    ListView lv_question;

    private List<Question> list_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
    }

    private void initListView() {
        getDataFromServer();
        lv_question.setDivider(null);
        lv_question.setOnItemClickListener(this);
    }

    private void getDataFromServer() {
        RequestParams params = new RequestParams(Server.SERVER);
        params.addBodyParameter("method", Server.SERVER_ALL_QUESTION_TEA);
        params.addBodyParameter("para", getCurrentUser());

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
        list_question = gson.fromJson(result, new TypeToken<List<Question>>() {
        }.getType());

        lv_question.setAdapter(new QuestionAdapter());
    }


    private class QuestionAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list_question.size();
        }

        @Override
        public Question getItem(int position) {
            return list_question.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mBaseActivity, R.layout.list_question,
                        null);
                holder = new ViewHolder();
                holder.tv_asker_name = (TextView) convertView
                        .findViewById(R.id.tv_asker_name);
                holder.tv_question = (TextView) convertView
                        .findViewById(R.id.tv_question);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Question item = getItem(position);
            holder.tv_asker_name.setText(item.getStudentName());
            holder.tv_question.setText(item.getQuestion());

            return convertView;
        }

    }

    private static class ViewHolder {

        TextView tv_asker_name;
        TextView tv_question;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Question question = list_question.get(position);
        Intent intent = new Intent(mBaseActivity, com.joshua.zhangrui.activity.teacher.QuestionDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("question", question);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        getDataFromServer();
    }
}
