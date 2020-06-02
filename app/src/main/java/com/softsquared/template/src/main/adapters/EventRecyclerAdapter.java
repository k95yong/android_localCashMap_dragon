package com.softsquared.template.src.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softsquared.template.R;
import com.softsquared.template.src.main.activities.MainNavigationActivity;
import com.softsquared.template.src.main.fragments.FragmentEventContent;
import com.softsquared.template.src.main.models.EventResponse;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    private ArrayList<EventResponse.Result> mList = null;
    Context mContext;
    MainNavigationActivity mainNavigationActivity;

    public EventRecyclerAdapter(Context mContext, ArrayList<EventResponse.Result> mList, MainNavigationActivity mainNavigationActivity) {
        this.mList = mList;
        this.mContext = mContext;
        this.mainNavigationActivity = mainNavigationActivity;
    }

    @Override
    public EventRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.event_item, parent, false);
        EventRecyclerAdapter.ViewHolder vh = new EventRecyclerAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerAdapter.ViewHolder holder, final int position) {
        EventResponse.Result item = mList.get(position);

        holder.tv_title.setText(item.getTitle());
        holder.tv_date.setText(item.getTime());

        holder.rl_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur_event_number = mList.get(position).getNo();
                mainNavigationActivity.transaction = mainNavigationActivity.fragmentManager.beginTransaction();
                mainNavigationActivity.transaction.replace(R.id.main_frame_layout, new FragmentEventContent(mainNavigationActivity, cur_event_number)).commitAllowingStateLoss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_date;
        RelativeLayout rl_event_button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_event_title);
            tv_date = itemView.findViewById(R.id.tv_event_date);
            rl_event_button = itemView.findViewById(R.id.rl_event_button);
        }
    }
}
