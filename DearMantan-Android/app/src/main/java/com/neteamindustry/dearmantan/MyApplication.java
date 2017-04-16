

package com.neteamindustry.dearmantan;

import android.app.Application;

import ibmmobileappbuilder.cloudant.factory.CloudantDatabaseSyncerFactory;
import ibmmobileappbuilder.injectors.ApplicationInjector;
import android.support.multidex.MultiDexApplication;
import ibmmobileappbuilder.push.BluemixPushWrapper;
import java.net.URI;


import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;

/**
 * You can use thapi.BMSClient;
 import ibmmobileappbuilder.cloudant.factory.CloudantDatabasis as a global place to keep application-level resources
 * such as singletons, services, etc.
 */
public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationInjector.setApplicationContext(this);
        BluemixPushWrapper bluemixPushWrapper = new BluemixPushWrapper();
        bluemixPushWrapper.register(this,
            getString(R.string.bmdBluemixRegion),
            getString(R.string.pushAppGuid),
            getString(R.string.pushClientSecret)
        );
        //Syncing cloudant ds
        CloudantDatabaseSyncerFactory.instanceFor(
            "curhatan",
            URI.create("https://d81f6ea6-57b5-4d0e-84a4-23e3e7956b59-bluemix:2d9bb3176c4caa6c1763d8c2b52d5aae967ea6ab8baf2793444669459e8b3845@d81f6ea6-57b5-4d0e-84a4-23e3e7956b59-bluemix.cloudant.com/curhatan")
        ).sync(null);
      }
}
