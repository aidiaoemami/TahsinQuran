package com.aidiaoemami.tahsinquran;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class TahsinActivity extends AppCompatActivity {

    Button btnSearch;
    EditText et_pattern;
    ImageView btnBack;
    String pattern;
    TextView textlafadz;
    ArrayList<String> dataSurat;
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    DataModel dataModel;
    ArrayAdapter adapter;
    Tahsin lafadzh;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahsin);

        btnSearch = findViewById(R.id.btn_search);
        et_pattern = findViewById(R.id.et_pattern);
//        textlafadz = findViewById(R.id.lafadz);
        btnBack = findViewById(R.id.btnBack);

        helper = new DataHelper(getApplicationContext());
        db = helper.getReadableDatabase();
        dataModel = new DataModel(db);

        Random r = new Random();
        Cursor max = db.query("tahsin", new String[]{"MAX(id) AS id"}, null, null, null, null, null);
        max.moveToFirst(); // to move the cursor to first record
        int index = max.getColumnIndex("id");
        String data = max.getString(index);// use the data type of the column or use String itself you can parse i
        int maxValue = Integer.parseInt(data);

        Cursor min = db.query("tahsin", new String[]{"min(id) AS id"}, null, null, null, null, null);
        min.moveToFirst(); // to move the cursor to first record
        int indexMin = min.getColumnIndex("id");
        String dataMin = min.getString(indexMin);// use the data type of the column or use String itself you can parse i
        int minValue = Integer.parseInt(dataMin);

        int id = r.nextInt(maxValue - minValue) + minValue;
        lafadzh = dataModel.selectTahsinByID(id);
        final String textLafadzh = lafadzh.getLafadz();
        int id_tahsin = lafadzh.getId();
//        textlafadz.setText(textLafadzh);
        SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("id_tahsin", id_tahsin);
        editor.apply();


//        voiceBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                speak();
//            }
//        });
        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                            Intent intent = new Intent (TahsinActivity.this, ResultActivity.class);
                                            intent.putExtra("pattern", et_pattern.getText().toString());
                                             Log.d("pattern", et_pattern.getText().toString());
                                            startActivity(intent);
                                         }
                                     }

        );
    }

    private void speak() {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "id-ID");
//        i.putExtra(RecognizerIntent.EXTRA_PROMPT, textlafadz);
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Bacakan sesuai lafadz");

        try {
            startActivityForResult(i, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if (resultCode == RESULT_OK && null!=data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Intent i = new Intent(TahsinActivity.this, ResultActivity.class);
                    i.putExtra("text", result);
                    //textView.setText(result.get(0));
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    TahsinActivity.this.finish();
                }
                break;
            }
        }
    }
}
