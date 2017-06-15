package com.example.hoaht.androidarsenal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hoaht.androidarsenal.adapter.ResultAdapter;
import com.example.hoaht.androidarsenal.model.Person;

import java.util.List;

import io.realm.Realm;

/**
 * ResultActivity.
 *
 * @author HoaHT
 */

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Realm realm = Realm.getDefaultInstance();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Person> listPerson = realm.where(Person.class).findAll();
        ResultAdapter adapter = new ResultAdapter(listPerson, null);
        recyclerView.setAdapter(adapter);
    }
}
