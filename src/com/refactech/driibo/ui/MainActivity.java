package com.refactech.driibo.ui;

import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.refactech.driibo.R;
import com.refactech.driibo.type.dribble.Category;
import com.refactech.driibo.ui.fragment.DrawerFragment;
import com.refactech.driibo.ui.fragment.ShotsFragment;

public class MainActivity extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private PullToRefreshAttacher mPullToRefreshAttacher;
	private ShotsFragment mShotsFragment;
	private Category mCategory;
	private Menu mMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		mDrawerLayout.setScrimColor(Color.argb(100, 0, 0, 0));
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setIcon(R.drawable.ic_actionbar);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				mMenu.findItem(R.id.action_refresh).setVisible(true);
			}

			public void onDrawerOpened(View drawerView) {
				mMenu.findItem(R.id.action_refresh).setVisible(false);
			}
		};

		PullToRefreshAttacher.Options options = new PullToRefreshAttacher.Options();
		options.headerInAnimation = R.anim.pulldown_fade_in;
		options.headerOutAnimation = R.anim.pulldown_fade_out;
		options.refreshScrollDistance = 0.3f;
		options.headerLayout = R.layout.pulldown_header;
		mPullToRefreshAttacher = new PullToRefreshAttacher(this, options);

		setCategory(Category.popular);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.left_drawer, new DrawerFragment()).commit();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		mMenu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_refresh:
			mShotsFragment.loadFirstPageAndScrollToTop();
			return true;
		case R.id.action_settings:
			startActivity(new Intent(this, PreferenceActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void setCategory(Category category) {
		mDrawerLayout.closeDrawer(GravityCompat.START);
		if (mCategory == category) {
			return;
		}

		mPullToRefreshAttacher.setRefreshing(false);
		mCategory = category;
		mShotsFragment = ShotsFragment.newInstance(category);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, mShotsFragment).commit();
	}

	public PullToRefreshAttacher getPullToRefreshAttacher() {
		return mPullToRefreshAttacher;
	}

}
