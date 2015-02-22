package com.manturf.authenticationsample;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends ActionBarActivity {

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
                login(name, password);
            }
        });
    }

    // ログイン処理
    public void login(final String name, final String password) {
        // TODO:このメソッドは非同期の通信処理でログインを試みます。
        // ログインに成功した場合、loginSuccess()を呼び出します。
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
