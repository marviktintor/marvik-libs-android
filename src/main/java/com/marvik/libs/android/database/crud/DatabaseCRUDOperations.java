package com.marvik.libs.android.database.crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public interface DatabaseCRUDOperations {

    Context getContext();

    Uri getUri();

    Uri insert(ContentValues values);

    Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder);

    int update(ContentValues values, String selection, String[] selectionArgs);

    int delete(String selection, String[] selectionArgs);

}
