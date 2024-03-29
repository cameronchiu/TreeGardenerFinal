package com.example.tree;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.tree.MyDatabaseHelper.TABLE_SCORES;

/**
 * Things to work on:
 * Disable user from growing tree without all filled (or put value of 0)
 *
 */


public class MainActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText scoreEditText;
    TextView displayTextView;
    MyDatabaseHelper databaseHelper;
    DateFormat dateFormat = new SimpleDateFormat("d");
    DateFormat monthFormat = new SimpleDateFormat("MM");

    //Goals
    public static int stepsGoal = 10000;
    public static int cupsOfWaterGoal = 8;
    public static int caloriesConsumedGoal = 2000;
    public static int numberOfMealsGoal = 3;
    public static int hoursOfSleepGoal = 8;
    public static int hoursOfExerciseGoal = 1;

    //Current variables
    private int todayStage;
    private int todayDayOfYear;

    //Database variables
    private int startDate;

    private int currentID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseHelper = new MyDatabaseHelper(this, null, null, 1);

        boolean test = databaseHelper.hasData();

//        databaseHelper.clearDatabase(TABLE_SCORES);
//        Scores score1 = new Scores(0,319, 60);
////        Scores score2 = new Scores(1,314, 64);
////        Scores score3 = new Scores(2,315, 62);
//        databaseHelper.addScore(score1);
//        databaseHelper.addScore(score2);
//        databaseHelper.addScore(score3);
//        int dateTemp = databaseHelper.getDateForGivenID(2);
//        int scoreTemp = databaseHelper.getScoreForGivenID(2);
//        int idTemp = databaseHelper.getIDForGivenDate(313);



        int count = databaseHelper.getProfilesCount(TABLE_SCORES);
        int[] phase = {R.drawable.fill0,R.drawable.fill1,R.drawable.fill2,R.drawable.fill3,R.drawable.fill4,R.drawable.fill5,R.drawable.fill6,R.drawable.fill7};
        ImageView currentphase = (ImageView) findViewById(R.id.imageView5);
        currentphase.setImageResource(phase[count]);







        //Sets home screen button to Sunday, Monday, Tuesday, etc.
        DateFormat dayFormat = new SimpleDateFormat("EEEE");
        String day = dayFormat.format(Calendar.getInstance().getTime());
        Button startDay = (Button) findViewById(R.id.buttonDayOfWeek);
        startDay.setText(day);


        //gets current day out of 365 (todayDayOfYear)
        String currentMonthString = monthFormat.format(Calendar.getInstance().getTime());
        int currentMonthInt = Integer.valueOf(currentMonthString);
        String currentDayOfMonthString = dateFormat.format(Calendar.getInstance().getTime());
        int currentDayOfMonthInt = Integer.valueOf(currentDayOfMonthString);

        todayDayOfYear = outOf365(currentDayOfMonthInt,currentMonthInt);

        if(databaseHelper.hasData()){
            startDate = databaseHelper.getDateForGivenID(0);
            currentID = databaseHelper.getIDForGivenDate(todayDayOfYear-1) +1;
        }
        else{
            startDate = todayDayOfYear;
            currentID = 0;
        }


        int scoreSum = 0;
        if(currentID!=0) {
            for (int i = currentID; i > 0; i--) {
                scoreSum += databaseHelper.getScoreForGivenID(i-1);
            }
        }
        todayStage = calculateStage(scoreSum);

        //grows tree upon opening
        ImageView treeImage = (ImageView) findViewById(R.id.imageViewTree);
        treeImage.setImageResource(todayStage);

    }


    public void goToQuestions(View v){
        /*
        Sends information from Main to Questions
         */

        Button myButton = (Button) findViewById(R.id.buttonDayOfWeek);
        myButton.setEnabled(false);

        Intent intent = new Intent(this, QuestionsActivity.class);

        String todayDayOfYearStr = Integer.toString(todayDayOfYear);
        String currentIDStr = Integer.toString(currentID);
        intent.putExtra("info1", todayDayOfYearStr);
        intent.putExtra("info2", currentIDStr);

        startActivity(intent);

    }


    public int outOf365 (int dayOutOf31, int month){

        int dayOutOf365 = 0;

        if(month==1){
            dayOutOf365 = dayOutOf31;
        }
        if(month==2){
            dayOutOf365 = 31+dayOutOf31;
        }
        if(month==3){
            dayOutOf365 = 60+dayOutOf31;
        }
        if(month==4){
            dayOutOf365 = 90+dayOutOf31;
        }
        if(month==5){
            dayOutOf365 = 121+dayOutOf31;
        }
        if(month==6){
            dayOutOf365 = 151+dayOutOf31;
        }
        if(month==7){
            dayOutOf365 = 182+dayOutOf31;
        }
        if(month==8){
            dayOutOf365 = 212+dayOutOf31;
        }
        if(month==9){
            dayOutOf365 = 243+dayOutOf31;
        }
        if(month==10){
            dayOutOf365 = 273+dayOutOf31;
        }
        if(month==11){
            dayOutOf365 = 304+dayOutOf31;
        }
        if(month==12){
            dayOutOf365 = 334+dayOutOf31;
        }

        return dayOutOf365;
    }


    public int calculateStage(int scoreSum) {
        if (scoreSum > 780) {
            return R.drawable.stage14;

        }
        if (scoreSum > 720 && scoreSum <= 780) {
            return R.drawable.stage13;

        }
        if (scoreSum > 660 && scoreSum <= 720) {
            return R.drawable.stage12;

        }
        if (scoreSum > 600 && scoreSum <= 660) {
            return R.drawable.stage11;

        }
        if (scoreSum > 540 && scoreSum <= 600) {
            return R.drawable.stage10;

        }
        if (scoreSum > 480 && scoreSum <= 540) {
            return R.drawable.stage9;

        }
        if (scoreSum > 420 && scoreSum <= 480) {
            return R.drawable.stage8;

        }
        if (scoreSum > 360 && scoreSum <= 420) {
            return R.drawable.stage7;

        }
        if (scoreSum > 300 && scoreSum <= 360) {
            return R.drawable.stage6;

        }
        if (scoreSum > 240 && scoreSum <= 300) {
            return R.drawable.stage5;

        }
        if (scoreSum > 180 && scoreSum <= 240) {
            return R.drawable.stage4;

        }
        if (scoreSum > 120 && scoreSum <= 180) {
            return R.drawable.stage3;

        }
        if (scoreSum >= 60 && scoreSum <= 120) {
            return R.drawable.stage2;

        }

        return R.drawable.stage1;

    }

    public void restart(View v){
        databaseHelper.clearDatabase(TABLE_SCORES);
        ImageView currentphase = (ImageView) findViewById(R.id.imageView5);
        currentphase.setImageResource(R.drawable.fill0);
        ImageView treeImage = (ImageView) findViewById(R.id.imageViewTree);
        treeImage.setImageResource(R.drawable.stage1);
        Toast.makeText(this, "Your week has been restarted! Click on the button to start a new week!",Toast.LENGTH_LONG).show();

    }
}