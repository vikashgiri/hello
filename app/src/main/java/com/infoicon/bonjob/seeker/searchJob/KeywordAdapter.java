package com.infoicon.bonjob.seeker.searchJob;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CustomCheckedTextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicon on 9/6/17.
 */

class KeywordAdapter extends RecyclerView.Adapter<KeywordAdapter.MyViewHolder> {

    private ArrayList<String> keywordList;
    private SearchJobFragment searchJobFragment;

    public KeywordAdapter( SearchJobFragment searchJobFragment) {
        this.searchJobFragment = searchJobFragment;
        keywordList = new ArrayList<>();
    }

    public void addKeyword(String keyword) {
        Collections.reverse(keywordList);
        if (!keywordList.contains(keyword)) {
            keywordList.add(keyword);
            Collections.reverse(keywordList);
            notifyDataSetChanged();
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_keyword, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.btnKeyword.setText(keywordList.get(position));
        holder.btnKeyword.setOnClickListener(v -> searchJobFragment.setTextToEdSearch(keywordList.get(position)));
        holder.imageViewCancel.setOnClickListener(v -> {
            keywordList.remove(position);
            //notifyDataSetChanged();
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        });

    }

    @Override
    public int getItemCount() {
        if (keywordList.size() > 10) {
            return 10;
        } else {
            return keywordList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.btnKeyword) CustomCheckedTextView btnKeyword;
        @BindView(R.id.imageViewCancel) ImageView imageViewCancel;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
