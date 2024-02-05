package com.zaidimarvels.voiceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaActionSound;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.service.media.MediaBrowserService;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.Inet4Address;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private TextToSpeech tts;
    private SpeechRecognizer speechRecog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.RECORD_AUDIO)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                    speechRecog.startListening(intent);
                }
            }
        });
        
        initializeTextToSpeech();
        initializeSpeechRecognizer();
    }

    private void initializeSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecog = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecog.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    List<String> result_arr = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    processResult(result_arr.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void processResult(String result_message) {
        result_message = result_message.toLowerCase();

//        Handle at least four sample cases

//        First: What is your Name?
//        Second: What is the time?
//        Third: Is the earth flat or a sphere?
//        Fourth: Open a browser and open url
        if(result_message.indexOf("what is") != -1){
            if(result_message.indexOf("your name") != -1){
                speak("My Name is Vision. Nice to meet you!");
            }
            if (result_message.indexOf("time") != -1){
                String time_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
                speak("The time is now: " + time_now);
            }
            if (result_message.indexOf("date") != -1){
                String date_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_YEAR);
                speak("The date is now: " + date_now);
            }
        } else if (result_message.indexOf("earth") != -1){
            speak("Don't be silly, The earth is a sphere. As are all other planets and celestial bodies");
        } else if (result_message.contains("open")) {

            if (result_message.contains("whatsapp")) {
                speak("Opening the whatsapp");
                Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.whatsapp");
                startActivity(intent);
            }
            if (result_message.contains("instagram")) {

                speak("Cannot open the instagram.");
                Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                startActivity(intent);
            }
            if (result_message.contains("youtube")) {
                speak("Cannot open the youtube.");
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.youtube.com/"));
                startActivity(intent);
            }
            if (result_message.contains("facebook")) {
                speak("Cannot open the facebook.");
                Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
                startActivity(intent);
            }
            if (result_message.contains("gmail")) {
                speak("Cannot open the gmail.");
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/gmail/"));
                startActivity(intent);
            }
            if (result_message.contains("maps")) {
                speak("Cannot open the maps.");
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/"));
                startActivity(intent);
            }
            if (result_message.contains("google drive")) {
                speak("Cannot open the google drive.");
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://drive.google.com/"));
                startActivity(intent);
            }
            if (result_message.contains("browser")) {
                speak("Cannot open the browser");
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.google.com/"));
                startActivity(intent);
            }
            if (result_message.contains("play store")) {
                speak("Cannot not open play store");
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com"));
                startActivity(intent);
            }
            if (result_message.contains("gallery")) {
                speak("Cannot open the gallery.");
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivity(intent);
            }
            if (result_message.contains("camera")) {
                speak("Cannot open the camera");
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                startActivity(intent);
            }
            if (result_message.contains("contacts")) {
                speak("Cannot open the contacts.");
                Intent intent =new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
            }
            if (result_message.contains("play music")) {
                speak("Cannot open the play music.");
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/music"));
                startActivity(intent);
            }
            if (result_message.contains("settings")) {
                speak("Cannot open the settings.");
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        }

    }

    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (tts.getEngines().size() == 0 ){
                    Toast.makeText(MainActivity.this, getString(R.string.tts_no_engines),Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    tts.setLanguage(Locale.US);
                    speak("Hello there, I am ready to start our conversation");
                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >= 21){
            tts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH,null);
        }
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

    @Override
    protected void onPause() {
        super.onPause();
        tts.shutdown();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Reinitialize the recognizer and tts engines upon resuming from background such as after openning the browser
        initializeSpeechRecognizer();
        initializeTextToSpeech();
    }
}
