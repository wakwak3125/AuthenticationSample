package com.manturf.authenticationsample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends ActionBarActivity {

    public static String TAG = LoginActivity.class.getSimpleName();

    public String Name;
    public String Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText nameEdit = (EditText) findViewById(R.id.name);
        final EditText passwordEdit = (EditText) findViewById(R.id.password);
        Button button = (Button) findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                try {
                    login(name, password);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                final String url = "http://manturf2.herokuapp.com/api/sessions";

                public StringRequest postReq = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    RespMsg respMsg = JsonConverter.toObject(response,RespMsg.class);

                                }

                            }
                        })
            }
        });
    }

    // ログイン処理
    public void login(final String name, final String password) throws JsonProcessingException {
        // TODO:このメソッドは非同期の通信処理でログインを試みます。
        // ログインに成功した場合、loginSuccess()を呼び出します。
        LoginActivity loginActivity = new LoginActivity();
        Map<String, LoginActivity> loginActivityMap = new HashMap<String,LoginActivity>();
        loginActivityMap.put("user", loginActivity);

        loginActivity.Name = name;
        loginActivity.Pass = password;

        ObjectMapper mapper = new ObjectMapper();
        String loginInfo = mapper.writeValueAsString(loginActivityMap);
        Log.i(TAG + "JSONに変身やで！", loginInfo);

        loginSuccess(name, password);
    }



    public void loginSuccess(final String name, final String password) {

        Account account = new Account(name, "com.example.test");
        AccountManager am = AccountManager.get(this);
        // アカウント情報を保存
        // TODO:本来はパスワードを暗号化する必要があります
        am.addAccountExplicitly(account, password, null);

        // 認証画面終了
        setResult(RESULT_OK);
        finish();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
