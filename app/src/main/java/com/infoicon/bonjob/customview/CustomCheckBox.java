package com.infoicon.bonjob.customview;

import android.content.Context;
import android.util.AttributeSet;

import com.infoicon.bonjob.utils.CommonUtils;


/**
 * Created by infoicona on 14/3/17.
 */

public class CustomCheckBox extends android.support.v7.widget.AppCompatCheckBox{
    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CustomCheckBox(Context context) {
        super(context);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }
}
