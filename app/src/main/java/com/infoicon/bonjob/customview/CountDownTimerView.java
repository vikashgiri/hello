package com.infoicon.bonjob.customview;

/**
 * Created by infoicona on 10/10/17.
 */

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infoicon.bonjob.R;
import com.infoicon.bonjob.utils.UtilsMethods;


public class CountDownTimerView extends LinearLayout {
    Handler handler;
    CountRunnable countRunnable;
    String endtime;
    String startTime;
    boolean endtimeoverflag = false;
    Context mContext;
    int position;
    private transient TextView tvCountDays;
    private transient TextView tvCounthours;
    private transient TextView tvCountMinute;
    private transient TextView tvCountSeconds;

    public CountDownTimerView(Context context) {
        super(context);
    }

    public CountDownTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvCountDays = (TextView ) findViewById(R.id.tvCountDays);
        tvCounthours = (TextView ) findViewById(R.id.tvCounthours);
        tvCountMinute = (TextView ) findViewById(R.id.tvCountMinute);
        tvCountSeconds = (TextView ) findViewById(R.id.tvCountSeconds);
        countRunnable = new CountRunnable();
        handler = new Handler();
    }

    public void setCountDownData() {
        handler.postDelayed(countRunnable, 1000);
    }

    public void setStartEndTime(String startTime,String endtime) {
        this.startTime = startTime;
        this.endtime = endtime;
        setCountDownData();
    }

    public boolean getendtimeflag() {
        return endtimeoverflag;
    }



    public void setContext(Context context,int position) {
        this.mContext = context;
        this.position=position;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacks(countRunnable);
    }

    class CountRunnable implements Runnable {

        @Override
        public void run() {
            String[] countdowntimearray = UtilsMethods.getCounterTime(startTime,endtime);

            if (Integer.parseInt(countdowntimearray[0]) <= 0) {
                tvCountDays.setText("0");
                endtimeoverflag = false;
            } else {
                tvCountDays.setText(countdowntimearray[0]);
                endtimeoverflag = true;
            }

            if (Integer.parseInt(countdowntimearray[1]) <= 0) {
                tvCounthours.setText("0");
                endtimeoverflag = false;
            } else {
                tvCounthours.setText(countdowntimearray[1]);
                endtimeoverflag = true;
            }

            if (Integer.parseInt(countdowntimearray[2]) <= 0) {
                tvCountMinute.setText("0");
                endtimeoverflag = false;
            } else {
                tvCountMinute.setText(countdowntimearray[2]);
                endtimeoverflag = true;
            }

            if (Integer.parseInt(countdowntimearray[3]) <= 0) {
                tvCountSeconds.setText("0");
                endtimeoverflag = false;
            } else {
                tvCountSeconds.setText(countdowntimearray[3]);
                endtimeoverflag = true;
            }

            setCountDownData();
        }
    }
}



