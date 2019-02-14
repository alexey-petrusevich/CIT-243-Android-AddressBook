package bignerdranch.com.myaddressbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;


public class ContactsListFragment extends Fragment {

    public static final String TAG_UUID_EXTRA = "uuid_extra";
    private static final String TAG_DEBUG = "ContactsListFragment";

    // implementing View Holder for Recycler View
    private class ContactHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mContactName;
        private Contact mContact;

        public ContactHolder(LayoutInflater inflater, ViewGroup parent) {

            super(inflater.inflate(R.layout.contacts_list_item, parent, false));
            itemView.setOnClickListener(this);

            mContactName = (TextView)itemView.findViewById(R.id.contact_list_item);

        }

        public void bind(Contact contact){
            mContact = contact;
            mContactName.setText(mContact.getName());
        }

        @Override
        public void onClick(View v) {
//            // intent for another acitivty
//            Intent intent = new Intent(getActivity(), ContactDetailsActivity.class);
//            Log.d(TAG_DEBUG,"mContact.getId(): " + mContact.getId().toString());
//            intent.putExtra(TAG_UUID_EXTRA, mContact.getId());
//            // add activity to the stack
//            startActivity(intent);
            mCallbacks.onContactSelected(mContact);
        }
    }

    // implementing Adapter for Recycler View
    private class ContactAdapter extends RecyclerView.Adapter<ContactHolder>{

        private List<Contact> mContacts;

        public ContactAdapter(List<Contact> contacts) {
            mContacts = contacts;
        }

        @NonNull
        @Override
        public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ContactHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
            Contact contact = mContacts.get(position);
            holder.bind(contact);
        }

        @Override
        public int getItemCount() {
            return mContacts.size();
        }

        public void setContacts(List<Contact> contacts){
            mContacts = contacts;
        }

    }


    private RecyclerView mRecyclerView;
    private ContactAdapter mAdapter;
    private FloatingActionButton mFloatingActionButton;
    private Callbacks mCallbacks;

    public interface Callbacks{
        void onContactSelected(Contact contact);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.contacts_list_fragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyler_view);
        // recycler view requires a layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFloatingActionButton = (FloatingActionButton) v.findViewById(R.id.btn_add_contact);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start editContactActivity
                Intent intent = new Intent(getActivity(), EditContactActivity.class);
                startActivity(intent);
            }
        });

        updateUI();

        return v;
    }

    public void updateUI(){

        // get an instance of Contacts List
        ContactsList contactsList = ContactsList.get(getActivity());

        // get a list of contacts (through query to the database)
        List<Contact> contacts = contactsList.getContacts();

        if (mAdapter == null){
            mAdapter = new ContactAdapter(contacts);
            mRecyclerView.setAdapter(mAdapter);
        }
        // if adapter has already been created, update contact list
        else {
            mAdapter.setContacts(contacts);
            mAdapter.notifyDataSetChanged();
        }

        // create new adapter and add it to the Recycler View
        mAdapter = new ContactAdapter(contacts);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }


}
