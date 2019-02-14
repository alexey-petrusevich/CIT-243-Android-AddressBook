package bignerdranch.com.myaddressbook;


import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements ContactsListFragment.Callbacks{

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        // add entries per exam if no entries found
        if (ContactsList.get(getBaseContext()).getContacts().size() == 0){

            ContactsList.get(getBaseContext()).addContact(new Contact(
                    "Tom Brady",
                    "555-555-1212",
                    "TB12@Patriots.com",
                    "1 Patriot Place",
                    "Foxboro",
                    "Mass",
                    "02134"
            ));

            ContactsList.get(getBaseContext()).addContact(new Contact(
                    "David Ortiz",
                    "617-555-1212",
                    "DOrtiz@RedSox.com",
                    "1 Landsdown Street",
                    "Boston",
                    "Mass",
                    "02135"
            ));

            ContactsList.get(getBaseContext()).addContact(new Contact(
                    "Patrice Bergeron",
                    "978-555-1212",
                    "PBergeron@BostonBruins.com",
                    "1 Causeway Street",
                    "Boston",
                    "Mass",
                    "02136"
            ));

        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            // create new ContactsListFragment
            fragment = new ContactsListFragment();
            // add fragment into fragment container
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onContactSelected(Contact contact) {

        if (findViewById(R.id.details_fragment_container) == null){

            Intent intent = new Intent(this, ContactDetailsActivity.class);
            intent.putExtra(ContactsListFragment.TAG_UUID_EXTRA, contact.getId());
            startActivity(intent);

        } else {

            Fragment fragment = ContactDetailsFragment.newInstance(contact.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
