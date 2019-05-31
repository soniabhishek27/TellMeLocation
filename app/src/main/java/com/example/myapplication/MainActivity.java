package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.CountryDataSource;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final int SPEAK_REQUEST=10;

    TextView text;
    Button btn;

    public static CountryDataSource countryDataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text=findViewById(R.id.textView);
        btn=findViewById(R.id.button);

        Hashtable<String, String > countriesAndMessages = new Hashtable<>();
        countriesAndMessages.put("India","Welcome To India");
        countriesAndMessages.put("France","Welcome To France");
        countriesAndMessages.put("SanFrancisco","Welcome To Sanfrancisco");
        countriesAndMessages.put("United States","Welcome To United States");
        countriesAndMessages.put("Japan","UNIO");
        countriesAndMessages.put("China","Chang");



       countryDataSource = new CountryDataSource(countriesAndMessages);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListenToUser();
            }
        });

        PackageManager packageManager=this.getPackageManager();
        List<ResolveInfo> ListOfInformation=packageManager.queryIntentActivities(

                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),0
        );
        if(ListOfInformation.size()> 0)
        {
            Toast.makeText(MainActivity.this,"Your device does support Speech Recognition"
                    ,Toast.LENGTH_SHORT).show();
           // ListenToUser();

        }
        else
        {
            Toast.makeText(MainActivity.this,"Your device does not support Speech Recognition"
                    ,Toast.LENGTH_SHORT).show();
    }


    }
    public void ListenToUser()
        {
        Intent voiceIntent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Talk to Me!");
        voiceIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent
                .LANGUAGE_MODEL_FREE_FORM);
        voiceIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,10);
        startActivityForResult(voiceIntent,SPEAK_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SPEAK_REQUEST && resultCode==RESULT_OK) {
            ArrayList<String> voiceWords = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            float[] confidentLevels = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);

//     This is commented
//            int index=0;
//            for(String userWords: voiceWords)
//            {
//                if(confidentLevels!=null && index<confidentLevels.length)
//                {
//                    text.setText(userWords + "-"+ confidentLevels[index]);
//                }
//            }


            String countryMatchedWithUserWord = countryDataSource.MatchWithWords(voiceWords, confidentLevels);

            Intent myMapActivity = new Intent(MainActivity.this, MapsActivity.class);
            myMapActivity.putExtra(CountryDataSource.COUNTRY_KEY, countryMatchedWithUserWord);
            startActivity(myMapActivity);


        }

    }
}
