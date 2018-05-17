package com.moying.energyring.myAcativity.Pk.Training.Inteface;


import com.moying.energyring.myAcativity.Pk.Training.utils.FileInfo;

import java.util.ArrayList;

/**
 * 传递下载进度接口
 */

public interface OnDownLoadBackListener {
    public void onDownloadSize(ArrayList<FileInfo> list);
}
