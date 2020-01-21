package com.infoicon.bonjob.chatModule.chatList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.customview.CircleImageView;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeRecruiterActivity;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.chatModule.chatMain.ChatFragment;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.response.GetChatListResponse.DataBean;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.Keys;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by infoicona on 18/3/17.
 */

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder>
{

    private FragmentActivity context;
    private List<DataBean> beanArrayList;

    public ChatListAdapter(FragmentActivity activity)
    {
        this.context = activity;
        beanArrayList = new ArrayList<>();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_chat_list, null);
        return new MyViewHolder(view);
    }

    public List<DataBean> getChatIdList() {
        return beanArrayList;
    }

    /** add user to chat list */
    public void addChatUser(DataBean data)
    {
        beanArrayList.add(data);
        if (!Singleton.getDbHelper().isTableExists(Singleton.getUserInfoInstance().getChatId() + "" + Keys.BONJOB_ + data.getUser_id())) {
            Singleton.getDbHelper().createTableUser(Singleton.getUserInfoInstance().getChatId(), Keys.BONJOB_ + data.getUser_id());
            context.sendBroadcast(new Intent(Keys.ADD_TABLE));
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textViewAccountHolderName.setText(beanArrayList.get(position).getFirst_name() + " " +
                beanArrayList.get(position).getLast_name());
        holder.textViewTitle.setText(beanArrayList.get(position).getJob_title());

        holder.tvDateTime.setText(Singleton.getDbHelper().getLastDate(Singleton.getUserInfoInstance().getChatId(), Keys.BONJOB_ + beanArrayList.get(position).getUser_id()) + " " +
                Singleton.getDbHelper().getLastTime(Singleton.getUserInfoInstance().getChatId(), Keys.BONJOB_ + beanArrayList.get(position).getUser_id()));

        int count = Singleton.getDbHelper().getUnreadCount(Singleton.getUserInfoInstance().getChatId(), Keys.BONJOB_ + beanArrayList.get(position).getUser_id());
        if (count > 0) {
            holder.textViewUnreadCount.setVisibility(View.VISIBLE);
            holder.textViewUnreadCount.setText(String.valueOf(count));
        } else {
            holder.textViewUnreadCount.setVisibility(View.GONE);
        }
        //https://bonjob.co//public/uploads/user_pic/
        if (!beanArrayList.get(position).getUser_pic().equals("") &&
                !beanArrayList.get(position).getUser_pic().equalsIgnoreCase(
                        "https://bonjob.co//public/uploads/user_pic/")) {
           ImageLoader.loadImageWithCircle(context, beanArrayList.get(position).getUser_pic(), holder.imgViewCandidate);
        } else holder.imgViewCandidate.setImageResource(R.drawable.default_photo_deactive);

        holder.rlChatDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatFragment chatFragment = new ChatFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Keys.RECEIVER_ID, Keys.BONJOB_ + beanArrayList.get(position).getUser_id());
                bundle.putString(Keys.USER_ID, beanArrayList.get(position).getUser_id());
                bundle.putString(Keys.NAME, beanArrayList.get(position).getFirst_name() + " " + beanArrayList.get(position).getLast_name());
                bundle.putString(Keys.USER_PIC, beanArrayList.get(position).getUser_pic());
                bundle.putString(Keys.JOB_TITLE, beanArrayList.get(position).getJob_title());
                bundle.putString(Keys.JOB_IMAGE, beanArrayList.get(position).getJob_image());
                bundle.putString(Keys.DESCRIPTION, beanArrayList.get(position).getJob_description());
                chatFragment.setArguments(bundle);
                if (Singleton.getUserInfoInstance().isLoginRecriter() || Singleton.getUserInfoInstance().getLoginAdmin()) {
                    ((HomeRecruiterActivity) context).addFragment(chatFragment, bundle, Keys.CHAT, false, true);
                } else {
                    ((HomeSeekerActivity) context).addFragment(chatFragment, bundle, Keys.CHAT,
                            false, true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanArrayList.size();
    }

    public void clearArray(){
        if(beanArrayList!=null && beanArrayList.size()>0){
            beanArrayList.clear();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewTitle) CustomsTextViewBold textViewTitle;
        @BindView(R.id.tvDateTime) CustomsTextView tvDateTime;
        @BindView(R.id.textViewAccountHolderName) CustomsTextView textViewAccountHolderName;
        @BindView(R.id.textViewUnreadCount) CustomsTextView textViewUnreadCount;
        @BindView(R.id.rlChatDetails) RelativeLayout rlChatDetails;
        @BindView(R.id.imgViewJob) CircleImageView imgViewCandidate;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgViewCandidate.setBorderWidth(2);
            imgViewCandidate.setBorderColor(ContextCompat.getColor(context, R.color.border_grey));
        }
    }
}
