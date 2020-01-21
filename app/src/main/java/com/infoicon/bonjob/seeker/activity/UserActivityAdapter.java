package com.infoicon.bonjob.seeker.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;
import com.infoicon.bonjob.R;
import com.infoicon.bonjob.chat.Message;
import com.infoicon.bonjob.customview.CircleImageView;
import com.infoicon.bonjob.customview.CustomsButton;
import com.infoicon.bonjob.customview.CustomsTextView;
import com.infoicon.bonjob.customview.CustomsTextViewBold;
import com.infoicon.bonjob.logger.Logger;
import com.infoicon.bonjob.main.HomeSeekerActivity;
import com.infoicon.bonjob.picasso.ImageLoader;
import com.infoicon.bonjob.retrofit.AppRetrofit;
import com.infoicon.bonjob.retrofit.ServiceCreator;
import com.infoicon.bonjob.retrofit.response.GetAcceptJobResponse;
import com.infoicon.bonjob.retrofit.response.GetApplyJobResponse;
import com.infoicon.bonjob.retrofit.response.GetSeekerActivityResponse;
import com.infoicon.bonjob.seeker.searchJob.JobDetailsFragment;
import com.infoicon.bonjob.singletons.Singleton;
import com.infoicon.bonjob.utils.CommonUtils;
import com.infoicon.bonjob.utils.Keys;
import com.infoicon.bonjob.utils.UtilsMethods;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by infoicona on 18/3/17.
 */

public class UserActivityAdapter extends RecyclerView.Adapter<UserActivityAdapter.MyViewHolder>
{

    private Activity context;
    private List<GetSeekerActivityResponse.DataBean> dataBeanList;
    private String currentDate;

    public UserActivityAdapter(Activity activity) {
        this.context = activity;
        dataBeanList = new ArrayList<>();
    }


    public void addData(GetSeekerActivityResponse.DataBean dataBean) {
        dataBeanList.add(dataBean);
        notifyDataSetChanged();
    }


    public void clear() {
        dataBeanList.clear();
    }

