package com.example.android.spelletje;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.spelletje.data.databaseHelper;
import com.example.android.spelletje.data.Contract;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etRank;
    private EditText etDelete;
    private EditText etNewName;
    private EditText etOldName;
    private Button btnInsert;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnRecord;
    private Button btnDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //databaseHelper helper = new databaseHelper(this);
        //SQLiteDatabase db = helper.getReadableDatabase();
        //CreateSpeler();
        readData();

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etName = (EditText) findViewById(R.id.editText2);
        etRank = (EditText) findViewById(R.id.etRank);
        etDelete = (EditText) findViewById(R.id.etDelete);
        etNewName = (EditText) findViewById(R.id.etNewPlayer);
        etNewName = (EditText) findViewById(R.id.etOldName);
        btnRecord = (Button) findViewById(R.id.button5);
        btnDataView = (Button) findViewById(R.id.btnDataView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteData(etName.toString());
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateData();
            }
        });

        btnDataView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewDataActivity.class);
                startActivity(intent);
            }
        });

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
            }
        });
    }



    //Deze methode voegt een user toe aan de database
    public void insertData()
    {

        databaseHelper helper = new databaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.spelEntry.COLUMN_NAME, etName.getText().toString());
        values.put(Contract.spelEntry.COLUMN_RANK, etRank.getText().toString());
        long speler_id = db.insert(Contract.spelEntry.TABLE_NAME, null, values);

        if (speler_id == 0){
            Toast.makeText(this, "hij werkt niet.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "hij werkt.", Toast.LENGTH_LONG).show();
        }
    }

    public void DeleteData(String name)
    {
        databaseHelper helper = new databaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        long speler_id = db.delete(Contract.spelEntry.TABLE_NAME, Contract.spelEntry.COLUMN_NAME + " = '" + name + "'", null);

        if (speler_id == 0){
            Toast.makeText(this, "hij werkt niet.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "hij werkt.", Toast.LENGTH_LONG).show();
        }

    }

    public void UpdateData()
    {
        databaseHelper helper = new databaseHelper(this);
        ContentValues cv = new ContentValues();

        cv.put(Contract.spelEntry.COLUMN_NAME, etNewName.toString());

        SQLiteDatabase db = helper.getWritableDatabase();
        long speler_id = db.update(Contract.spelEntry.TABLE_NAME, cv, Contract.spelEntry.COLUMN_NAME + " = '" + etOldName + "'", null );

        if (speler_id == 0){
            Toast.makeText(this, "hij werkt niet.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "hij werkt.", Toast.LENGTH_LONG).show();
        }
    }


    private void readData() {
        databaseHelper helper = new databaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] projection = {Contract.spelEntry.COLUMN_NAME,
                Contract.spelEntry.COLUMN_RANK};
        String selection = Contract.spelEntry._ID + " = ?";
        String[] selectionArgs = {"1"};
        Cursor c = db.query(Contract.spelEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        int i = c.getCount();
        Log.d("Record Count", String.valueOf(i));
    }




    private void CreateSpeler(){
        databaseHelper helper = new databaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.spelEntry.COLUMN_NAME, "Stefan");
        values.put(Contract.spelEntry.COLUMN_RANK, "46");
        long speler_id = db.insert(Contract.spelEntry.TABLE_NAME, null, values);
        Toast.makeText(this, "hij werkt.", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
