package com.gysttodomanager.gysttodomanager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Yates on 4/18/2014.
 */
public class AppInfoFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_info_fragment, container, false);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Stuff Happens?
                onAppInfoClickListener mCallback = (onAppInfoClickListener) getActivity();
                mCallback.onAppInfoClick();
            }
        });
        return rootView;
    }

    public interface onAppInfoClickListener {
        public void onAppInfoClick();
    }
}
