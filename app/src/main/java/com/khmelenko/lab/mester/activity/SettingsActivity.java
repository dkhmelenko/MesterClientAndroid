package com.khmelenko.lab.mester.activity;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;

import com.khmelenko.lab.mester.R;

import org.androidannotations.annotations.EActivity;

import java.util.List;

/**
 * Settings screen
 *
 * @author Dmytro Khmelenko
 */
public class SettingsActivity extends PreferenceActivity {

    public static final String SERVICE_URL_KEY = "settings_service_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction
                .replace(android.R.id.content, new GeneralSettingsFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.settings_headers, target);
    }

    /**
     * Defines fragment with general settings
     */
    public static class GeneralSettingsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Make sure default values are applied.  In a real app, you would
            // want this in a shared function that is used to retrieve the
            // SharedPreferences wherever they are needed.
//            PreferenceManager.setDefaultValues(getActivity(),
//                    R.xml.fragment_general_settings, false);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.fragment_general_settings);
        }
    }
}
