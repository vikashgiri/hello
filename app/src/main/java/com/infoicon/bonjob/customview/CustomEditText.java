package com.infoicon.bonjob.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

import com.infoicon.bonjob.utils.CommonUtils;

/**
 * Created by infoicon on 9/6/17.
 */

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {
    public CustomEditText(Context context) {
        super(context);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }


}
