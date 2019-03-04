package com108104271860286811966hlru.google.httpsplus.seven_words;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
ArrayAdapter<String> adapter;
Logic logic=new Logic(this);
ListView listView;

String english[];
String translate[];


private final String DB_NAME="english.db";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.ListView);
        logic.onCreateDBAPK(this,DB_NAME);
        logic.initialise();
        english=logic.word;
        translate=logic.translation;

         adapter=new ArrayAdapter<String>(this,R.layout.text_view,R.id.text_View,english);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ((TextView) view).setText(translate[position]);
        myAsyntask asyntask=new myAsyntask(view,english,position);
        asyntask.execute();


            }

}
class myAsyntask extends AsyncTask<Void,Integer,Void>{
    View view;
    String traslate[];
    int position;
myAsyntask(View view,String[] traslate, int i){
    this.traslate=traslate;
    this.view=view;
    position=i;
}
    @Override
    protected Void doInBackground(Void... voids) {
        int i=0;
        while (i<4){
            SystemClock.sleep(500);
            i++;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ((TextView)view).setText(traslate[position]);
    }
}
