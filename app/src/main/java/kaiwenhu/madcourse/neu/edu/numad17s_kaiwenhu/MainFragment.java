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
import android.app.Activity;




public class MainFragment extends Fragment {
    private AlertDialog mDialog;
    private TextView mImeiView;
    private Context context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Handle buttons here...
        context = this.getActivity();

        TextView tv = rootView.findViewById(R.id.ver_name);
        tv.setText("foo");

        //setVersionName();

        View aboutButton = rootView.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

//                TextView imeiView = view.findViewById(R.id.imei_textView);
//                TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
//                imeiView.setText("IMEI: "+tm.getDeviceId());

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.about_title);

                LayoutInflater inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.about, null);
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

    private void setVersionName() {
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

        context = this.getActivity();
        View rootView = ((Activity)context).getWindow().getDecorView().findViewById(android.R.id.content);
        TextView tv = rootView.findViewById(R.id.ver_name);
        tv.setText(versionName);
    }
}