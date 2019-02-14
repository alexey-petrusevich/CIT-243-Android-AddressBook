package bignerdranch.com.myaddressbook;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import java.util.UUID;

public class RemoveContactDialogFragment extends DialogFragment {

    private static final String ARG_CONTACT = "contact";

    private Contact mContact;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mContact = (Contact) getArguments().get("contact");

        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.item_remove)
                .setMessage(R.string.dialog_remove)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // remove item from the list
                        ContactsList.get(getContext()).removeContact(mContact);

                        if (getActivity().findViewById(R.id.details_fragment_container) != null) {

                            FragmentManager fragmentManager = getFragmentManager();

                            fragmentManager.beginTransaction()
                                    .remove(fragmentManager.findFragmentById(R.id.details_fragment_container))
                                    .commit();

                            ContactsListFragment fragment = (ContactsListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                            fragment.updateUI();

                        }

                        closeDialog();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        closeDialog();

                    }
                })
                .create();
    }

    public static RemoveContactDialogFragment newInstance(Contact contact) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT, contact);

        RemoveContactDialogFragment dialogFragment = new RemoveContactDialogFragment();
        dialogFragment.setArguments(args);

        return dialogFragment;
    }

    private void closeDialog(){
        if (getActivity().findViewById(R.id.details_fragment_container) == null) {

            getActivity().finish();

        } else {
            // dismiss this dialog
            dismiss();
        }
    }

}
