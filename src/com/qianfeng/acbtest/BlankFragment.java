package com.qianfeng.acbtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class BlankFragment extends Fragment {

	public BlankFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Bundle argBundle = getArguments();
		int index = 0;
		index = argBundle.getInt("Index", 0);
		View ret = inflater.inflate(R.layout.fragment_blank, container, false);
		TextView txt = (TextView)ret.findViewById(R.id.txtIndex);
		
		txt.setText(Integer.toString(index));
		
		return ret;
	}

}
