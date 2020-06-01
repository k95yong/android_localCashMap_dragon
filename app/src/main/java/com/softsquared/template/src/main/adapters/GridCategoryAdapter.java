package com.softsquared.template.src.main.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softsquared.template.R;

public class GridCategoryAdapter extends BaseAdapter {
    Context context;
    int layout;
    int img[];
    String str[];
    LayoutInflater inf;

    public GridCategoryAdapter(Context context, int layout, int[] img, String[] str) {
        this.context = context;
        this.layout = layout;
        this.img = img;
        this.str = str;
        inf = (LayoutInflater) context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int position) {
        return img[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null)
            convertView = inf.inflate(layout, null);

        TextView tv = (TextView) convertView.findViewById(R.id.tv_cat_name);
        tv.setText(str[position]);
        ImageView iv = (ImageView) convertView.findViewById(R.id.iv_grid_pic);
        iv.setImageResource(img[position]);
        //Drawable curImg = context.getResources().getDrawable(img[position]);

        //tv.setCompoundDrawablesWithIntrinsicBounds(null, curImg, null, null);;
        return convertView;
    }


}

