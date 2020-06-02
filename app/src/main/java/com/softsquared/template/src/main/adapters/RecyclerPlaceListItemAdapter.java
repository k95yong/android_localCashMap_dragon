package com.softsquared.template.src.main.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softsquared.template.R;
import com.softsquared.template.src.main.activities.PlaceInfoActivity;
import com.softsquared.template.src.main.fragments.FragmentHome;
import com.softsquared.template.src.main.models.SearchResult;

import java.util.ArrayList;

public class RecyclerPlaceListItemAdapter extends RecyclerView.Adapter<RecyclerPlaceListItemAdapter.ViewHolder> {
    private ArrayList<SearchResult> mList = null;
    Context mContext;

    public RecyclerPlaceListItemAdapter(Context mContext, ArrayList<SearchResult> list) {
        this.mContext = mContext;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerPlaceListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflator.inflate(R.layout.place_item, parent, false);
        RecyclerPlaceListItemAdapter.ViewHolder vh = new RecyclerPlaceListItemAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerPlaceListItemAdapter.ViewHolder holder, final int position) {
        SearchResult item = mList.get(position);

        holder.tv_title.setText(item.getMutual_name());
        holder.tv_address.setText(item.getAddress());

        holder.tv_item_distance.setText(String.format("%.2f", item.getDistance()) + "km");

        holder.ll_mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PlaceInfoActivity.class);
                intent.putExtra("title", mList.get(position).getMutual_name());
                intent.putExtra("address", mList.get(position).getAddress());
                intent.putExtra("tel", mList.get(position).getPhone_number());
                intent.putExtra("lat", mList.get(position).getLatitude());
                intent.putExtra("lon", mList.get(position).getLongitude());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                FragmentHome.mapViewContainer.removeAllViews();
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_address, tv_tel, tv_item_distance;
        LinearLayout ll_item_call_to_place;
        LinearLayout ll_mView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_item_distance = itemView.findViewById(R.id.tv_item_distance);
            tv_title = itemView.findViewById(R.id.tv_item_place_title);
            tv_address = itemView.findViewById(R.id.tv_item_place_address);
            tv_tel = itemView.findViewById(R.id.tv_item_place_phone_number);
            ll_item_call_to_place = itemView.findViewById(R.id.ll_item_call_to_place);
            ll_mView = itemView.findViewById(R.id.ll_total_item);
        }

    }

    public void addItem(SearchResult ipt) {
        mList.add(ipt);
    }

}