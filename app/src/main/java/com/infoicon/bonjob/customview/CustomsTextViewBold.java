package com.infoicon.bonjob.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.infoicon.bonjob.utils.CommonUtils;


/**
 * Created by infoicon on 21/3/16.
 */
public class CustomsTextViewBold extends AppCompatTextView {


    public CustomsTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(CommonUtils.getTypeFaceHalveticalBold(context));

    }

    public CustomsTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(CommonUtils.getTypeFaceHalveticalBold(context));
    }

    public CustomsTextViewBold(Context context) {
        super(context);
        setTypeface(CommonUtils.getTypeFaceHalveticalBold(context));
    }


}