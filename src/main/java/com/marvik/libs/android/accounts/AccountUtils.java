package com.marvik.libs.android.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;


public class AccountUtils {

    /**
     * Add an account
     *
     * @param context
     * @param accountType
     * @param accountName
     * @param authority
     * @param forceSync
     */
    public static void addAccount(Context context, String accountType, String accountName, String authority, boolean forceSync) {

        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        Account account = new Account(accountName, accountType);

        accountManager.addAccountExplicitly(account, accountType, null);

        if (forceSync) {
            forceSync(account, authority);
        }
    }

    /**
     * Check if an account exists
     *
     * @param context
     * @param accountType
     * @return
     */
    public static boolean isAccountExists(Context context, String accountType) {

        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccounts();

        for (Account account : accounts) {
            if (account.type.equals(accountType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Force sync an account
     *
     * @param account
     * @param authority
     */
    public static void forceSync(Account account, String authority) {

        Bundle extras = new Bundle();
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);//Force a manual sync
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);//Make SyncExecutor Start Immediatley
        ContentResolver.requestSync(account, authority, extras);

    }

}
