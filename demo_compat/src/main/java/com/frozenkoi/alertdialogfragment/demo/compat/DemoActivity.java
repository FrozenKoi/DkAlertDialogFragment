package com.frozenkoi.alertdialogfragment.demo.compat;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.frozenkoi.alertdialogfragment.lib.compat.AlertDialogFragment;

/**
 * Activity to demonstrate usage of the DkAlertDialogFragment library.
 */
public final class DemoActivity extends AppCompatActivity
                          implements AlertDialogFragment.AlertDialogObserver {
    private static final String TAG = "DemoActivity";

    private static final String DIALOG_WITH_RESOURCES_FRAGMENT_TAG = "dialog.with.resources";

    private static final String DIALOG_WITH_STRINGS_FRAGMENT_TAG = "dialog.with.strings";
    private TextView mTextResourcesResult;
    private TextView mTextStringsResult;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        final Button buttonRes = (Button) findViewById(R.id.button_show_dialog_with_resources);
        mTextResourcesResult = (TextView) findViewById(R.id.text_last_action_dialog_with_resources);
        buttonRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showDialogWithResources();
            }
        });

        final Button buttonStrings = (Button) findViewById(R.id.button_show_dialog_with_strings);
        mTextStringsResult = (TextView) findViewById(R.id.text_last_action_dialog_with_strings);
        buttonStrings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showDialogWithStrings();
            }
        });
    }

    /**
     * Helper method to create and show a dialog that uses string resources for the button labels.
     */
    private void showDialogWithResources() {
        mTextResourcesResult.setText(R.string.label_no_last_action);
        AlertDialogFragment adf = AlertDialogFragment.createDialogFragment(DIALOG_WITH_RESOURCES_FRAGMENT_TAG,
                R.string.dialog_title_with_resources,
                R.string.dialog_message_with_resources,
                false,
                R.string.dialog_button_positive,
                R.string.dialog_button_neutral,
                R.string.dialog_button_negative);
        adf.show(getSupportFragmentManager(), DIALOG_WITH_RESOURCES_FRAGMENT_TAG);
    }

    /**
     * Helper method to create and show a dialog that uses hardcoded strings for the button labels.
     */
    private void showDialogWithStrings() {
        mTextStringsResult.setText(R.string.label_no_last_action);
        AlertDialogFragment adf = AlertDialogFragment.createDialogFragment(DIALOG_WITH_STRINGS_FRAGMENT_TAG,
                "Dialog using strings in code",
                "This dialog is shown using strings set from code",
                "Positive string",
                "Neutral string",
                "Negative string");
//        adf.setCancelable(false);
        adf.show(getSupportFragmentManager(), DIALOG_WITH_STRINGS_FRAGMENT_TAG);
    }

    @Override
    public void onDialogButtonClicked(@NonNull final DialogInterface dialog, @NonNull final String whichDialog,
                                      final int whichButton) {
        switch (whichDialog) {
            case DIALOG_WITH_RESOURCES_FRAGMENT_TAG:
                mTextResourcesResult.setText(Integer.toString(whichButton));
                break;
            case DIALOG_WITH_STRINGS_FRAGMENT_TAG:
                mTextStringsResult.setText(Integer.toString(whichButton));
                break;
        }
    }

    @Override
    public void onDialogCancelled(@NonNull final DialogInterface dialog, @NonNull final String whichDialog) {
        switch (whichDialog) {
            case DIALOG_WITH_RESOURCES_FRAGMENT_TAG:
                mTextResourcesResult.setText("cancelled");
                break;
            case DIALOG_WITH_STRINGS_FRAGMENT_TAG:
                mTextStringsResult.setText("cancelled");
                break;
        }
    }

    @Override
    public void onDialogDismissed(@NonNull final DialogInterface dialog, @NonNull final String whichDialog) {
        switch (whichDialog) {
            case DIALOG_WITH_RESOURCES_FRAGMENT_TAG:
                mTextResourcesResult.setText("dismissed");
                break;
            case DIALOG_WITH_STRINGS_FRAGMENT_TAG:
                mTextStringsResult.setText("dismissed");
                break;
        }
    }
}
