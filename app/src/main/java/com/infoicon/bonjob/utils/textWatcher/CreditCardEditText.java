package com.infoicon.bonjob.utils.textWatcher;

/**
 * Created by Pramod on 3/2/18.
 */

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import com.infoicon.bonjob.utils.CommonUtils;


public class CreditCardEditText extends android.support.v7.widget.AppCompatEditText {

    private CreditCardBaseTextWatcher mTextWatcher;

    public CreditCardEditText(Context context) {
        super(context);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CreditCardEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CreditCardEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(CommonUtils.getTypeFaceHalvetical(context));
    }

    public CreditCardBaseTextWatcher getTextWatcher() {
        return mTextWatcher;
    }

    public void setTextWatcher(CreditCardBaseTextWatcher textWatcher) {
        this.mTextWatcher = textWatcher;
    }

    public void setCopyPastedText(CharSequence text) {
        mTextWatcher.setIsCopyPasted(true);
        setText(text);
        mTextWatcher.setIsCopyPasted(false);
    }

    @Override
    public void addTextChangedListener(TextWatcher watcher) {
        super.addTextChangedListener(watcher);
        if (watcher instanceof CreditCardBaseTextWatcher) {
            CreditCardBaseTextWatcher creditCardBaseTextWatcher = (CreditCardBaseTextWatcher) watcher;
            setTextWatcher(creditCardBaseTextWatcher);
        }
    }
}