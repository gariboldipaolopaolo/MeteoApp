package ch.supsi.dti.isin.meteoapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.time.LocalTime;

public class MeteoWorker extends Worker {

    private Context mContext;

    public MeteoWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        mContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext, "default")
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle("MeteoApp Customized Notification")
                .setContentText("Thanks for using MeteoApp. Support us with a good mark :)")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(mContext);
        manager.notify(0, mBuilder.build());

        return Result.success();
    }
}
