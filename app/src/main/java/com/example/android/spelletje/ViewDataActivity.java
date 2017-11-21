package com.example.android.spelletje;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.android.spelletje.data.Contract;
import com.example.android.spelletje.data.databaseHelper;

public class ViewDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);

        TextView view = (TextView) findViewById(R.id.AllData);
        view.setText(allData());
    }

    public String allData(){
        databaseHelper helper = new databaseHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();


        String[] projection =
                {
                        Contract.spelEntry._ID,
                        Contract.spelEntry.COLUMN_NAME,
                        Contract.spelEntry.COLUMN_RANK,
                };

        Cursor cursor = db.query( Contract.spelEntry.TABLE_NAME, projection, null, null, null, null, null);

        String View;

        try
        {
            View = "\nThe movies table contains " + cursor.getCount() + " movies.\n\n\n";
            View += Contract.spelEntry._ID + " - " + Contract.spelEntry.COLUMN_NAME + "\n";

            int idColumnIndex = cursor.getColumnIndex(Contract.spelEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(Contract.spelEntry.COLUMN_NAME);
            int rankColumnIndex = cursor.getColumnIndex(Contract.spelEntry.COLUMN_RANK);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentRank = cursor.getString(rankColumnIndex);
                View += "\n" + currentID + " - " + currentName + " " + currentRank;
            }
        }
        finally
        {
            cursor.close();
        }
        return View;
    }
}
