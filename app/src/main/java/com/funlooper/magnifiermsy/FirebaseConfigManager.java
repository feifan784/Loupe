package com.funlooper.magnifiermsy;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Objects;

public class FirebaseConfigManager {
    private FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

    public FirebaseConfigManager setConfig() {
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
        return this;
    }

    public void fetch(Context activity) {
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener((Activity) activity, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d("FirebaseConfigManager", "Config params updated: " + updated);

                        } else {
                            Log.d("FirebaseConfigManager", "failed:  " + Objects.requireNonNull(task.getException()).getMessage());
                        }
                    }
                });
    }

}
