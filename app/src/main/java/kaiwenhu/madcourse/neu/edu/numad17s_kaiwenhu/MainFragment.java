package kaiwenhu.madcourse.neu.edu.numad17s_kaiwenhu;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import 	android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.util.Log;
import android.app.Activity;



public class MainFragment extends Fragment {
    private AlertDialog mDialog;
    private Context context;
    private StringBuffer IMEIb = new StringBuffer();
    private String IMEI = "";

    private static final int REQUEST_READ_PHONE_STATE = 110;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Handle buttons here...
        context = this.getActivity();


        setVersionName(rootView);
        setVersionCode(rootView);

        //Set IMEI
//        setIMEI(rootView,(Activity)context);

        if (ContextCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        REQUEST_READ_PHONE_STATE);

                // MY_PERMISSIONS_REQUEST_READ_PHONE_STATE is an
                // app-defined int constant. The callback method gets the
                // result of the request.

        }else{
            //If permission already got, just get it
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            IMEI= tm.getDeviceId();
        }



        //setting up button.
        View aboutButton = rootView.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.about_title);

                //here I just create a vew.
                LayoutInflater inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.about, null);

                //This is for getting imei
                TextView imeiView = v.findViewById(R.id.imei_textView);
                //I used stringbuffer to get the IMEI when First time getting permission in onRequestPerssionsResult
                if (IMEIb.length()>0) {
                    IMEI = IMEIb.toString();
                }

                imeiView.setText("IMEI: "+IMEI);


                builder.setView(v);

                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok_label,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface,
                                                int i) {
                                // nothing
                            }
                        });
                mDialog = builder.show();
            }
        });

        //to generate an arithmetic error.
        View errorButton = rootView.findViewById(R.id.error_button);
        errorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = 1/ 0;
            }
        });

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        // Get rid of the about dialog if it's still up
        if (mDialog != null)
            mDialog.dismiss();
    }

    //Setting version name
    private void setVersionName(View v) {
        String versionName = "Version: ";
        PackageInfo packageInfo;
        try {
            packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(
                            context.getApplicationContext().getPackageName(),
                            0
                    );
            versionName += packageInfo.versionName;
        } catch (NameNotFoundException e) {
            versionName += "Unknown";
        }

        TextView tv = v.findViewById(R.id.ver_name);
        tv.setText(versionName);
    }

    //Setting version code
    private void setVersionCode(View v) {
        String versionCode = "Version: ";
        PackageInfo packageInfo;
        try {
            packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(
                            context.getApplicationContext().getPackageName(),
                            0
                    );
            versionCode += packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            versionCode += "Unknown";
        }

        TextView tv = v.findViewById(R.id.ver_number);
        tv.setText(versionCode);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    StringBuffer temp = new StringBuffer(tm.getDeviceId());
                    IMEIb.append(temp);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}