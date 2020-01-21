package com.infoicon.bonjob.utils;

import com.infoicon.bonjob.logger.MyApplication;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Pramod on 25/1/18.
 */

public class AddShortcutBadger {

    public static void addBadger(int badgeCount){
        ShortcutBadger.applyCount(MyApplication.getAppContext(), badgeCount); //for 1.1.4+
    }

    public static void removeAllBadger(){
        ShortcutBadger.removeCount(MyApplication.getAppContext());
    }
}
