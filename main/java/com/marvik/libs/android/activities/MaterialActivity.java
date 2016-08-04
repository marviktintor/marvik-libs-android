package com.marvik.libs.android.activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * MaterialActivity
 * An Activity class that extends the AppCompatActivity to provide a material theme
 * Created by victor on 4/8/2016.
 */
public abstract class MaterialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityContentView());
        onActivityCreated(savedInstanceState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        onActivityDestroyed();
    }


    @Override
    protected void onStop() {
        super.onStop();
        onActivityStopped();
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * { #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        super.onResume();
        onActivityResumed();
    }


    /**
     * Called after { #onStop} when the current activity is being
     * re-displayed to the user (the user has navigated back to it).  It will
     * be followed by { #onStart} and then { #onResume}.
     * <p/>
     * <p>For activities that are using raw { Cursor} objects (instead of
     * creating them through
     * { #managedQuery(Uri, String[], String, String[], String)},
     * this is usually the place
     * where the cursor should be requeried (because you had deactivated it in
     * { #onStop}.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onStop
     * @see #onStart
     * @see #onResume
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        onActivityRestarted();
    }


    /**
     * Called when a Fragment is being attached to this activity, immediately
     * after the call to its { Fragment#onAttach Fragment.onAttach()}
     * method and before { Fragment#onCreate Fragment.onCreate()}.
     *
     * @param fragment
     */
    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        onAttachFragmentToActivity(fragment);
    }


    /**
     * Called when the main window associated with the activity has been
     * attached to the window manager.
     * See { View onAttachedToWindow() View.onAttachedToWindow()}
     * for more information.
     *
     * @see View#onAttachedToWindow
     */
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        onActivityMainWindowAttachedToWindowManager();
    }


    /**
     * Called when the main window associated with the activity has been
     * detached from the window manager.
     * See { View#onDetachedFromWindow() View.onDetachedFromWindow()}
     * for more information.
     *
     * @see View#onDetachedFromWindow
     */
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onActivityMainWindowDetachedFromWindowManager();
    }


    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        onActivityPaused();
    }


    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        super.onStart();
        onActivityStart();
    }


    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     * <p/>
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * { #onPrepareOptionsMenu}.
     * <p/>
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the { Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     * <p/>
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     * <p/>
     * <p>When you add items to the menu, you can implement the Activity's
     * { #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        onActivityCreateOptionsMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Prepare the Screen's standard options menu to be displayed.  This is
     * called right before the menu is shown, every time it is shown.  You can
     * use this method to efficiently enable/disable items or otherwise
     * dynamically modify the contents.
     * <p/>
     * <p>The default implementation updates the system menu items based on the
     * activity's state.  Deriving classes should always call through to the
     * base class implementation.
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        onActivityPrepareOptionsMenu(menu);
        return super.onPrepareOptionsMenu(menu);
    }


    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p/>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onActivityOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }


    /**
     * This method is called whenever the user chooses to navigate Up within your application's
     * activity hierarchy from the action bar.
     * <p/>
     * <p>If the attribute { android.R.attr#parentActivityName parentActivityName}
     * was specified in the manifest for this activity or an activity-alias to it,
     * default Up navigation will be handled automatically. If any activity
     * along the parent chain requires extra Intent arguments, the Activity subclass
     * should override the method { onPrepareNavigateUpTaskStack(TaskStackBuilder)}
     * to supply those arguments.</p>
     * <p/>
     * <p>See <a href="{@docRoot}guide/topics/fundamentals/tasks-and-back-stack.html">Tasks and Back Stack</a>
     * from the developer guide and <a href="{@docRoot}design/patterns/navigation.html">Navigation</a>
     * from the design guide for more information about navigating within your app.</p>
     * <p/>
     * <p>See the { TaskStackBuilder} class and the Activity methods
     * { #getParentActivityIntent()}, { #shouldUpRecreateTask(Intent)}, and
     * { #navigateUpTo(Intent)} for help implementing custom Up navigation.
     * The AppNavigation sample application in the Android SDK is also available for reference.</p>
     *
     * @return true if Up navigation completed successfully and this Activity was finished,
     * false otherwise.
     */
    @Override
    public boolean onNavigateUp() {
        onActivityNavigateUp();
        return super.onNavigateUp();
    }


    /**
     * This is called when a child activity of this one attempts to navigate up.
     * The default implementation simply calls onNavigateUp() on this activity (the parent).
     *
     * @param child The activity making the call.
     */
    @Override
    public boolean onNavigateUpFromChild(Activity child) {
        onActivityNavigateUpFromChild(child);
        return super.onNavigateUpFromChild(child);
    }


    /**
     * This hook is called whenever the options menu is being closed (either by the user canceling
     * the menu with the back/menu button, or when an item is selected).
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     */
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
        onActivityOptionsMenuClosed(menu);
    }


    /**
     * Called when a fragment is attached to the activity.
     *
     * @param fragment
     */
    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
        super.onAttachFragment(fragment);
        onFragmentAttachedToActivity(fragment);
    }


    /**
     * Modifies the standard behavior to allow results to be delivered to fragments.
     * This imposes a restriction that requestCode be <= 0xffff.
     *
     * @param intent
     * @param requestCode
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        onStartActivityForResult(intent, requestCode);
    }


    /**
     * Called by Fragment.startActivityForResult() to implement its behavior.
     *
     * @param fragment
     * @param intent
     * @param requestCode
     */
    @Override
    public void startActivityFromFragment(android.support.v4.app.Fragment fragment, Intent intent, int requestCode) {
        super.startActivityFromFragment(fragment, intent, requestCode);
        onStartActivityFromFragment(fragment, intent, requestCode);
    }


    /**
     * Called by Fragment.startActivityForResult() to implement its behavior.
     *
     * @param fragment
     * @param intent
     * @param requestCode
     * @param options
     */
    @Override
    public void startActivityFromFragment(android.support.v4.app.Fragment fragment, Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityFromFragment(fragment, intent, requestCode, options);
        onStartActivityFromFragment(fragment, intent, requestCode, options);
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on { #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in { #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either { PackageManager#PERMISSION_GRANTED}
     *                     or { PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onActivityRequestPermissionResult(requestCode, permissions, grantResults);
    }


    /**
     * Dispatch onLowMemory() to all fragments.
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        onActivityLowMemory();
    }


    /**
     * Handle onNewIntent() to inform the fragment manager that the
     * state is not saved.  If you are handling new intents and may be
     * making changes to the fragment state, you want to be sure to call
     * through to the super-class here first.  Otherwise, if your state
     * is saved but the activity is not stopped, you could get an
     * onNewIntent() call which happens before onResume() and trying to
     * perform fragment operations at that point will throw IllegalStateException
     * because the fragment manager thinks the state is still saved.
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        onFragmentNewIntent(intent);
    }


    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityIntentResult(requestCode, resultCode, data);
    }


    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        onActivityBackPressed();
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            finish();
        }
    }


    /**
     * When { ActivityOptions makeSceneTransitionAnimation(Activity,
     * View, String)} was used to start an Activity, <var>callback</var>
     * will be called to handle shared elements on the <i>launched</i> Activity. This requires
     * { Window FEATURE_CONTENT_TRANSITIONS}.
     *
     * @param callback Used to manipulate shared element transitions on the launched Activity.
     */
    @Override
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
        setEnteringSharedElementCallback(callback);
    }


    /**
     * When { ActivityOptions makeSceneTransitionAnimation(Activity,
     * View, String)} was used to start an Activity, <var>listener</var>
     * will be called to handle shared elements on the <i>launching</i> Activity. Most
     * calls will only come when returning from the started Activity.
     *
     * @param listener Used to manipulate shared element transitions on the launching Activity.
     */
    @Override
    public void setExitSharedElementCallback(SharedElementCallback listener) {
        super.setExitSharedElementCallback(listener);
        setExitingSharedElementCallback(listener);
    }


    /**
     * {@inheritDoc}
     * <p/>
     * <p>Please note: AppCompat uses it's own feature id for the action bar:
     *
     * @param featureId
     * @param menu
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        onActivityMenuOpened(featureId, menu);
        return super.onMenuOpened(featureId, menu);
    }

    /**
     * Returns the id of the parent container layout associated with this activity
     *
     * @return layout resid
     */
    protected abstract int getActivityContentView();

    /**
     * Returns the id of the parent container where the inflated fragments are attached
     *
     * @return
     */
    protected abstract int getParentContainerId();

    /**
     * Perform initialization of all fragments and loaders.
     */
    protected abstract void onActivityCreated(Bundle savedInstanceState);

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    protected abstract void onActivityStart();

    /**
     * Method called on the onResume of an Activity to get the name of this activity
     *
     * @return activityTitle
     */
    protected abstract String getActivityTitle();

    /**
     * Called after { #onStop} when the current activity is being
     * re-displayed to the user (the user has navigated back to it).  It will
     * be followed by { #onStart} and then { #onResume}.
     * <p/>
     * <p>For activities that are using raw { Cursor} objects (instead of
     * creating them through
     * { #managedQuery(Uri, String[], String, String[], String)},
     * this is usually the place
     * where the cursor should be requeried (because you had deactivated it in
     * { #onStop}.
     * <p/>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onStop
     * @see #onStart
     * @see #onResume
     */
    protected abstract void onActivityRestarted();

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * { #onResumeFragments()}.
     */
    protected abstract void onActivityResumed();

    /**
     * Gets the name of this activity and sets it as the title of the activity
     *
     * @return activityTitle
     */
    //protected abstract String getActivityTitle();

    /**
     * Called when a fragment is attached to the activity.
     *
     * @param fragment
     */
    protected abstract void onFragmentAttachedToActivity(android.support.v4.app.Fragment fragment);

    /**
     * When { ActivityOptions makeSceneTransitionAnimation(Activity,
     * View, String)} was used to start an Activity, <var>callback</var>
     * will be called to handle shared elements on the <i>launched</i> Activity. This requires
     * { Window FEATURE_CONTENT_TRANSITIONS}.
     *
     * @param callback Used to manipulate shared element transitions on the launched Activity.
     */
    protected abstract void setEnteringSharedElementCallback(SharedElementCallback callback);

    /**
     * Called when the main window associated with the activity has been
     * attached to the window manager.
     * See { View#onAttachedToWindow() View.onAttachedToWindow()}
     * for more information.
     *
     * @see View#onAttachedToWindow
     */
    protected abstract void onActivityMainWindowAttachedToWindowManager();

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected abstract void onActivityIntentResult(int requestCode, int resultCode, Intent data);

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on { #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in { #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either { PackageManager#PERMISSION_GRANTED}
     *                     or { PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    protected abstract void onActivityRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults);

    /**
     * Handle onNewIntent() to inform the fragment manager that the
     * state is not saved.  If you are handling new intents and may be
     * making changes to the fragment state, you want to be sure to call
     * through to the super-class here first.  Otherwise, if your state
     * is saved but the activity is not stopped, you could get an
     * onNewIntent() call which happens before onResume() and trying to
     * perform fragment operations at that point will throw IllegalStateException
     * because the fragment manager thinks the state is still saved.
     *
     * @param intent
     */
    protected abstract void onFragmentNewIntent(Intent intent);

    /**
     * This is called when a child activity of this one attempts to navigate up.
     * The default implementation simply calls onNavigateUp() on this activity (the parent).
     *
     * @param child The activity making the call.
     */
    protected abstract void onActivityNavigateUpFromChild(Activity child);

    /**
     * This method is called whenever the user chooses to navigate Up within your application's
     * activity hierarchy from the action bar.
     * <p/>
     * <p>If the attribute { android.R.attr#parentActivityName parentActivityName}
     * was specified in the manifest for this activity or an activity-alias to it,
     * default Up navigation will be handled automatically. If any activity
     * along the parent chain requires extra Intent arguments, the Activity subclass
     * should override the method { onPrepareNavigateUpTaskStack(TaskStackBuilder)}
     * to supply those arguments.</p>
     * <p/>
     * <p>See <a href="{@docRoot}guide/topics/fundamentals/tasks-and-back-stack.html">Tasks and Back Stack</a>
     * from the developer guide and <a href="{@docRoot}design/patterns/navigation.html">Navigation</a>
     * from the design guide for more information about navigating within your app.</p>
     * <p/>
     * <p>See the { TaskStackBuilder} class and the Activity methods
     * { #getParentActivityIntent()}, { #shouldUpRecreateTask(Intent)}, and
     * { #navigateUpTo(Intent)} for help implementing custom Up navigation.
     * The AppNavigation sample application in the Android SDK is also available for reference.</p>
     *
     * @return true if Up navigation completed successfully and this Activity was finished,
     * false otherwise.
     */
    protected abstract void onActivityNavigateUp();

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     * <p/>
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * { #onPrepareOptionsMenu}.
     * <p/>
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the { Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     * <p/>
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     * <p/>
     * <p>When you add items to the menu, you can implement the Activity's
     * { #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    protected abstract void onActivityCreateOptionsMenu(Menu menu);

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p/>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    protected abstract void onActivityOptionsItemSelected(MenuItem item);

    /**
     * Prepare the Screen's standard options menu to be displayed.  This is
     * called right before the menu is shown, every time it is shown.  You can
     * use this method to efficiently enable/disable items or otherwise
     * dynamically modify the contents.
     * <p/>
     * <p>The default implementation updates the system menu items based on the
     * activity's state.  Deriving classes should always call through to the
     * base class implementation.
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onCreateOptionsMenu
     */
    protected abstract void onActivityPrepareOptionsMenu(Menu menu);

    /**
     * {@inheritDoc}
     * <p/>
     * <p>Please note: AppCompat uses it's own feature id for the action bar:
     *
     * @param featureId
     * @param menu
     */
    protected abstract void onActivityMenuOpened(int featureId, Menu menu);

    /**
     * This hook is called whenever the options menu is being closed (either by the user canceling
     * the menu with the back/menu button, or when an item is selected).
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     */
    protected abstract void onActivityOptionsMenuClosed(Menu menu);

    /**
     * Called when a Fragment is being attached to this activity, immediately
     * after the call to its { Fragment#onAttach Fragment.onAttach()}
     * method and before { Fragment#onCreate Fragment.onCreate()}.
     *
     * @param fragment
     */
    protected abstract void onAttachFragmentToActivity(Fragment fragment);

    /**
     * Modifies the standard behavior to allow results to be delivered to fragments.
     * This imposes a restriction that requestCode be <= 0xffff.
     *
     * @param intent
     * @param requestCode
     */
    protected abstract void onStartActivityForResult(Intent intent, int requestCode);

    /**
     * Called by Fragment.startActivityForResult() to implement its behavior.
     *
     * @param fragment
     * @param intent
     * @param requestCode
     */
    protected abstract void onStartActivityFromFragment(android.support.v4.app.Fragment fragment, Intent intent, int requestCode);

    /**
     * Called by Fragment.startActivityForResult() to implement its behavior.
     *
     * @param fragment
     * @param intent
     * @param requestCode
     * @param options
     */
    protected abstract void onStartActivityFromFragment(android.support.v4.app.Fragment fragment, Intent intent, int requestCode, Bundle options);

    /**
     * When { ActivityOptions makeSceneTransitionAnimation(Activity,
     * View, String)} was used to start an Activity, <var>listener</var>
     * will be called to handle shared elements on the <i>launching</i> Activity. Most
     * calls will only come when returning from the started Activity.
     *
     * @param listener Used to manipulate shared element transitions on the launching Activity.
     */
    protected abstract void setExitingSharedElementCallback(SharedElementCallback listener);

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    protected abstract void onActivityBackPressed();

    /**
     * Dispatch onPause() to fragments.
     */
    protected abstract void onActivityPaused();

    /**
     * Dispatch onStop() to all fragments.  Ensure all loaders are stopped.
     */
    protected abstract void onActivityStopped();

    /**
     * Dispatch onLowMemory() to all fragments.
     */
    protected abstract void onActivityLowMemory();

    /**
     * Called when the main window associated with the activity has been
     * detached from the window manager.
     * See { View#onDetachedFromWindow() View.onDetachedFromWindow()}
     * for more information.
     *
     * @see View#onDetachedFromWindow
     */
    protected abstract void onActivityMainWindowDetachedFromWindowManager();

    /**
     * Destroy all fragments and loaders.
     */
    protected abstract void onActivityDestroyed();

    /**
     * Attaches a fragment to the parent container
     *
     * @param fragment       to attach
     * @param addToBackStack
     * @return backStackEntryCount
     */
    public int attachFragment(Fragment fragment, boolean addToBackStack) {
        if (addToBackStack)
            getFragmentManager().beginTransaction().replace(getParentContainerId(), fragment)
                    .addToBackStack(fragment.getClass().getCanonicalName())
                    .commit();
        else
            getFragmentManager().beginTransaction().replace(getParentContainerId(), fragment).commit();

        return getFragmentManager().getBackStackEntryCount();
    }

    /**
     * Attaches a fragment to the parent container
     *
     * @param fragment to attach
     * @return backStackEntryCount
     */
    public int attachFragment(Fragment fragment) {
        return attachFragment(fragment, true);
    }

    /**
     * Sets the title of this activity
     *
     * @param activityTitle the title
     */
    public void setActivityTitle(String activityTitle) {
        if (activityTitle == null) {
            setTitle(getString(com.marvik.libs.android.R.string.app_name));
        } else setTitle(activityTitle);
    }
}

