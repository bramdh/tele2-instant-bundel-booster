package com.bramdehart.tele2InstantBundelBooster;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static final int SMS_PERMISSION_CODE = 0;
    private Button btnRequestSmsPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!hasReadSmsPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }
        btnRequestSmsPermission = findViewById(R.id.btn_request_sms_permission);
        if (hasReadSmsPermission()) {
            btnRequestSmsPermission.setText(getString(R.string.bundle_booster_activated));
        } else {
            btnRequestSmsPermission.setText(getString(R.string.request_sms_permission));
        }
        btnRequestSmsPermission.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (hasReadSmsPermission()) {
                    btnRequestSmsPermission.setText(getString(R.string.bundle_booster_activated));
                } else {
                    btnRequestSmsPermission.setText(getString(R.string.request_sms_permission));
                    showRequestPermissionsInfoAlertDialog();
                }
            }
        });
    }

    /**
     * Optional informative alert dialog to explain the user why the app needs the Read/Send SMS permission
     */
    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        builder.setTitle(R.string.permission_alert_dialog_title);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }

    /**
     * Runtime permission shenanigans
     */
    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)) {
            Log.d(TAG, "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);
        btnRequestSmsPermission.setText(getString(R.string.bundle_booster_activated));

    }
}