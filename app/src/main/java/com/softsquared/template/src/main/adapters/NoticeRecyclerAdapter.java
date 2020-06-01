package com.softsquared.template.src.main.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softsquared.template.R;
import com.softsquared.template.src.main.activities.MainNavigationActivity;
import com.softsquared.template.src.main.fragments.FragmentNoticeContent;
import com.softsquared.template.src.main.models.NoticeResponse;

import java.util.ArrayList;

public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.ViewHolder>{
    private ArrayList<NoticeResponse.Result> mList = null;
    Context mContext;
    MainNavigationActivity mainNavigationActivity;

    public NoticeRecyclerAdapter(Context mContext, ArrayList<NoticeResponse.Result> mList, MainNavigationActivity mainNavigationActivity) {
        this.mList = mList;
        this.mContext = mContext;
        this.mainNavigationActivity = mainNavigationActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.notice_item, parent, false);
        NoticeRecyclerAdapter.ViewHolder vh = new NoticeRecyclerAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        NoticeResponse.Result item = mList.get(position);

        holder.tv_title.setText(item.getTitle());
        holder.tv_date.setText(item.getTime());

        holder.ll_notice_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cur_notice_number = mList.get(position).getNo();
                mainNavigationActivity.transaction = mainNavigationActivity.fragmentManager.beginTransaction();
                mainNavigationActivity.transaction.replace(R.id.main_frame_layout, new FragmentNoticeContent(mainNavigationActivity, cur_notice_number)).commitAllowingStateLoss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title, tv_date;
        LinearLayout ll_notice_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_notice_title);
            tv_date = itemView.findViewById(R.id.tv_notice_date);
            ll_notice_button = itemView.findViewById(R.id.ll_notice_button);
        }
    }
}
