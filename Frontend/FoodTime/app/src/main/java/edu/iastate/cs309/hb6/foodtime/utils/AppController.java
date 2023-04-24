package edu.iastate.cs309.hb6.foodtime.utils;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Controller to invoke FoodTime server API
 */
public class AppController extends Application {

    /** Controller tag used in outputs */
    public static final String TAG = AppController.class
            .getSimpleName();

    /** Volley Request Queue */
    private RequestQueue mRequestQueue;

    /** Singleton instance of the controller */
    private static AppController mInstance;

    /**
     * Extends Android application
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    /**
     * Get the universal controller instance across the app
     *
     * @return an instance of the AppController class
     */
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    /**
     * Get the request queue from Volley to use with adding requests to.
     * If a queue doesn't exist (i.e. the app just launched), create one first.
     *
     * @return a Volley RequestQueue
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    /**
     * Add an HTTP request to the Volley queue
     *
     * @param req - the request object
     * @param tag - (optional) tag to include
     * @param <T> - generic type of request (String, JSONObj, etc.)
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * Add an HTTP request to the Volley queue (without a tag)
     *
     * @param req - the request object
     * @param <T> - generic type of request (String, JSONObj, etc.)
     */
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancel all requests still on the queue
     *
     * @param tag - Delete the requests that go with the tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
