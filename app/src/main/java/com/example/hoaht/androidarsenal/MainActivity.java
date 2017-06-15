package com.example.hoaht.androidarsenal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.hoaht.androidarsenal.adapter.ResultAdapter;
import com.example.hoaht.androidarsenal.model.Person;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements ResultAdapter.OnItemClickListener {

    private EditText mEdtName;
    private EditText mEdtAge;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();
        mEdtName = (EditText) findViewById(R.id.edtName);
        mEdtAge = (EditText) findViewById(R.id.edtAge);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnResult = (Button) findViewById(R.id.btnResult);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RealmResults<Person> listPerson = mRealm.where(Person.class).findAll();
        final ResultAdapter adapter = new ResultAdapter(listPerson, this);
        recyclerView.setAdapter(adapter);

        listPerson.addChangeListener(new RealmChangeListener<RealmResults<Person>>() {
            @Override
            public void onChange(RealmResults<Person> persons) {
                adapter.notifyDataSetChanged();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEdtName.getText().toString();
                String age = mEdtAge.getText().toString();
                mRealm.beginTransaction();
                Person person = mRealm.createObject(Person.class);
                person.setName(name);
                person.setAge(age);
                mRealm.commitTransaction();
                clearInput();
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                startActivity(intent);
            }
        });
    }

    private void clearInput() {
        mEdtName.setText("");
        mEdtAge.setText("");
        getWindow().getDecorView().requestFocus();
        hideKeyboard();
    }

    private void hideKeyboard() {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mRealm != null) {
            mRealm.close();
        }
    }

    @Override
    public void onItemClick(final int position) {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you want to delete this row?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final RealmResults<Person> results = mRealm.where(Person.class).findAll();
                        mRealm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                Person person = results.get(position);
                                person.deleteFromRealm();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .create()
                .show();

    }
}
