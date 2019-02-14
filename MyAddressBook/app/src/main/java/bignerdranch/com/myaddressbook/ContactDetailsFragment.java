package bignerdranch.com.myaddressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.UUID;

public class ContactDetailsFragment extends Fragment{

    private static final String TAG_DEBUG = "ContactDetailsFragment";
    private static final String DIALOG_CONFIRM_DELETE = "DialogConfirmDelete";

    private Contact mContact;
    private EditText mName;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mStreet;
    private EditText mCity;
    private EditText mState;
    private EditText mZip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.contact_details_fragment, container, false);

        mName = (EditText) v.findViewById(R.id.edit_text_name);
        mPhone = (EditText) v.findViewById(R.id.edit_text_phone);
        mEmail = (EditText) v.findViewById(R.id.edit_text_email);
        mStreet = (EditText) v.findViewById(R.id.edit_text_street);
        mCity = (EditText) v.findViewById(R.id.edit_text_city);
        mState = (EditText) v.findViewById(R.id.edit_text_state);
        mZip = (EditText) v.findViewById(R.id.edit_text_zip);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID uuid = (UUID) getArguments().getSerializable(ContactsListFragment.TAG_UUID_EXTRA);
        Log.d(TAG_DEBUG, "onCreate: UUID = " + uuid.toString());
        mContact = ContactsList.get(getContext()).getContact(uuid);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_details_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_edit:
                // if details fragment does not exist
                if (getActivity().findViewById(R.id.details_fragment_container) == null){
                    // start edit activity
                    Intent intent = new Intent(getActivity(), EditContactActivity.class);
                    // add extra from ContactsListActivity
                    intent.putExtra(
                            ContactsListFragment.TAG_UUID_EXTRA,
                            getActivity().getIntent().getSerializableExtra(ContactsListFragment.TAG_UUID_EXTRA)
                    );
                    // add activity to the stack
                    startActivity(intent);
                }
                // if details fragment container exists, replace fragment
                else {
                    FragmentManager fragmentManager = getFragmentManager();
                    EditContactFragment fragment = EditContactFragment.newInstance(mContact.getId());
                    fragmentManager.beginTransaction()
                            .replace(R.id.details_fragment_container, fragment)
                            .commit();
                }


                return true;
            case R.id.item_remove:
                // prompt the user if he wants to remove the item
                FragmentManager manager = getFragmentManager();
                RemoveContactDialogFragment dialogFragment = RemoveContactDialogFragment.newInstance(mContact);

                dialogFragment.show(manager, DIALOG_CONFIRM_DELETE);
                return true;

                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI(){
        Log.d(TAG_DEBUG, "updateUI: called");
        mName.setText(mContact.getName());
        mPhone.setText(mContact.getPhone());
        mEmail.setText(mContact.getEmail());
        mStreet.setText(mContact.getStreet());
        mCity.setText(mContact.getCity());
        mState.setText(mContact.getState());
        mZip.setText(mContact.getZip());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG_DEBUG, "onResume: called");
        if (mContact == null)
            mContact = ContactsList.get(getContext()).getContact((UUID)getActivity().getIntent().getSerializableExtra(ContactsListFragment.TAG_UUID_EXTRA));
        else
            mContact = ContactsList.get(getContext()).getContact(mContact.getId());

        if (getActivity().findViewById(R.id.details_fragment_container) != null){
            ContactsListFragment fragment = (ContactsListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            fragment.updateUI();
        }
        updateUI();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ContactsListFragment.TAG_UUID_EXTRA, mContact.getId().toString());
    }

    public static ContactDetailsFragment newInstance(UUID contactId){

        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ContactsListFragment.TAG_UUID_EXTRA, contactId);
        fragment.setArguments(bundle);

        return fragment;
    }

}
