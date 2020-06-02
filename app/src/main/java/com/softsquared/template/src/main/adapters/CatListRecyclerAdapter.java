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
import com.softsquared.template.src.main.fragments.FragmentHome;
import com.softsquared.template.src.main.items.CatListItem;

import java.util.ArrayList;

public class CatListRecyclerAdapter extends RecyclerView.Adapter<CatListRecyclerAdapter.ViewHolder> {
    private ArrayList<CatListItem> mList = new ArrayList<>();
    Context mContext;
    FragmentHome fragmentHome;

    public CatListRecyclerAdapter(Context mContext, FragmentHome fragmentHome) {
        this.mContext = mContext;
        this.fragmentHome = fragmentHome;
        mList = new ArrayList<>();
        final String str[] = {
                "음식점", "제과", "약국", "숙박",
                "미용", "의류", "병원", "보험",
                "기타 의료", "문화", "주유", "유통",
                "서적", "학원", "사무통신", "자동차판매",
                "서비스", "여행", "레저", "취미",
                "음료", "위생", "생활", "문구"
        };
        for (int i = 0; i < str.length; i++) {
            CatListItem catListItem = new CatListItem(str[i]);
            this.mList.add(catListItem);
        }
    }

    @Override
    public CatListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.cat_list_item, parent, false);
        CatListRecyclerAdapter.ViewHolder vh = new CatListRecyclerAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CatListRecyclerAdapter.ViewHolder holder, final int position) {
        final CatListItem item = mList.get(position);
        holder.tv_title.setText(item.getTitle());
        holder.rl_cat_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHome.sendCategoryName(item.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        RelativeLayout rl_cat_list;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_cat_list_title);
            rl_cat_list = itemView.findViewById(R.id.rl_cat_list);

        }
    }
}