package com.refactech.driibo.ui.fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.Preference;

import com.refactech.driibo.R;

public class PreferenceFragment extends android.preference.PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);

		// 设置版本号
		Preference versionPreference = findPreference(getString(R.string.pref_key_version));
		PackageInfo packageInfo;
		try {
			packageInfo = getActivity().getPackageManager().getPackageInfo(
					getActivity().getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			versionPreference.setTitle(packageInfo.versionName);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
