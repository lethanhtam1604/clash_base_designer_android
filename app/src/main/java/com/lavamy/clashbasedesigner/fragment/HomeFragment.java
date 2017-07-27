package com.lavamy.clashbasedesigner.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lavamy.clashbasedesigner.R;
import com.lavamy.clashbasedesigner.activity.CollectionActivity;
import com.lavamy.clashbasedesigner.manager.ViewExtras;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initToolBar();
        initView(view);

        return view;
    }

    private void initToolBar() {
        Toolbar toolbar_main = (Toolbar) getActivity().findViewById(R.id.toolbar_main);
        TextView title_text = (TextView) toolbar_main.findViewById(R.id.title_text);
        title_text.setText(getString(R.string.clash_base_designer));

        ImageView updateBtn = (ImageView) toolbar_main.findViewById(R.id.updateBtn);
        updateBtn.setImageDrawable(ViewExtras.getDrawable(getContext(), R.drawable.ic_action_update_layout));
        updateBtn.setVisibility(View.VISIBLE);

        RelativeLayout updateRL = (RelativeLayout) toolbar_main.findViewById(R.id.updateRL);
        updateRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initView(View view) {

        TextView ourCollectionTV = (TextView) view.findViewById(R.id.ourCollectionTV);
        TextView brownOurCollectionTV = (TextView) view.findViewById(R.id.brownOurCollectionTV);
        TextView ourLayoutTV = (TextView) view.findViewById(R.id.ourLayoutTV);
        TextView pickLayoutTV = (TextView) view.findViewById(R.id.pickLayoutTV);

        Typeface type1 = Typeface.createFromAsset(getContext().getAssets(),"Font/supercell-magic.ttf");
        ourCollectionTV.setTypeface(type1);
        ourLayoutTV.setTypeface(type1);

        Typeface type2 = Typeface.createFromAsset(getContext().getAssets(),"Font/proxima-nova-light.otf");
        brownOurCollectionTV.setTypeface(type2);
        pickLayoutTV.setTypeface(type2);

        RelativeLayout ourCollectionRL = (RelativeLayout) view.findViewById(R.id.ourCollectionRL);
        ourCollectionRL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CollectionActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout ourLayoutRL = (RelativeLayout) view.findViewById(R.id.ourLayoutRL);
        ourLayoutRL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
    }
}
