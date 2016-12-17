package com.frozenkoi.alertdialogfragment.lib.compat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

/**
 * Subclass of DialogFragment that handles click events and other dialog lifecycle events.
 */
public final class AlertDialogFragment extends DialogFragment {
    private static final String KEY_DIALOG_ID = "key_dialog_id";
    private  static final String KEY_BUTTON_TEXT_ID_POSITIVE = "key_positive_text_id";
    private static final String KEY_BUTTON_TEXT_ID_NEUTRAL  = "key_neutral_text_id";
    private static final String KEY_BUTTON_TEXT_ID_NEGATIVE = "key_negative_text_id";
    private static final String KEY_BUTTON_TEXT_STRING_POSITIVE = "key_positive_text_label";
    private static final String KEY_BUTTON_TEXT_STRING_NEUTRAL = "key_neutral_text_label";
    private static final String KEY_BUTTON_TEXT_STRING_NEGATIVE = "key_negative_text_label";

    public static final String KEY_DIALOG_TITLE_ID = "key_dialog_title_text_id";
    public static final String KEY_DIALOG_MESSAGE_ID = "key_dialog_message_text_id";
    private static final String KEY_DIALOG_TITLE_STRING = "key_dialog_title_string";
    private static final String KEY_DIALOG_MESSAGE_STRING = "key_dialog_message_string";

    public static final int ID_NOT_FOUND = -1;
    public static final String DIALOG_ID_NOT_FOUND = "-1";

    /**
     * Key for configuration for reversing button order. Stores a boolean in the bundle.
     * @see #mReverseButtons
     */
    public static final String KEY_REVERSE_BUTTON_ORDER = "key_reverse_buttons";
    /**
     * Default configuration for button reversal. Used when no configuration is explicitly used.
     */
    private static final boolean DEFAULT_BUTTON_INVERSION = false;
    /**
     * Reverse the positive and negative button positions.
     */
    private boolean mReverseButtons = false;

    /** Required empty constructor. */
    public AlertDialogFragment() {
        super();
    }

    /** Creates a new AlertDialogFragment and adds a Bundle with arguments.
     * @param dialogID The dialog string id. Used to identify one AlertDialogFragment from another.
     * @param titleId The resource id of the text to use for the title.
     * @param messageId The resource id of the text to use for the body text.
     * @return A newly created AlertDialogFragment with a {@link Bundle} set as the arguments.
     */
    public static AlertDialogFragment createDialogFragment(@NonNull final String dialogID
            , @StringRes final int titleId, @StringRes final int messageId) {
        AlertDialogFragment adf = new AlertDialogFragment();
        final Bundle args = new Bundle();
        args.putString(KEY_DIALOG_ID, dialogID);
        args.putInt(KEY_DIALOG_TITLE_ID, titleId);
        args.putInt(KEY_DIALOG_MESSAGE_ID, messageId);
        adf.setArguments(args);
        return adf;
    }

    /** Creates a new AlertDialogFragment and adds a Bundle with arguments.
     * @param dialogID The dialog string id. Used to identify one AlertDialogFragment from another.
     * @param titleId The resource id of the text to use for the title.
     * @param messageId The resource id of the text to use for the body text.
     * @param positiveButtonId String resource to use for the label of the positive button.
     * @param neutralButtonId String resource to use for the label of the neutral button.
     * @param negativeButtonId String resource to use for the label of the negative button.
     * @return A newly created AlertDialogFragment with a {@link Bundle} set as the arguments.
     */
    private static AlertDialogFragment createDialogFragment(@NonNull final String dialogID
            , @StringRes final int titleId, @StringRes final int messageId
            , @StringRes final int positiveButtonId, @StringRes final int neutralButtonId
            , @StringRes final int negativeButtonId) {
        return createDialogFragment(dialogID, titleId, messageId, DEFAULT_BUTTON_INVERSION,
                positiveButtonId, neutralButtonId, negativeButtonId);
    }

