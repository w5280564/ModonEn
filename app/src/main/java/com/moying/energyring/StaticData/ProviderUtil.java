package com.moying.energyring.StaticData;

import android.content.Context;

/**用于解决 Provider 冲突的 util
 * Created by waylen on 2017/12/19.
 */

public class ProviderUtil {
    public static String getFileProviderName(Context context){
        return context.getPackageName()+".provider";
    }
}
