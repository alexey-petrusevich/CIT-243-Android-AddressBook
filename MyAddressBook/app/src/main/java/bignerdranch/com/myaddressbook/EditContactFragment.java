package bignerdranch.com.myaddressbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

public class EditContactFragment extends Fragment {

    private Contact mContact;
    private EditText mName;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mStreet;
    private EditText mCity;
    private EditText mState;
    private EditText mZip;
    private Button mOkButton;
    private Button mCancelButton;
    private UUID uuid;
    private final String TAG = "EditContactFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if details fragment container does not exists, read from intent
        if (getActivity().findViewById(R.id.details_fragment_container) == null){
            uuid = (UUID)getActivity().getIntent().getSerializableExtra(ContactsListFragment.TAG_UUID_EXTRA);
        }
        // else read from arguments
        else {
            uuid = (UUID)getArguments().getSerializable(ContactsListFragment.TAG_UUID_EXTRA);
        }

        if (uuid != null) {
            mContact = ContactsList.get(getContext()).getContact(uuid);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.edit_contacts_fragment, container, false);

        mName = (EditText) v.findViewById(R.id.edit_text_name_editable);
        mPhone = (EditText) v.findViewById(R.id.edit_text_phone_editable);
        mEmail = (EditText) v.findViewById(R.id.edit_text_email_editable);
        mStreet = (EditText) v.findViewById(R.id.edit_text_street_editable);
        mCity = (EditText) v.findViewById(R.id.edit_text_city_editable);
        mState = (EditText) v.findViewById(R.id.edit_text_state_editable);
        mZip = (EditText) v.findViewById(R.id.edit_text_zip_editable);

        mOkButton = (Button) v.findViewById(R.id.btn_ok);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Contact contact = new Contact(
                        mName.getText().toString(),
                        mPhone.getText().toString(),
                        mEmail.getText().toString(),
                        mStreet.getText().toString(),
                        mCity.getText().toString(),
                        mState.getText().toString(),
                        mZip.getText().toString()
                        );

                if (uuid != null) contact.setId(uuid);

                Log.d(TAG, "onClick: new contact name = " + mName.getText().toString());

                // if mContacts is null, new contacts is added
                if (mContact == null){
                    ContactsList.get(getContext()).addContact(contact);
                    Log.d(TAG, "onClick: contact added");
                }
                // else update
                else{
                    Log.d(TAG, "onClick: contact updated");
                    ContactsList.get(getContext()).updateContact(contact);
                }

                //updateUI();

//                ContactsListFragment fragment = (ContactsListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
//                fragment.updateUI();

                Log.d(TAG, "onClick: contact updated in DB");

                finish();


            }
        });

        mCancelButton = (Button) v.findViewById(R.id.btn_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        if (mContact != null) updateUI();

        return v;
    }

    public static EditContactFragment newInstance(UUID contactId){

        EditContactFragment fragment = new EditContactFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ContactsListFragment.TAG_UUID_EXTRA, contactId);
        fragment.setArguments(bundle);

        return fragment;
    }

    private void updateUI(){
        mName.setText(mContact.getName());
        mPhone.setText(mContact.getPhone());
        mEmail.setText(mContact.getEmail());
        mStreet.setText(mContact.getStreet());
        mCity.setText(mContact.getCity());
        mState.setText(mContact.getCity());
        mZip.setText(mContact.getZip());
    }

    private void finish(){
        if (getActivity().findViewById(R.id.details_fragment_container) == null){
            getActivity().finish();
        } else {
            FragmentManager fragmentManager = getFragmentManager();
            ContactDetailsFragment fragment = ContactDetailsFragment.newInstance(mContact.getId());
            fragmentManager.beginTransaction()
                    .replace(R.id.details_fragment_container, fragment)
                    .commit();
        }
    }
}
