package com.marvik.libs.android.views.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
     * <p/>
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

}