    /** Creates a new AlertDialogFragment and adds a Bundle with arguments.
     * @param dialogID The dialog string id. Used to identify one AlertDialogFragment from another.
     * @param titleId The resource id of the text to use for the title.
     * @param messageId The resource id of the text to use for the body text.
     * @param reverseButtons Set to {@code true} to invert the order of the positive and negative
     *                       buttons.<p/>
     *                       The reversal is done when setting the buttons and when calling
     *                       #onDialogButtonClicked. Using {@link #getDialog()} and
     *                       {@link AlertDialog#getButton(int)} will not yield consistent results if
     *                       this parameter is set to {@code true}.
     * @param positiveButtonId String resource to use for the label of the positive button.
     * @param neutralButtonId String resource to use for the label of the neutral button.
     * @param negativeButtonId String resource to use for the label of the negative button.
     * @return A newly created AlertDialogFragment with a {@link Bundle} set as the arguments.
     */
    public static AlertDialogFragment createDialogFragment(@NonNull final String dialogID
            , @StringRes final int titleId, @StringRes final int messageId
            , final boolean reverseButtons
            , @StringRes final int positiveButtonId, @StringRes final int neutralButtonId
            , @StringRes final int negativeButtonId) {
        AlertDialogFragment adf = createDialogFragment(dialogID, titleId, messageId);
        Bundle args = adf.getArguments();
        args.putInt(KEY_BUTTON_TEXT_ID_POSITIVE, positiveButtonId);
        args.putInt(KEY_BUTTON_TEXT_ID_NEUTRAL, neutralButtonId);
        args.putInt(KEY_BUTTON_TEXT_ID_NEGATIVE, negativeButtonId);
        args.putBoolean(KEY_REVERSE_BUTTON_ORDER, reverseButtons);
        return adf;
    }

    /** Creates a new AlertDialogFragment and adds a Bundle with arguments.
     * @param dialogID The dialog string id. Used to identify one AlertDialogFragment from another.
     * @param title The text to use for the title.
     * @param message The text to use for the body text.
     * @param positiveButtonLabel String to use for the label of the positive button.
     * @param neutralButtonLabel String to use for the label of the neutral button.
     * @param negativeButtonLabel String to use for the label of the negative button.
     * @return A newly created AlertDialogFragment with a {@link Bundle} set as the arguments.
     */
    public static AlertDialogFragment createDialogFragment(@NonNull final String dialogID,
            @Nullable final String title, @Nullable final String message,
            @Nullable final String positiveButtonLabel, @Nullable final String neutralButtonLabel,
            @Nullable final String negativeButtonLabel) {
        AlertDialogFragment adf = new AlertDialogFragment();
        final Bundle args = new Bundle();
        args.putString(KEY_DIALOG_ID, dialogID);
        args.putString(KEY_DIALOG_TITLE_STRING, title);
        args.putString(KEY_DIALOG_MESSAGE_STRING, message);
        args.putString(KEY_BUTTON_TEXT_STRING_POSITIVE, positiveButtonLabel);
        args.putString(KEY_BUTTON_TEXT_STRING_NEUTRAL, neutralButtonLabel);
        args.putString(KEY_BUTTON_TEXT_STRING_NEGATIVE, negativeButtonLabel);
        adf.setArguments(args);
        return adf;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        mReverseButtons = getArguments().getBoolean(KEY_REVERSE_BUTTON_ORDER,
                                                    DEFAULT_BUTTON_INVERSION);

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
        int id = getArguments().getInt(KEY_DIALOG_TITLE_ID, ID_NOT_FOUND);
        if (ID_NOT_FOUND != id) {
            b.setTitle(id);
        }

        String str = getArguments().getString(KEY_DIALOG_TITLE_STRING);
        if (str != null) {
            b.setTitle(str);
        }

        id = getArguments().getInt(KEY_DIALOG_MESSAGE_ID, ID_NOT_FOUND);
        if (ID_NOT_FOUND != id) {
            b.setMessage(id);
        }

        str = getArguments().getString(KEY_DIALOG_MESSAGE_STRING);
        if (str != null) {
            b.setMessage(str);
        }

        id = getArguments().getInt(KEY_BUTTON_TEXT_ID_POSITIVE, ID_NOT_FOUND);
        if (ID_NOT_FOUND != id) {
            if (mReverseButtons) {
                b.setNegativeButton(id, mButtonEventHandler);
            } else {
                b.setPositiveButton(id, mButtonEventHandler);
            }
        }

        str = getArguments().getString(KEY_BUTTON_TEXT_STRING_POSITIVE);
        if (str != null) {
            if (mReverseButtons) {
                b.setNegativeButton(str, mButtonEventHandler);
            } else {
                b.setPositiveButton(str, mButtonEventHandler);
            }
        }

        id = getArguments().getInt(KEY_BUTTON_TEXT_ID_NEUTRAL, ID_NOT_FOUND);
        if (ID_NOT_FOUND != id) {
            b.setNeutralButton(id, mButtonEventHandler);
        }

        str = getArguments().getString(KEY_BUTTON_TEXT_STRING_NEUTRAL);
        if (str != null) {
            b.setNeutralButton(str, mButtonEventHandler);
        }

        id = getArguments().getInt(KEY_BUTTON_TEXT_ID_NEGATIVE, ID_NOT_FOUND);
        if (ID_NOT_FOUND != id) {
            if (mReverseButtons) {
                b.setPositiveButton(id, mButtonEventHandler);
            } else {
                b.setNegativeButton(id, mButtonEventHandler);
            }
        }

        str = getArguments().getString(KEY_BUTTON_TEXT_STRING_NEGATIVE);
        if (str != null) {
            if (mReverseButtons) {
                b.setPositiveButton(str, mButtonEventHandler);
            } else {
                b.setNegativeButton(str, mButtonEventHandler);
            }
        }

        AlertDialog dialog = b.create();

        return dialog;
    }

