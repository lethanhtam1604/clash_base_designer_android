package com.lavamy.clashbasedesigner.adapter.townhall;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lavamy.clashbasedesigner.R;
import com.lavamy.clashbasedesigner.manager.ViewExtras;
import com.lavamy.clashbasedesigner.model.TownHall;

import java.util.ArrayList;

public class TownHallListViewAdapter extends BaseAdapter {

    private ArrayList<TownHall> townHalls;
    private Context context;

    public TownHallListViewAdapter(Context context) {
        this.context = context;
        townHalls = new ArrayList<>();
    }

    public void addTownHall(TownHall townHall) {
        this.townHalls.add(townHall);
    }

    @Override
    public Object getItem(int position) {
        return townHalls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return townHalls.size();
    }

    public void clear() {
        townHalls.clear();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        TownHall townHall = townHalls.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_town_hall, parent, false);
        }

        ImageView iconImgView = (ImageView)convertView.findViewById(R.id.iconImgView);
        iconImgView.setImageDrawable(ViewExtras.getDrawable(context, townHall.getImage()));

        TextView titleLabel = (TextView)convertView.findViewById(R.id.titleLabel);
        titleLabel.setText(townHall.getTitle());
        Typeface type = Typeface.createFromAsset(context.getAssets(),"Font/supercell-magic.ttf");
        titleLabel.setTypeface(type);

        if (townHall.isSelected()) {
            convertView.setBackgroundColor(ViewExtras.getColor(context, R.color.md_orange_700));
        }
        else {
            convertView.setBackgroundColor(ViewExtras.getColor(context, R.color.white));
        }
        return convertView;
    }
}
