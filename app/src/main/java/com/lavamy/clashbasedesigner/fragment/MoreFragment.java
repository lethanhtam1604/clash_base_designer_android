package com.lavamy.clashbasedesigner.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lavamy.clashbasedesigner.R;

public class MoreFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        initToolBar();
        initView(view);

        return view;
    }

    private void initToolBar() {
        Toolbar toolbar_main = (Toolbar) getActivity().findViewById(R.id.toolbar_main);
        TextView title_text = (TextView) toolbar_main.findViewById(R.id.title_text);
        title_text.setText(getString(R.string.more));

        ImageView updateBtn = (ImageView) toolbar_main.findViewById(R.id.updateBtn);
        updateBtn.setVisibility(View.GONE);
    }

    private void initView(View view) {


    }
}