package ca.mcgill.ecse321.myTutor;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private String error = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    public void login(View v) {
        error = "";
        final TextView usertv = (TextView) findViewById(R.id.username);
        String username = usertv.getText().toString();
        final TextView passtv = (TextView) findViewById(R.id.password);
        String password = passtv.getText().toString();
        RequestParams rp = new RequestParams();
        rp.add("username", username);
        rp.add("password", password);

        HttpUtils.get("login/", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                usertv.setText("");
                passtv.setText("");
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                try {
                    intent.putExtra("user", response.get("name").toString());
                    intent.putExtra("id", response.getInt("idNumber"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }

    public void signup(View v) {
        error = "";
        final TextView usertv = (TextView) findViewById(R.id.username);
        String username = usertv.getText().toString();
        final TextView passtv = (TextView) findViewById(R.id.password);
        String password = passtv.getText().toString();
        RequestParams rp = new RequestParams();
        rp.add("name", username);
        rp.add("password", password);
        rp.add("Type", "Tutor");

        HttpUtils.post("signup/", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                refreshErrorMessage();
                usertv.setText("");
                passtv.setText("");
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                try {
                    intent.putExtra("user", response.get("name").toString());
                    intent.putExtra("id", response.getInt("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    error += errorResponse.get("message").toString();
                } catch (JSONException e) {
                    error += e.getMessage();
                }
                refreshErrorMessage();
            }
        });
    }
}
