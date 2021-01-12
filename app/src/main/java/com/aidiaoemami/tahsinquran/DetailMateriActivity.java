package com.aidiaoemami.tahsinquran;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailMateriActivity extends AppCompatActivity {

    TextView textView, caraBaca, huruf;
    ImageView btnBack;
    ArrayList<String> datahuruf, audio;
    MediaPlayer mySong;
    ListView listView;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_materi);
        textView = findViewById(R.id.hukum);
        btnBack = findViewById(R.id.btnBack);
        caraBaca = findViewById(R.id.cara_baca);
        huruf = findViewById(R.id.huruf);
        listView = findViewById(R.id.listView);
        datahuruf = getIntent().getStringArrayListExtra("huruf");
        ArrayList <String> contoh = getIntent().getStringArrayListExtra("transli");
        audio = getIntent().getStringArrayListExtra("audio");
        adapter = new ArrayAdapter(this, R.layout.custom_textview, contoh);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int resId = getResources().getIdentifier(audio.get(position), "raw", getPackageName());
                if (mySong==null){
                    mySong = MediaPlayer.create(DetailMateriActivity.this, resId);
                    mySong.start();
                }
                else{
                    mySong.stop();
                    mySong = MediaPlayer.create(DetailMateriActivity.this, resId);
                    mySong.start();
                }

            }
        });

        textView.setText(getIntent().getStringExtra("hukum"));
        caraBaca.setText(getIntent().getStringExtra("cara_baca"));

        StringBuilder detailHuruf = new StringBuilder();

        for (int i =0 ; i<datahuruf.size();i++){
            datahuruf.get(i).replaceAll("atau", "");
            detailHuruf.append(datahuruf.get(i)+" ");

        }

        huruf.setText(detailHuruf);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mySong!=null)
            mySong.release();
    }
}
