package com.marvik.libs.android.fragments;

import android.app.SharedElementCallback;
import android.content.res.Configuration;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * BasicFragment
 * A basic fragment but very powerful
 * Created by victor on 4/8/2016.
 */
public abstract class BasicFragment extends MasterFragment {

    /**
     * Called when a fragment loads an animation.
     *
     * @param transit
     * @param enter
     * @param nextAnim
     */
    @Override
    protected void onFragmentCreateAnimator(int transit, boolean enter, int nextAnim) {

    }

    /**
     * When custom transitions are used with Fragments, the enter transition callback
     * is called when this Fragment is attached or detached when not popping the back stack.
     *
     * @param callback Used to manipulate the shared element transitions on this Fragment
     *                 when added not as a pop from the back stack.
     */
    @Override
    protected void onSetFragmentEnterSharedElementCallback(SharedElementCallback callback) {

    }

    /**
     * This hook is called whenever the options menu is being closed (either by the user canceling
     * the menu with the back/menu button, or when an item is selected).
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     */
    @Override
    protected void onFragmentOptionsMenuClosed(Menu menu) {

    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     * <p>
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    protected void onFragmentOptionsItemSelected(MenuItem item) {

    }

    /**
     * Prepare the Screen's standard options menu to be displayed.  This is
     * called right before the menu is shown, every time it is shown.  You can
     * use this method to efficiently enable/disable items or otherwise
     * dynamically modify the contents.  See
     * {Activity#onPrepareOptionsMenu(Menu) Activity.onPrepareOptionsMenu}
     * for more information.
     *
     * @param menu The options menu as last shown or first initialized by
     *             onCreateOptionsMenu().
     * @see #setHasOptionsMenu
     * @see #onCreateOptionsMenu
     */
    @Override
    protected void onFragmentPrepareOptionsMenu(Menu menu) {

    }

    /**
     * Called when this fragment's option menu items are no longer being
     * included in the overall options menu.  Receiving this call means that
     * the menu needed to be rebuilt, but this fragment's items were not
     * included in the newly built menu (its {#onCreateOptionsMenu(Menu, MenuInflater)}
     * was not called).
     */
    @Override
    protected void onFragmentDestroyOptionsMenu() {

    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.  For this method
     * to be called, you must have first called {#setHasOptionsMenu}.  See
     * {Activity#onCreateOptionsMenu(Menu) Activity.onCreateOptionsMenu}
     * for more information.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater
     * @see #setHasOptionsMenu
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    protected void onFragmentCreateOptionsMenu(Menu menu, MenuInflater inflater) {

    }

    /**
     * Configuration changed
     *
     * @param newConfig
     */
    @Override
    protected void onFragmentConfigurationChanged(Configuration newConfig) {

    }

    /**
     * Sets the Transition that will be used for shared elements transferred back during a
     * pop of the back stack. This Transition acts in the leaving Fragment.
     * Typical Transitions will affect size and location, such as
     * { ChangeBounds}. A null
     * value will cause transferred shared elements to blink to the final position.
     * If no value is set, the default will be to use the same value as
     * {#setSharedElementEnterTransition(Transition)}.
     *
     * @param transition The Transition to use for shared elements transferred out of the content
     *                   Scene.
     * @attr ref android.R.styleable#Fragment_fragmentSharedElementReturnTransition
     */
    @Override
    protected void onSetSharedElementReturnTransition(Transition transition) {

    }

    /**
     * Sets the Transition that will be used for shared elements transferred into the content
     * Scene. Typical Transitions will affect size and location, such as
     * {ChangeBounds}. A null
     * value will cause transferred shared elements to blink to the final position.
     *
     * @param transition The Transition to use for shared elements transferred into the content
     *                   Scene.
     * @attr ref android.R.styleable#Fragment_fragmentSharedElementEnterTransition
     */
    @Override
    protected void onSetSharedElementEnterTransition(Transition transition) {

    }

    /**
     * Sets the Transition that will be used to move Views in to the scene when returning due
     * to popping a back stack. The entering Views will be those that are regular Views
     * or ViewGroups that have {ViewGroup#isTransitionGroup} return true. Typical Transitions
     * will extend {Visibility} as exiting is governed by changing
     * visibility from {View#VISIBLE} to {View#INVISIBLE}. If transition is null,
     * the views will remain unaffected. If nothing is set, the default will be to use the same
     * transition as {#setExitTransition(Transition)}.
     *
     * @param transition The Transition to use to move Views into the scene when reentering from a
     *                   previously-started Activity.
     * @attr ref android.R.styleable#Fragment_fragmentReenterTransition
     */
    @Override
    protected void onSetFragmentReenterTransition(Transition transition) {

    }

    /**
     * Sets the Transition that will be used to move Views out of the scene when the
     * fragment is removed, hidden, or detached when not popping the back stack.
     * The exiting Views will be those that are regular Views or ViewGroups that
     * have {ViewGroup#isTransitionGroup} return true. Typical Transitions will extend
     * { Visibility} as exiting is governed by changing visibility
     * from {View#VISIBLE} to {View#INVISIBLE}. If transition is null, the views will
     * remain unaffected.
     *
     * @param transition The Transition to use to move Views out of the Scene when the Fragment
     *                   is being closed not due to popping the back stack.
     * @attr ref android.R.styleable#Fragment_fragmentExitTransition
     */
    @Override
    protected void onSetFragmentExitTransition(Transition transition) {

    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {#requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {#requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either { PackageManager#PERMISSION_GRANTED}
     *                     or { PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    protected void onFragmentRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

    }

    /**
     * When custom transitions are used with Fragments, the exit transition callback
     * is called when this Fragment is attached or detached when popping the back stack.
     *
     * @param callback Used to manipulate the shared element transitions on this Fragment
     *                 when added as a pop from the back stack.
     */
    @Override
    protected void onSetFragmentExitSharedElementCallback(SharedElementCallback callback) {

    }
}
