package com.marvik.libs.android.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * ViewsUtils
 * Contains simple method that play around with android views
 * Created by victor on 7/11/2016.
 */
public class ViewsUtils {

    private Context context;

    public ViewsUtils(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    /**
     * Shows a toast
     *
     * @param text
     */
    public void toast(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    /**
     * Shows a custom toast
     *
     * @param text
     * @param duration
     */
    public void toast(String text, int duration) {
        Toast toast = new Toast(getContext());
        TextView view = new TextView(getContext());
        view.setPadding(10, 10, 10, 10);
        view.setBackgroundColor(Color.rgb(180, 180, 180));
        view.setTextColor(Color.BLACK);
        view.setText(text);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    /**
     * Checks for null text views
     *
     * @param textViews textViews to validate
     * @return isEmpty
     */
    public boolean isEmpty(@NonNull TextView[] textViews) {
        boolean isEmpty = false;
        for (TextView textView : textViews) {
            if (textView.getText().length() == 0) {
                textView.setError("Cannot be null");
                textView.setHintTextColor(Color.RED);
                isEmpty = true;
            } else {
                textView.setHintTextColor(Color.GRAY);
            }
        }
        return isEmpty;
    }

    /**
     * Creates a snack bar but does not show it
     *
     * @param view view to bind on
     * @param text the text to show
     * @return snackBar
     */
    public Snackbar createSnackBar(View view, String text) {
        return createSnackBar(view, text, Snackbar.LENGTH_SHORT);
    }

    /**
     * Creates a snack bar but does not show it
     *
     * @param view   view to bind on
     * @param text   the text to show
     * @param length duration it will be shown
     * @return snackBar
     */
    public Snackbar createSnackBar(View view, String text, int length) {
        return Snackbar.make(view, text, length);
    }

    /**
     * Creates a snack bar and shows it
     *
     * @param view view to bind on
     * @param text the text to show
     * @return
     */
    public void showSnackBar(View view, String text) {
        showSnackBar(view, text, Snackbar.LENGTH_SHORT);
    }

    /**
     * Creates a snack bar and shows it
     *
     * @param view   view to bind on
     * @param text   the text to show
     * @param length duration it will be shown
     */
    public void showSnackBar(View view, String text, int length) {
        Snackbar.make(view, text, length).show();
    }

    /**
     * Creates a snack bar and shows it
     *
     * @param view         view to bind on
     * @param text         the text to show
     * @param length       duration it will be shown
     * @param actionText   text of the action
     * @param actionIntent the intent sent when the action text is clicked
     */
    public void showSnackBar(View view, String text, int length, String actionText, final Intent actionIntent) {
        Snackbar.make(view, text, length).setAction(actionText, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(actionIntent);
            }
        }).show();
    }

    /**
     * Creates a snack bar and shows it
     *
     * @param view         view to bind on
     * @param text         the text to show
     * @param length       duration it will be shown
     * @param actionText   text of the action
     * @param actionIntent the intent sent when the action text is clicked
     */
    public Snackbar createSnackBar(View view, String text, int length, String actionText, final Intent actionIntent) {
        Snackbar snackBar = Snackbar.make(view, text, length).setAction(actionText, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(actionIntent);
            }
        });
        snackBar.show();
        return snackBar;
    }

    /**
     * Creates a snack bar and shows it
     * <p>
     * Nothing happens when the action of the snack bar is clicked
     *
     * @param view       view to bind on
     * @param text       the text to show
     * @param actionText action text
     */
    public void showSnackBar(View view, String text, String actionText) {
        Snackbar snackBar = createSnackBar(view, text);
        snackBar.setAction(actionText, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    /**
     * Empties textViews
     *
     * @param textViews textViews to empty
     * @return emptied
     */
    public boolean resetTextViews(@NonNull TextView[] textViews) {
        boolean emptied = false;
        for (TextView textView : textViews) {
            if (!textView.getText().equals("")) {
                textView.setText("");
                emptied = true;
            }
        }
        return emptied;
    }

    /**
     * Hides the keyboard
     *
     * @param binder Supplies the identifying token given to an input method
     *               when it was started, which allows it to perform this operation on
     *               itself.
     */
    public void hideSoftInputFromInputMethod(View binder) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(binder.getWindowToken(), InputMethodManager.RESULT_HIDDEN);
    }

    /**
     * Hides the keyboard
     *
     * @param binder The token of the window that is making the request,
     */
    public void hideSoftInputFromWindow(View binder) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(binder.getApplicationWindowToken(), InputMethodManager.RESULT_HIDDEN);
    }

    /**
     * Show soft input method on a view
     *
     * @param view The currently focused view, which would like to receive
     *             soft keyboard input.
     */
    public void showSoftInput(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Show the input method's soft input area, so the user
     * sees the input method window and can interact with it.
     * This can only be called from the currently active input method,
     * as validated by the given token.
     *
     * @param view View that supplies the identifying token given to an input method
     *             when it was started, which allows it to perform this operation on
     *             itself.
     */
    public void showSoftInputFromInputMethod(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInputFromInputMethod(view.getApplicationWindowToken(), InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Returns a drawable resource
     *
     * @param resId drawable id
     * @return drawable
     */
    public Drawable getDrawable(int resId) {
        return getContext().getResources().getDrawable(resId);
    }

    /**
     * Creates an alert dialog
     *
     * @param title
     * @param message
     * @param positiveButtonText
     * @param positiveIntent
     * @param neutralButtonText
     * @param neutralIntent
     */
    public void createAlertDialog(String title, String message, String positiveButtonText, final Intent positiveIntent,
                                  String neutralButtonText, final Intent neutralIntent) {

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getContext().sendBroadcast(positiveIntent);
            }
        });
        alert.setNeutralButton(neutralButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getContext().sendBroadcast(neutralIntent);
            }
        });

        alert.show();
    }

    /**
     * Should be used to show a dialog when the device is not connected to the internet.
     * The positive action directs the user to wireless settings
     *
     * @param message
     */
    public void showNetworkNotConnectedDialog(final String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Network error");
        alert.setMessage(message);
        alert.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getContext().startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        alert.setNeutralButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            }
        });
        alert.setCancelable(false);
        alert.show();
    }

    /**
     * Get a bitmap
     * If the bitmap cannot be loaded due to a out of memory error, a null is returned
     *
     * @param drawableResId id of the drawable
     * @return bitmap
     */
    @Nullable
    public Bitmap getBitmap(int drawableResId) {
        try {
            return BitmapFactory.decodeResource(getContext().getResources(), drawableResId);
        } catch (OutOfMemoryError e) {
            System.gc();
            Runtime.getRuntime().gc();
            return null;
        }

    }

    /**
     * Get a string in a text view
     *
     * @param textView
     * @return
     */
    @NonNull
    public String getString(@NonNull TextView textView) {
        return textView.getText().toString();
    }

    /**
     * Shows a custom toast
     *
     * @param text
     * @param duration
     */
    public static void toast(Context context, String text, int duration) {

        Toast toast = new Toast(context);
        TextView view = new TextView(context);
        view.setPadding(10, 10, 10, 10);
        view.setBackgroundColor(Color.rgb(180, 180, 180));
        view.setTextColor(Color.BLACK);
        view.setText(text);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }

    /**
     * Checks for null text views
     *
     * @param textViews
     * @return
     */
    public static boolean isEmpty(@NonNull TextView[] textViews, boolean showError) {
        boolean isEmpty = false;
        for (TextView textView : textViews) {
            if (textView.getText().length() == 0) {
                if (showError) {
                    textView.setError("Cannot be null");
                    textView.setHintTextColor(Color.RED);
                }
                isEmpty = true;
            } else {
                textView.setHintTextColor(Color.GRAY);
            }
        }
        return isEmpty;
    }

    /**
     * Hides views
     *
     * @param views
     */
    public static void hideViews(@NonNull View[] views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Shows views
     *
     * @param views
     */
    public void showViews(@NonNull View[] views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Reset any Views that extend text view
     *
     * @param t
     * @param <T>
     */
    public <T extends TextView> void resetInputs(@NonNull T[] t, String defaultValue) {
        for (T v : t) {
            v.setText(defaultValue);
        }
    }

    /**
     * Loads images from file system
     *
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    public Drawable getDrawableFromStream(String filePath) throws FileNotFoundException {

        if (filePath == null) {
            return null;
        }
        return Drawable.createFromStream(new FileInputStream(new File(filePath)), filePath);
    }

    /**
     * Get bitmap from file system
     *
     * @param fileUri
     * @return
     */
    public Bitmap getFileBitmap(String fileUri, @IntegerRes int defaultIcon) {


        if (fileUri == null) {
            return null;
        }

        Bitmap bitmap = null;
        try {
            return bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(fileUri)));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap = BitmapFactory.decodeResource(getContext().getResources(), defaultIcon);
    }

    /**
     * Show simple snack bar with  message and action text
     *
     * @param view
     * @param text
     * @param actionText
     * @param action
     */
    public void showSimpleSnackBar(final View view, String text, String actionText, @NonNull final Intent action) {
        Snackbar snackbar =
                Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        if (actionText != null && action != null) {
            snackbar.setAction(actionText, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (action != null) {
                        view.getContext().sendBroadcast(action);
                    }
                }
            });
        }

        snackbar.show();
    }

    /**
     * Show progress dialog
     *
     * @param title
     * @param message
     * @param cancelable
     * @return
     */
    public ProgressDialog showCustomProgressDialog(Context context, String title, String message, boolean cancelable) {
        ProgressDialog mDialog = new ProgressDialog(context);
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(cancelable);
        mDialog.show();
        return mDialog;
    }

}
