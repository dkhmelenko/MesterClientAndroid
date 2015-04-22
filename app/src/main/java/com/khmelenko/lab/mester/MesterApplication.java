package com.khmelenko.lab.mester;

import android.app.Application;

import com.khmelenko.lab.mester.module.NetworkModule;

import dagger.ObjectGraph;

/**
 * Main entry point for the app
 *
 * @author Dmytro Khmelenko
 */
public class MesterApplication extends Application {

    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();

        mObjectGraph = ObjectGraph.create(new NetworkModule());
    }

    /**
     * Gets object graph
     *
     * @param object Object for injection
     */
    public void inject(Object object) {
        mObjectGraph.inject(object);
    }
}
