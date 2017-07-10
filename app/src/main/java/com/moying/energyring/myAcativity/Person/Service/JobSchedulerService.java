package com.moying.energyring.myAcativity.Person.Service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;

/**
 * Created by waylen on 2017/7/2.
 */

public class JobSchedulerService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        mJobHandler.sendMessage( Message.obtain( mJobHandler, 1, jobParameters) );

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        mJobHandler.removeMessages( 1 );
        return false;
    }

    private Handler mJobHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
//            Log.e("tttttttttt","闹钟重新注册");
//            String[] timelegth = saveFile.getShareData("timeTxt", JobSchedulerService.this).split(":");
//            Person_Play.calendarWeek(timelegth,JobSchedulerService.this);
//            Toast.makeText(getApplicationContext(),"JobService task running", Toast.LENGTH_SHORT).show();
            jobFinished((JobParameters) msg.obj, false);//false 会重复循环线程注册
            return true;
        }

    });
}
