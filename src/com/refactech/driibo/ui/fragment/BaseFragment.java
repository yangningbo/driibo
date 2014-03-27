package com.refactech.driibo.ui.fragment;

import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.refactech.driibo.data.RequestManager;

public class BaseFragment extends Fragment {

	@Override
	public void onStop() {
		super.onStop();
		RequestManager.cancelAll(this);
	}

	protected void executeRequest(Request<?> request) {
		RequestManager.addRequest(request, this);
	}

}
