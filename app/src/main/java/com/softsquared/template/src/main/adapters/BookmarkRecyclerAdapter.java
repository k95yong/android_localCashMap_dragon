package com.softsquared.template.src.main.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.softsquared.template.R;
import com.softsquared.template.src.BaseActivity;
import com.softsquared.template.src.main.PreferenceManager;
import com.softsquared.template.src.main.activities.PlaceInfoActivity;
import com.softsquared.template.src.main.fragments.FragmentHome;

import java.util.ArrayList;

public class BookmarkRecyclerAdapter extends RecyclerView.Adapter<BookmarkRecyclerAdapter.SwipeViewHolder> {

    private Context context;
    public ArrayList<BaseActivity.Place> mList = null;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    PreferenceManager preferenceManager = new PreferenceManager();

    public BookmarkRecyclerAdapter(Context context, ArrayList<BaseActivity.Place> list) {
        this.context = context;
        this.mList = list;
        Log.e("mListSize in adapter", " :" + mList.size());
    }

    @NonNull
    @Override
    public SwipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bookmark, viewGroup, false);
        return new SwipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwipeViewHolder swipeViewHolder, final int i) {
        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(swipeViewHolder.swipeRevealLayout, String.valueOf(mList.get(i).getLat() + mList.get(i).getLon()));
        viewBinderHelper.closeLayout(String.valueOf(mList.get(i).getTitle()));
        swipeViewHolder.bindData(mList.get(i));
        BaseActivity.Place item = mList.get(i);


        swipeViewHolder.ll_bookmark_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlaceInfoActivity.class);
                intent.putExtra("title", mList.get(i).getTitle());
                intent.putExtra("address", mList.get(i).getAddress());
                intent.putExtra("tel", mList.get(i).getTel());
                intent.putExtra("lat", mList.get(i).getLat());
                intent.putExtra("lon", mList.get(i).getLon());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if(FragmentHome.mapViewContainer != null){
                    FragmentHome.mapViewContainer.removeAllViews();
                }

                context.startActivity(intent);
            }
        });

        swipeViewHolder.ll_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, mList.size());
                preferenceManager.saveBookMarkList(context, mList);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class SwipeViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_address;
        LinearLayout ll_bookmark_item, ll_delete_button;
        SwipeRevealLayout swipeRevealLayout;


        SwipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_delete_button = itemView.findViewById(R.id.ll_delete_button);
            tv_title = itemView.findViewById(R.id.tv_item_bookmark_title);
            tv_address = itemView.findViewById(R.id.tv_item_bookmark_address);
            ll_bookmark_item = itemView.findViewById(R.id.ll_bookmark_item);
            swipeRevealLayout = itemView.findViewById(R.id.swipelayout);
        }

        void bindData(BaseActivity.Place place) {
            tv_title.setText(place.getTitle());
            tv_address.setText(place.getAddress());
        }
    }
}