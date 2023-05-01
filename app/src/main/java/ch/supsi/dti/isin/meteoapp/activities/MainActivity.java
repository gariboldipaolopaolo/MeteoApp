package ch.supsi.dti.isin.meteoapp.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.work.WorkManager;
import java.util.concurrent.TimeUnit;
import ch.supsi.dti.isin.meteoapp.MeteoWorker;
import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.fragments.ListFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_single_fragment);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "TEST_CHANNEL", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Test Channel Description");
            mNotificationManager.createNotificationChannel(channel);
        }

        PeriodicWorkRequest periodicRequest = new PeriodicWorkRequest.Builder(MeteoWorker.class, 10, TimeUnit.MINUTES).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("SHOW NOTIFICATION", ExistingPeriodicWorkPolicy.KEEP, periodicRequest);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new ListFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
