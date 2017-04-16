package com.neteamindustry.dearmantan;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by USER on 2/16/2017.
 */
public class AlertDialogFragment extends DialogFragment{

    public static AlertDialogFragment newInstance (int title, String message, boolean canContinue){
        AlertDialogFragment frag = new AlertDialogFragment();
        Bundle args = new Bundle();

        args.putInt("title",title);
        args.putString("message",message);
        args.putBoolean("canContinue", canContinue);

        frag.setArguments(args);
        frag.setCancelable(false);
        frag.setRetainInstance(true);

        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int title = getArguments().getInt("title");
        String message = getArguments().getString("message");
        boolean canContinue = getArguments().getBoolean("canContinue");

        AlertDialog.Builder adb =  new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message);

        if(canContinue){
            return adb.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    }
            )
                    .create();
        }
        else{
            return adb.setNeutralButton("Close Application",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            getActivity().finish();
                        }
                    }
            )
                    .create();
        }
    }

    public void onDestroyView(){
        Dialog dialog = getDialog();
        //untuk rotasi fragment
        if (dialog != null && getRetainInstance()){
            dialog.setDismissMessage(null);
        }
        super.onDestroyView();
    }
}