    public void setCurrentDate(String currDate) {
        currentDate = currDate;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_user_activity, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textViewTitle.setText(dataBeanList.get(holder.getAdapterPosition()).getJob_title());
        if (!dataBeanList.get(position).getJob_image().equals("")) {
             ImageLoader.loadImageWithCircle(context, dataBeanList.get(holder.getAdapterPosition()).getJob_image(), holder.imgViewJob);
        } else {
            holder.imgViewJob.setImageResource(R.drawable.default_job);
        }

        holder.tvMessage.setText(dataBeanList.get(holder.getAdapterPosition()).getActivity_title());
       /* switch (dataBeanList.get(position).getStatus()) {
            case "0"://applied
                holder.tvMessage.setText(context.getResources().getString(R.string.have_applied));
                break;
            case "1"://pre-selected
                holder.tvMessage.setText(context.getResources().getString(R.string.preselected));
                break;
            case "2"://application not accepted
                holder.tvMessage.setText(context.getResources().getString(R.string.application_accepted));
                break;
            case "3"://hired
                    holder.tvMessage.setText(context.getResources().getString(R.string.your_are_selected));
                break;
            case "4"://expired
                holder.tvMessage.setText(context.getResources().getString(R.string.candidacy_expired));
                break;
        }*/


        if (dataBeanList.get(position).getSelectedByEmployer().equals("")) {
            holder.linearAcceptReject.setVisibility(View.GONE);
        } else {
            holder.linearAcceptReject.setVisibility(View.VISIBLE);
        }

        holder.imgViewJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JobDetailsFragment jobDetailsFragment = new JobDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Keys.JOB_ID, dataBeanList.get(holder.getAdapterPosition()).getJob_id());
                bundle.putInt(Keys.POSITION, -1);
                ((HomeSeekerActivity) context).addInnerFragment(jobDetailsFragment, bundle, Keys.JOB_DETAILS, false, true);
            }
        });

        holder.tvAgo.setText(getConvertedTime(dataBeanList.get(holder.getAdapterPosition()).getCreatedOn()));

        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceAcceptReject(holder.getAdapterPosition(), 1);//1->accept
            }
        });

        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serviceAcceptReject(holder.getAdapterPosition(), 0);//0->reject
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataBeanList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewTitle) CustomsTextViewBold textViewTitle;
        @BindView(R.id.imgViewJob) CircleImageView imgViewJob;
        @BindView(R.id.tvMessage) CustomsTextView tvMessage;
        @BindView(R.id.tvAgo) CustomsTextView tvAgo;
        @BindView(R.id.linearAcceptReject) LinearLayout linearAcceptReject;
        @BindView(R.id.btnAccept) CustomsButton btnAccept;
        @BindView(R.id.btnReject) CustomsButton btnReject;
        @BindView(R.id.progressBar) ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            imgViewJob.setBorderWidth(2);
            imgViewJob.setBorderColor(ContextCompat.getColor(context, R.color.border_grey));
        }
    }

    /** convert date to get the activity made before */
    private String getConvertedTime(String createdOn) {
        int seconds = UtilsMethods.getSecondDiff(createdOn, currentDate);
        if (seconds < 60 && seconds >= 0) {
            return context.getResources().getString(R.string.there_is) + " " + seconds + " " +
                    context.getResources().getString(R.string.seconds);
        } else if (seconds > 60 && seconds < 3600) {
            int minutes = seconds / 60;
            return context.getResources().getString(R.string.there_is) + " " + minutes + " " +
                    context.getResources().getString(R.string.minutes);
        } else if (seconds > 3600 && seconds < 86400) {
            int hour = seconds / 3600;
            return context.getResources().getString(R.string.there_is) + " " + hour + " " +
                    context.getResources().getString(R.string.hours);
        } else {
            return UtilsMethods.convertDateTimeFormat(createdOn);
        }
    }



    void addUserOnFireBase(GetAcceptJobResponse  getApplyJobResponse,String jobTitle) {
        FirebaseDatabase.
                getInstance().
                getReference()
                .child("recruiter/" +getApplyJobResponse.getEmployerInfo().getId())
                .
                        addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                HashMap mapUserInfo = (HashMap) dataSnapshot.getValue();


                                final DatabaseReference fireDatabaseReference = FirebaseDatabase.getInstance().getReference();

                                //send msg to user
                                Message newMessage = new Message();
                                newMessage.setText("Le candidat a postule à l'offre "+jobTitle);
                                newMessage.setFromId(getApplyJobResponse.getSeekerInfo().getId());
                                newMessage.setToId(getApplyJobResponse.getEmployerInfo().getId());
                                newMessage.setFcmToken((String) mapUserInfo.get("fcmToken"));
                                newMessage.setTimeStamp(System.currentTimeMillis() / 1000);

                                newMessage.setReadStatus("0");               //send msg
                                Message.setUserType from = new Message.setUserType();
                                from.setName(getApplyJobResponse.getSeekerInfo().getFirst_name()
                                        + " " + getApplyJobResponse.getSeekerInfo().getLast_name());
                                from.setType("seeker");
                                from.setProfilePic(Singleton.getUserInfoInstance().getuser_pic());

                                Message.setUserType to = new Message.setUserType();
                                to.setName(getApplyJobResponse.getEmployerInfo().getFirst_name()
                                        + " " + getApplyJobResponse.getEmployerInfo().getLast_name());
                                to.setType("recruiter");
                                to.setProfilePic((String) mapUserInfo.get("profilePic"));

                                newMessage.setFrom(from);
                                newMessage.setTo(to);

                                final DatabaseReference mss = FirebaseDatabase.getInstance().getReference();
                                final String key = mss.child("Messages").push().getKey();
                                newMessage.setMessageID(key);
                                mss.child("Messages/" + key).setValue(newMessage).
                                        addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {


                                                FirebaseDatabase.getInstance().getReference().child("read-Messages").
                                                        child(getApplyJobResponse.getEmployerInfo().getId())
                                                        .child(getApplyJobResponse.getSeekerInfo().getId()
                                                                + "/unreadMessages")
                                                        .child(key).setValue(1);

                                                fireDatabaseReference.
                                                        child("User-List/" + getApplyJobResponse.getSeekerInfo().getId() + "/"
                                                                + getApplyJobResponse.getEmployerInfo().getId() + "/" + key).setValue(1);
                                                fireDatabaseReference.
                                                        child("User-List/" + getApplyJobResponse.getEmployerInfo().getId() + "/"
                                                                + getApplyJobResponse.getSeekerInfo().getId() + "/" + key).setValue(1);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Write failed
                                                // ...
                                            }
                                        });
                                ;


                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value

                            }
                        });
    }
    /**
     * call service for get accept/reject job offer
     * @param adapterPosition
     */
    private void serviceAcceptReject(int adapterPosition, int status) {
        final SpotsDialog spotsDialog = new SpotsDialog(context, R.style.Custom);
        spotsDialog.setCancelable(false);
        spotsDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.SELECTED_ID, dataBeanList.get(adapterPosition).getSelectedByEmployer());
        jsonObject.addProperty(Keys.ACTIVITY_ID, dataBeanList.get(adapterPosition).getActivity_id());
        jsonObject.addProperty(Keys.JOB_ID, dataBeanList.get(adapterPosition).getJob_id());
        Call<GetAcceptJobResponse> call;
        if (status == 1)
        {//accept
            call = AppRetrofit.getAppRetrofitInstance().getApiServices().getAcceptJobResponse(jsonObject);
        } else {//reject
            call = AppRetrofit.getAppRetrofitInstance().getApiServices().getRejectJobResponse(jsonObject);
        }
        ServiceCreator.enqueueCall(call, new Callback<GetAcceptJobResponse>() {
            @Override
            public void onResponse(Response<GetAcceptJobResponse> response, Retrofit retrofit) {
                if (spotsDialog.isShowing())
                    spotsDialog.dismiss();

                GetAcceptJobResponse getSeekerActivityResponse = response.body();
                if (getSeekerActivityResponse.isSuccess()) {
                    if (status == 1) {
                        addUserOnFireBase(getSeekerActivityResponse,dataBeanList.get(adapterPosition).getJob_title());
                    }// add bubble on activity
                    int count = Singleton.getUserInfoInstance().getSeekerActivityCount();
                    Singleton.getUserInfoInstance().setSeekerActivityCount(count + 1);
                    context.sendBroadcast(new Intent(Keys.ACTIVITY_COUNT));

                    dataBeanList.get(adapterPosition).setSelectedByEmployer("");
                    dataBeanList.get(adapterPosition).setActivity_title(getSeekerActivityResponse.getMsg());
                    notifyDataSetChanged();
                    UtilsMethods.openCustumAlert(context, getSeekerActivityResponse.getMsg());
                } else {
                    if (getSeekerActivityResponse.getActive_user().equals(Keys.AUTH_CODE)) {
                        UtilsMethods.unAuthorizeAlert(context, getSeekerActivityResponse.getMsg());
                    } else {
                        CommonUtils.showSimpleMessageBottom(context, getSeekerActivityResponse.getMsg());
                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                spotsDialog.dismiss();
            }
        });
    }
}
