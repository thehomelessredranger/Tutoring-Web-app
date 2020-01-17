package ca.mcgill.ecse321.myTutor;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity {

    String[] course_subject, course_name, course_number, course_avg;

    private String error = null;
    private String user_name;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = getIntent().getExtras();
        user_name = bundle.getString("user");
        user_id = bundle.getInt("id");
        TextView greeting = (TextView) findViewById(R.id.greeting);
        greeting.setText("Welcome, \n" + user_name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshErrorMessage() {
        // set the error message
        TextView tvError = (TextView) findViewById(R.id.error);
        tvError.setText(error);

        if (error == null || error.length() == 0) {
            tvError.setVisibility(View.GONE);
        } else {
            tvError.setVisibility(View.VISIBLE);
        }
    }

    public void mycourses(View v) {
        setContentView(R.layout.content_courses);
        Button all = findViewById(R.id.allcourses);
        all.setVisibility(View.VISIBLE);
        Button my = findViewById(R.id.mycourses);
        my.setVisibility(View.GONE);
        HttpUtils.get("Courses/" + user_id, new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Log.d("TESTER", "successGET");
                course_subject = new String[response.length()];
                course_number = new String[response.length()];
                course_name = new String[response.length()];
                course_avg = new String[response.length()];
                Log.d("TESTER", String.valueOf(response.length()));

                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jobj = response.getJSONObject(i);

                        course_subject[i] = jobj.getString("subject");
                        course_number[i] = jobj.getString("number");
                        course_name[i] = jobj.getString("name");
                        course_avg[i] = jobj.getString("averageRate");
                        Log.d("TESTER", course_subject[i] + course_number[i] + course_name[i] + course_avg[i]);

                    }

                    buildCoursesTable();
                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
            }
        });


    }

    public void courses(View v) {
        setContentView(R.layout.content_courses);
        Button my = findViewById(R.id.mycourses);
        my.setVisibility(View.VISIBLE);
        Button all = findViewById(R.id.allcourses);
        all.setVisibility(View.GONE);
        HttpUtils.get("Courses", new RequestParams(), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {

                Log.d("TESTER", "successGET");
                course_subject = new String[response.length()];
                course_number = new String[response.length()];
                course_name = new String[response.length()];
                course_avg = new String[response.length()];
                Log.d("TESTER", String.valueOf(response.length()));

                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jobj = response.getJSONObject(i);

                        course_subject[i] = jobj.getString("subject");
                        course_number[i] = jobj.getString("number");
                        course_name[i] = jobj.getString("name");
                        course_avg[i] = jobj.getString("averageRate");
                        Log.d("TESTER", course_subject[i] + course_number[i] + course_name[i] + course_avg[i]);

                    }

                    buildCoursesTable();
                }catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
            }
        });


    }

    public void buildCoursesTable(){
        TableRow row;
        TextView t1, t2, t3, t4;
        TableLayout course_table = (TableLayout)findViewById(R.id.courses_table);
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) 1, getResources().getDisplayMetrics());
        for (int i = 0; i < course_subject.length; i++) {

            row = new TableRow(this);
            t1 = new TextView(this);
            t2 = new TextView(this);
            t3 = new TextView(this);
            t4 = new TextView(this);
            t1.setText(course_subject[i]);
            t2.setText(course_number[i]);
            t3.setText(course_name[i]);
            t4.setText(course_avg[i]);
            row.addView(t1);
            row.addView(t2);
            row.addView(t3);
            row.addView(t4);

            course_table.addView(row, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        }
    }

    public void back(View v) {
        setContentView(R.layout.content_home);
    }
}