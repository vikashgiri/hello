package com.infoicon.bonjob.chatModule.chatMain;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chatModule.CommonMethods;
import com.infoicon.bonjob.chatModule.model.ChatMessage;
import com.infoicon.bonjob.customview.CircleImageView;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.singletons.Singleton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by himanshusoni on 06/09/15.
 */
public class ChatMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = "ChatMessageAdapter";
    private static final int MY_MESSAGE = 0, OTHER_MESSAGE = 1, JOB_DETAILS = 2;
    private List<ChatMessage> mMessages;
    private Context mContext;

    String job_image;
    String job_title;
    String job_desc;

    public ChatMessageAdapter(Context context, List<ChatMessage> data) {
        mContext = context;
        mMessages = data;
        mMessages.add(new ChatMessage("", "", "", "", false, "", ""));
    }

    @Override
    public int getItemCount() {
        return mMessages == null ? 0 : mMessages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return JOB_DETAILS;
        } else {
            ChatMessage item = mMessages.get(position);
            if (item.isMine()) return MY_MESSAGE;
            else return OTHER_MESSAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == JOB_DETAILS) {
            return new FirstRowHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_first_pos_view, parent, false));
        }
        if (viewType == MY_MESSAGE) {
            return new MessageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mine_message, parent, false));
        } else {
            return new MessageHolder(LayoutInflater.from(mContext).inflate(R.layout.item_other_message, parent, false));
        }
    }

    /** add new message here */
    public void add(ChatMessage message) {
        mMessages.add(message);
        notifyItemInserted(mMessages.size() - 1);
    }

    /** add new message here */
    public void addAll(ArrayList<ChatMessage> message) {
        mMessages.addAll(message);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position == 0) {
            ((FirstRowHolder) holder).textViewTitle.setText(job_title);
            ((FirstRowHolder) holder).tvDesc.setText(job_desc);
            if (job_image != null && !job_image.equals("")) {
                ImageLoader.loadImageWithCircle(mContext, job_image, ((FirstRowHolder) holder).imgViewJob);
            }

            if (Singleton.getUserInfoInstance().isLoginRecriter()) {
                ((FirstRowHolder) holder).tvPostMessage.setText(mContext.getString(R.string.candidate_applied_job));
            } else {
                ((FirstRowHolder) holder).tvPostMessage.setText(mContext.getString(R.string.applied_job_offer));
            }

        } else {
            ChatMessage chatMessage = mMessages.get(position);
            ((MessageHolder) holder).tvMessage.setText(chatMessage.getContent());

            if (DateUtils.isToday(CommonMethods.getDateInMillis(chatMessage.getDate()))) {
                String time = chatMessage.getTime();
                ((MessageHolder) holder).tvTime.setText(time);
            } else {
                String date = chatMessage.getDate();
                String time = chatMessage.getTime();
                ((MessageHolder) holder).tvTime.setText(date + " " + time);

            }
        }
    }


    public void addJobDetails(String job_image, String job_title, String job_desc) {
        this.job_image = job_image;
        this.job_title = job_title;
        this.job_desc = job_desc;

    }

    class MessageHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_message) CustomsTextView tvMessage;
        @BindView(R.id.tv_time) CustomsTextView tvTime;

        MessageHolder(@Nullable View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class FirstRowHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.imgViewJob) CircleImageView imgViewJob;
        @BindView(R.id.tvPostMessage) CustomsTextView tvPostMessage;
        @BindView(R.id.textViewTitle) CustomsTextView textViewTitle;
        @BindView(R.id.tvDesc) CustomsTextView tvDesc;

        FirstRowHolder(@Nullable View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgViewJob.setBorderWidth(2);
            imgViewJob.setBorderColor(ContextCompat.getColor(mContext, R.color.border_grey));
        }
    }

}