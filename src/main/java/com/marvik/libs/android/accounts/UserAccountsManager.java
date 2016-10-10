package com.marvik.libs.android.accounts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;


public class UserAccountsManager {
    private Context context;

    public UserAccountsManager(Context context) {
        this.context = context;

    }

    /**
     * Adds a sync account
     *
     * @param accountType account type
     * @param accountName name of account
     * @param authority   authority of the content provider
     */
    public void addSyncAccount(String accountType, String accountName, String authority) {
        if (!isAccountExists(accountType)) {
            addAccount(accountType, accountName, authority);
        }
    }

    /**
     * Adds a sync account
     *
     * @param accountType account type
     * @param accountName name of account
     * @param authority   authority of the content provider
     */
    public void addAccount(String accountType, String accountName, String authority) {
        addAccount(accountType, accountName, authority, false);
    }

    /**
     * Adds a sync account
     *
     * @param accountType account type
     * @param accountName name of account
     * @param authority   authority of the content provider
     * @param forceSync   force the account to sync
     */
    public void addAccount(String accountType, String accountName, String authority, boolean forceSync) {

        Account account = new Account(accountName, accountType);

        addAccount(account);

        if (forceSync) {
            forceSync(account, authority);
        }
    }

    /**
     * Add s Sync Account
     *
     * @param account account
     */
    public void addAccount(Account account) {
        AccountManager accountManager = (AccountManager) getContext().getSystemService(Context.ACCOUNT_SERVICE);
        accountManager.addAccountExplicitly(account, account.type, null);
    }

    /**
     * Checks whether this sync account exists
     *
     * @param account the syncAccount
     * @return isExists
     */
    public boolean isAccountExists(Account account) {
        return isExistsUserAccount(account.type);
    }

    /**
     * Checks whether this sync account exists
     *
     * @param accountType the type of account
     * @return isExists
     */
    public boolean isExistsUserAccount(String accountType) {
        return isAccountExists(accountType);
    }

    /**
     * Checks whether this sync acccount exists
     *
     * @param accountType the account type
     * @return isExists
     */
    public boolean isAccountExists(String accountType) {
        AccountManager accountManager = (AccountManager) getContext().getSystemService(Context.ACCOUNT_SERVICE);
        Account[] accounts = accountManager.getAccounts();

        for (Account account : accounts) {
            if (account.type.equals(accountType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param account
     * @param authority
     */
    public void forceSync(Account account, String authority) {

        if (!isAccountExists(account)) {
            addAccount(account);
        }
        Bundle extras = new Bundle();
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);//Force a manual sync
        extras.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);//Make SyncExecutor Start Immediatley
        ContentResolver.requestSync(account, authority, extras);

    }

    public Context getContext() {
        return context;
    }

}
