package com.infoicon.bonjob.customview;

import android.content.Context;
import android.util.AttributeSet;

import com.infoicon.bonjob.utils.CommonUtils;


/**
 * Created by infoicon on 9/5/16.
 */
public class CustomsButton extends android.support.v7.widget.AppCompatButton {


    public CustomsButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(CommonUtils.getTypeFaceHalveticalBold(context));
    }

    public CustomsButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(CommonUtils.getTypeFaceHalveticalBold(context));
    }

    public CustomsButton(Context context) {
        super(context);
        setTypeface(CommonUtils.getTypeFaceHalveticalBold(context));
    }




}