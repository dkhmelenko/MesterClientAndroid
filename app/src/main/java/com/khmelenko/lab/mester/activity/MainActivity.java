package com.khmelenko.lab.mester.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.khmelenko.lab.mester.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

/**
 * Main application activity
 *
 * @author Dmytro Khmelenko
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends ActionBarActivity {

    @AfterViews
    protected void init() {
        // TODO Do initialization
    }

    @OptionsItem(R.id.action_settings)
    protected void handleActionSettings() {
        Toast.makeText(this, "SETTINGS", Toast.LENGTH_LONG).show();
    }


}