    private AlertDialogObserver getTarget() {
        AlertDialogObserver retVal = null;
        Fragment t = getTargetFragment();
        if (t instanceof AlertDialogObserver) {
            retVal = (AlertDialogObserver) t;   //Cast
        }
        if (null == retVal) {
            Activity a = getActivity();
            if (a instanceof AlertDialogObserver) {
                retVal = (AlertDialogObserver) a;   //Cast
            }
        }
        return retVal;
    }

    private final DialogInterface.OnClickListener mButtonEventHandler
            = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(final DialogInterface dialog, final int which) {
            final String whichDialog = getArguments().getString(KEY_DIALOG_ID, DIALOG_ID_NOT_FOUND);

            final int whichButton = chooseButtonOrder(which, mReverseButtons);
            AlertDialogObserver target = getTarget();
            if (null != target) {
                target.onDialogButtonClicked(dialog, whichDialog, whichButton);
            }
        }
    };

    /**
     * Utility function to choose which button to use based on the 'real' button and whether the
     * positive and negative buttons are being inverted.
     * @param which Which button we want to use.
     * @param reverseButtons {@code true} if inverting or {@code false} if not.
     * @return The id that we should use. If reverseButtons is {@code false} the same value
     *         as {@code which}. For buttons other than positive and negative, return {@code which}
     *         unchanged regardless of {@code reverseButtons}.
     */
    private static int chooseButtonOrder(final int which, final boolean reverseButtons) {
        int whichButton = which;
        if (reverseButtons) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    whichButton = DialogInterface.BUTTON_NEGATIVE;
                    break;
                case DialogInterface.BUTTON_NEGATIVE:
                    whichButton = DialogInterface.BUTTON_POSITIVE;
                    break;
                default:
                    whichButton = which;
            }
        }
        return whichButton;
    }

    @Override
    public void onCancel(final DialogInterface dialog) {
        super.onCancel(dialog);

        AlertDialogObserver target = getTarget();
        if (null != target) {
            target.onDialogCancelled(dialog, getArguments().getString(KEY_DIALOG_ID, DIALOG_ID_NOT_FOUND));
        }
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);

        AlertDialogObserver target = getTarget();
        if (null != target) {
            target.onDialogDismissed(dialog, getArguments().getString(KEY_DIALOG_ID, DIALOG_ID_NOT_FOUND));
        }
    }

    /**
     * Interface for observers of a AlertDialogFragment.
     */
    public interface AlertDialogObserver {
        /**
         * Called when one of the dialog buttons is clicked.
         * @param dialog The dialog that received the click.
         * @param whichDialog The id of the dialog that is being dismissed.
         * @param whichButton Id of the button that was clicked. The button that was clicked (e.g.
         *            {@link DialogInterface#BUTTON1}) or the position
         *            of the item clicked.
         *
         * @see android.content.DialogInterface.OnClickListener#onClick(
           android.content.DialogInterface, int)
         */
        void onDialogButtonClicked(@NonNull final DialogInterface dialog, @NonNull final String whichDialog, int whichButton);

        /**
         * Called when the dialog is cancelled.
         * @param dialog The dialog that was canceled will be passed into the method.
         * @param whichDialog The id of the dialog that is being dismissed.
         *
         * @see android.app.DialogFragment#onCancel(android.content.DialogInterface)
         * @see AlertDialogFragment#createDialogFragment createDialogFragment's dialogID parameter.
         */
        void onDialogCancelled(@NonNull final DialogInterface dialog, @NonNull final String whichDialog);

        /**
         * Called when the dialog is dismissed.
         * @param dialog The dialog that was dismissed will be passed into the method.
         * @param whichDialog The id of the dialog that is being dismissed.
         *
         * @see android.app.DialogFragment#onDismiss(android.content.DialogInterface)
         * @see AlertDialogFragment#createDialogFragment createDialogFragment's dialogID parameter.
         */
        void onDialogDismissed(@NonNull final DialogInterface dialog, @NonNull final String whichDialog);
    }
}
