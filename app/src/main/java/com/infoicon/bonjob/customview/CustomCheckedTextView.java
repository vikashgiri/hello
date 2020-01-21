package com.infoicon.bonjob.customview;

import android.content.Context;
import android.util.AttributeSet;

import com.infoicon.bonjob.utils.CommonUtils;


/**
 * Created by infoicona on 9/3/17.
 */

public class CustomCheckedTextView extends android.support.v7.widget.AppCompatCheckedTextView {
    public CustomCheckedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CustomCheckedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CustomCheckedTextView(Context context) {
        super(context);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }
}
