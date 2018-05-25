package com.frozenkoi.alertdialogfragment.demo.nocompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;

/**
 * Subclass of DialogFragment that handles click events and other dialog lifecycle events.
 */
public final class AlertDialogFragment extends DialogFragment {
    public static final String KEY_DIALOG_ID = "key_dialog_id";
    public static final String KEY_BUTTON_TEXT_ID_POSITIVE = "key_positive_text_id";
    public static final String KEY_BUTTON_TEXT_ID_NEUTRAL  = "key_neutral_text_id";
    public static final String KEY_BUTTON_TEXT_ID_NEGATIVE = "key_negative_text_id";

    public static final String KEY_DIALOG_TITLE_ID = "key_dialog_title_text_id";
    public static final String KEY_DIALOG_MESSAGE_ID = "key_dialog_message_text_id";
    public static final int ID_NOT_FOUND = -1;

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
     * @param dialogID The dialog integer id. Used to identify one AlertDialogFragment from another.
     * @param titleId The resource id of the text to use for the title.
     * @param messageId The resource id of the text to use for the body text.
     * @return A newly created AlertDialogFragment with a {@link Bundle} set as the arguments.
     */
    public static AlertDialogFragment createDialogFragment(final int dialogID
            , @StringRes final int titleId, @StringRes final int messageId) {
        AlertDialogFragment adf = new AlertDialogFragment();
        final Bundle args = new Bundle();
        args.putInt(KEY_DIALOG_ID, dialogID);
        args.putInt(KEY_DIALOG_TITLE_ID, titleId);
        args.putInt(KEY_DIALOG_MESSAGE_ID, messageId);
        adf.setArguments(args);
        return adf;
    }

    /** Creates a new AlertDialogFragment and adds a Bundle with arguments.
     * @param dialogID The dialog integer id. Used to identify one AlertDialogFragment from another.
     * @param titleId The resource id of the text to use for the title.
     * @param messageId The resource id of the text to use for the body text.
     * @param positiveButtonId String resource to use for the label of the positive button.
     * @param neutralButtonId String resource to use for the label of the neutral button.
     * @param negativeButtonId String resource to use for the label of the negative button.
     * @return A newly created AlertDialogFragment with a {@link Bundle} set as the arguments.
     */
    private static AlertDialogFragment createDialogFragment(final int dialogID
            , @StringRes final int titleId, @StringRes final int messageId
            , @StringRes final int positiveButtonId, @StringRes final int neutralButtonId
            , @StringRes final int negativeButtonId) {
        return createDialogFragment(dialogID, titleId, messageId, DEFAULT_BUTTON_INVERSION,
                positiveButtonId, neutralButtonId, negativeButtonId);
    }

    /** Creates a new AlertDialogFragment and adds a Bundle with arguments.
     * @param dialogID The dialog integer id. Used to identify one AlertDialogFragment from another.
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
    public static AlertDialogFragment createDialogFragment(final int dialogID
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

        id = getArguments().getInt(KEY_DIALOG_MESSAGE_ID, ID_NOT_FOUND);
        if (ID_NOT_FOUND != id) {
            b.setMessage(id);
        }

        id = getArguments().getInt(KEY_BUTTON_TEXT_ID_POSITIVE, ID_NOT_FOUND);
        if (ID_NOT_FOUND != id) {
            if (mReverseButtons) {
                b.setNegativeButton(id, mButtonEventHandler);
            } else {
                b.setPositiveButton(id, mButtonEventHandler);
            }
        }

        id = getArguments().getInt(KEY_BUTTON_TEXT_ID_NEUTRAL, ID_NOT_FOUND);
        if (ID_NOT_FOUND != id) {
            b.setNeutralButton(id, mButtonEventHandler);
        }

        id = getArguments().getInt(KEY_BUTTON_TEXT_ID_NEGATIVE, ID_NOT_FOUND);
        if (ID_NOT_FOUND != id) {
            if (mReverseButtons) {
                b.setPositiveButton(id, mButtonEventHandler);
            } else {
                b.setNegativeButton(id, mButtonEventHandler);
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
            final int whichDialog = getArguments().getInt(KEY_DIALOG_ID, ID_NOT_FOUND);

            final int whichButton = chooseButtonOrder(which, mReverseButtons);
            AlertDialogObserver tgt = getTarget();
            if (null != tgt) {
                tgt.onDialogButtonClicked(dialog, whichDialog, whichButton);
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
     *         unchanged regardles of {@code reverseButtons}.
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

        AlertDialogObserver tgt = getTarget();
        if (null != tgt) {
            tgt.onDialogCancelled(dialog, getArguments().getInt(KEY_DIALOG_ID, ID_NOT_FOUND));
        }
    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);

        AlertDialogObserver tgt = getTarget();
        if (null != tgt) {
            tgt.onDialogDismissed(dialog, getArguments().getInt(KEY_DIALOG_ID, ID_NOT_FOUND));
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
        void onDialogButtonClicked(DialogInterface dialog, int whichDialog, int whichButton);

        /**
         * Called when the dialog is cancelled.
         * @param dialog The dialog that was canceled will be passed into the method.
         * @param whichDialog The id of the dialog that is being dismissed.
         *
         * @see android.app.DialogFragment#onCancel(android.content.DialogInterface)
         * @see AlertDialogFragment#createDialogFragment createDialogFragment's dialogID parameter.
         */
        void onDialogCancelled(DialogInterface dialog, int whichDialog);

        /**
         * Called when the dialog is dismissed.
         * @param dialog The dialog that was dismissed will be passed into the method.
         * @param whichDialog The id of the dialog that is being dismissed.
         *
         * @see android.app.DialogFragment#onDismiss(android.content.DialogInterface)
         * @see AlertDialogFragment#createDialogFragment createDialogFragment's dialogID parameter.
         */
        void onDialogDismissed(DialogInterface dialog, int whichDialog);
    }
}
