package com.infoicon.bonjob.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.infoicon.bonjob.utils.CommonUtils;


/**
 * Created by infoicona on 14/3/17.
 */

public class CustomRadioButton extends android.support.v7.widget.AppCompatRadioButton{

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CustomRadioButton(Context context) {
        super(context);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }
}
