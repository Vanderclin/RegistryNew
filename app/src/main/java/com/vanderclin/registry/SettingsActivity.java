package com.vanderclin.registry;

import android.content.*;
import android.content.res.*;
import android.os.*;
import android.preference.*;
import android.preference.Preference.*;
import android.support.annotation.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;

public class SettingsActivity extends PreferenceActivity
{

	public static final int RESULT_CODE_THEME_UPDATED = 1;
	private AppCompatDelegate mDelegate;
	private int SETTINGS_ACTION = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		SharedPreferences pref = PreferenceManager
			.getDefaultSharedPreferences(this);
		String themeName = pref.getString("theme", getString(R.string.dark));
		if (themeName.equals(getString(R.string.dark))) {
			setTheme(R.style.dark);
		} else 
		if (themeName.equals(getString(R.string.red))) {
			setTheme(R.style.red);
		}
		if (themeName.equals(getString(R.string.pink))) {
			setTheme(R.style.pink);
		}
		if (themeName.equals(getString(R.string.purple))) {
			setTheme(R.style.purple);
		}
		if (themeName.equals(getString(R.string.deepPurple))) {
			setTheme(R.style.deepPurple);
		}
		if (themeName.equals(getString(R.string.indigo))) {
			setTheme(R.style.indigo);
		}
		if (themeName.equals(getString(R.string.blue))) {
			setTheme(R.style.blue);
		}
		if (themeName.equals(getString(R.string.lightBlue))) {
			setTheme(R.style.lightBlue);
		}
		if (themeName.equals(getString(R.string.cyan))) {
			setTheme(R.style.cyan);
		}
		if (themeName.equals(getString(R.string.teal))) {
			setTheme(R.style.teal);
		}
		if (themeName.equals(getString(R.string.green))) {
			setTheme(R.style.green);
		}
		if (themeName.equals(getString(R.string.lightGreen))) {
			setTheme(R.style.lightGreen);
		}
		if (themeName.equals(getString(R.string.lime))) {
			setTheme(R.style.lime);
		}
		if (themeName.equals(getString(R.string.yellow))) {
			setTheme(R.style.yellow);
		}
		if (themeName.equals(getString(R.string.amber))) {
			setTheme(R.style.amber);
		}
		if (themeName.equals(getString(R.string.orange))) {
			setTheme(R.style.orange);
		}
		if (themeName.equals(getString(R.string.deepOrange))) {
			setTheme(R.style.deepOrange);
		}
		if (themeName.equals(getString(R.string.brown))) {
			setTheme(R.style.brown);
		}
		if (themeName.equals(getString(R.string.grey))) {
			setTheme(R.style.grey);
		}
		if (themeName.equals(getString(R.string.blueGrey))) {
			setTheme(R.style.blueGrey);
		}
		getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		findPreference("theme").setOnPreferenceChangeListener(new RefershActivityOnPreferenceChangeListener(RESULT_CODE_THEME_UPDATED));
	}

    private class RefershActivityOnPreferenceChangeListener implements OnPreferenceChangeListener {
        private final int resultCode;
        public RefershActivityOnPreferenceChangeListener(int resultCode) {
            this.resultCode = resultCode;

		}

        @Override
        public boolean onPreferenceChange(Preference p, Object newValue) {
            setResult(resultCode);
            finish();
			startActivityForResult(new Intent(SettingsActivity.this,SettingsActivity.class), SETTINGS_ACTION);
			return true;

        }
    }
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getDelegate().onPostCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setSupportActionBar(@Nullable Toolbar toolbar) {
        getDelegate().setSupportActionBar(toolbar);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return getDelegate().getMenuInflater();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        getDelegate().onPostResume();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);

        getDelegate().setTitle(title);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        getDelegate().onConfigurationChanged(newConfig);
    }

    @Override
    protected void onStop() {
        super.onStop();

        getDelegate().onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        getDelegate().onDestroy();
    }

    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    private AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = AppCompatDelegate.create(this, null);
        }

        return mDelegate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
