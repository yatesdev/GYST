package com.gysttodomanager.gysttodomanager;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Yates on 4/18/2014.
 */

/**
 * This displays the AppInfo Screen the "Credits" so to speak.
 * Very simple. It inflates the XML layout and adds a clickListener to it
 * When clicked, the fragment calls back to the main activity so that the activity can do the
 * fragment transaction to close the credits screen when it is clicked.
 */
public class AppInfoFragment extends Fragment {

    /**
     * Inflates the XML Layout for app_info_fragment
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.app_info_fragment, container, false);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAppInfoClickListener mCallback = (onAppInfoClickListener) getActivity();
                mCallback.onAppInfoClick();
            }
        });
        return rootView;
    }

    /**
     * This is the interface that the activity implements when the click occurs.
     */
    public interface onAppInfoClickListener {
        public void onAppInfoClick();
    }
}
