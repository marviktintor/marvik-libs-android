package com.marvik.libs.android.intents;

/**
 * Intents
 */
public class Intents {

    /**
     * Actions
     */
    public class Actions {
        /**
         * Action to launch the android camera app and let it crop the capture pic
         */
        public static final String ACTION_CROP_PICTURE = "";
    }

    /**
     * Broadcasts
     */
    public class Broadcasts {
    }

    /**
     * Request codes
     */
    public class RequestCodes {
        /**
         * Request code to pick an image
         */
        public static final int REQUESTS_PICK_IMAGE = 1 << 0;
        /**
         * Request code to crop a picture
         */
        public static final int REQUEST_CODE_CROP_PICTURE = 1 << 1;
    }


}
