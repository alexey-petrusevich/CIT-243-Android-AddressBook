package bignerdranch.com.myaddressbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.UUID;

public class EditContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get fragment manager
        FragmentManager fm = getSupportFragmentManager();
        // find fragment container
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            // create new EditContactFragment
            fragment = EditContactFragment.newInstance((UUID)getIntent().getSerializableExtra(ContactsListFragment.TAG_UUID_EXTRA));
            // add fragment into fragment container
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
