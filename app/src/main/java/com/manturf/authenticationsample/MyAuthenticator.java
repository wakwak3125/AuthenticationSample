package com.manturf.authenticationsample;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by RyoSakaguchi on 15/02/22.
 */
public class MyAuthenticator extends AbstractAccountAuthenticator {

    public static final String ACCOUNT_TYPE = "com.manturf.authenticationsample";

    final Context mContext;

    public MyAuthenticator(Context context, Context mContext) {
        super(context);
        this.mContext = mContext;
    }

    public MyAuthenticator(Context context) {
        super(context);
        mContext = context;

    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    // コレは最低限必要
    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response,
                             String accountType, String authTokenType,
                             String[] requiredFeatures, Bundle options) throws NetworkErrorException {

        // アカウントの追加を行う画面を呼び出すIntentを生成
        final Intent intent
                = new Intent(mContext,
                LoginActivity.class);
        // アカウントの追加後の戻り先の画面を設定　
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        // Intentを返却
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    // コレも最低限必要
    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response,
                               Account account, String authTokenType,
                               Bundle options) throws NetworkErrorException {
        Bundle result = new Bundle();

        if (!authTokenType.equals(AUTH_TOKEN_TYPE_READ_ONLY) && !authTokenType.equals(AUTH_TOKEN_TYPE_FULL_ACCESS)) {
            result.putInt(AccountManager.KEY_ERROR_CODE, ERR_INVALID_AUTH_TOKEN_TYPE);
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType: "+ authTokenType);
        } else {
            final AccountManager manager = AccountManager.get(mContext);
            String authToken = manager.peekAuthToken(account, authTokenType);

            // 認証トークンが存在しない場合で、かつパスワードがアカウントに紐付いている場合は、それを使って認証トークンを取得しなおします
            if (authToken == null || authToken.isEmpty()) {
                final String password = manager.getPassword(account);
                if (password != null) {
                    // TODO:サーバーに接続して認証トークンを取得します
                    authToken = "AuthToken";
                }
            }

            // 認証トークンが存在する場合は、その情報を返します
            if (authToken != null && !authToken.isEmpty()) {
                result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            } else {
                // ユーザーに認証情報を入力してもらう必要がある場合は、addAccount のときと同様に Authenticator Activity を起動します
                final Intent intent = new Intent(mContext, LoginActivity.class);

                // ここで、Authenticator Acitivity に必要な情報をセットします。
                // 作成したアカウントは response 経由で伝達するので必ずセットしておきます
                intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

                // 戻り値には KEY_INTENT が含まれていれば十分です。
                result.putParcelable(AccountManager.KEY_INTENT, intent);
            }
        }
        return result;

        /*AccountManager manager = AccountManager.get(mContext);
        String name = account.name;

        // TODO:本来は暗号化の必要あり
        String password = manager.getPassword(account);

        // TODO:ここで通信を行い、ユーザー名(メール)とパスワードからトークンの取得を行う
        String authToken = "AUTH_TOKEN";
        //トークンをここでキャッシュ
        manager.setAuthToken(account, authTokenType, authToken);

        // トークンを返却
        Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, name);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
        result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        return result;*/
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
