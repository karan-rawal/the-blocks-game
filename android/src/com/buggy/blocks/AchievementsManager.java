package com.buggy.blocks;

import android.content.Context;

import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

/**
 * Created by karan on 31/1/17.
 */
public class AchievementsManager {

    public static final int NOOBIE = 40;
    public static final int LUCKY = 50;
    public static final int PRO = 60;
    public static final int IMBA = 65;
    public static final int IMMORTAL = 70;

    public static void unlockAchievement(int score, GameHelper helper, Context context){
        if(score > IMMORTAL){
            processUnlocking(context.getString(R.string.achievement_noobie), helper);
            processUnlocking(context.getString(R.string.achievement_lucky), helper);
            processUnlocking(context.getString(R.string.achievement_pro), helper);
            processUnlocking(context.getString(R.string.achievement_imba), helper);
            processUnlocking(context.getString(R.string.achievement_immortal), helper);
        }else if(score > IMBA && score <= IMMORTAL){
            processUnlocking(context.getString(R.string.achievement_noobie), helper);
            processUnlocking(context.getString(R.string.achievement_lucky), helper);
            processUnlocking(context.getString(R.string.achievement_pro), helper);
            processUnlocking(context.getString(R.string.achievement_imba), helper);
        }else if(score > PRO && score <= IMBA){
            processUnlocking(context.getString(R.string.achievement_noobie), helper);
            processUnlocking(context.getString(R.string.achievement_lucky), helper);
            processUnlocking(context.getString(R.string.achievement_pro), helper);
        }else if(score > LUCKY && score <= PRO){
            processUnlocking(context.getString(R.string.achievement_noobie), helper);
            processUnlocking(context.getString(R.string.achievement_lucky), helper);
        }else if(score > NOOBIE && score <= LUCKY){
            processUnlocking(context.getString(R.string.achievement_noobie), helper);
        }
    }

    public static void processUnlocking(String achievementId , GameHelper helper){
        Games.Achievements.unlock(helper.getApiClient(), achievementId);
    }
}
