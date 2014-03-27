package com.refactech.driibo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.refactech.driibo.R;
import com.refactech.driibo.type.dribble.Category;
import com.refactech.driibo.ui.MainActivity;
import com.refactech.driibo.ui.adapter.DrawerAdapter;

public class DrawerFragment extends Fragment {

	private MainActivity mActivity;
	private ListView mListView;
	private DrawerAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mActivity = (MainActivity) getActivity();
		View view = inflater.inflate(R.layout.fragment_drawer, null);
		mListView = (ListView) view.findViewById(R.id.listView);
		mAdapter = new DrawerAdapter(mListView);
		mListView.setAdapter(mAdapter);
		mListView.setItemChecked(0, true);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mListView.setItemChecked(position, true);
				mActivity.setCategory(Category.values()[position]);
			}
		});
		return view;
	}

}
