package com.infoicon.bonjob.interfaces;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by infoicona on 8/2/17.
 */

public interface FragmentChanger {
    public void addFragment(Fragment fragment, Bundle bundle, String tag,
                            boolean isRemoveBackStack, boolean isAddToBackStack);
    public void addInnerFragment(Fragment fragment, Bundle bundle,
                                 String tag, boolean isRemoveBackStack, boolean isAddToBackStack);
    public void removeFragment();
}
