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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        databaseHelper = new MyDatabaseHelper(this, null, null, 1);

        databaseHelper.clearDatabase(TABLE_SCORES);

        boolean test = databaseHelper.hasData();

        Scores score = new Scores(310, 60);
        databaseHelper.addScore(score);

        //finds start date
        test = databaseHelper.hasData();
        //startDate = databaseHelper.findDateWithID(1);
        //Cursor tester = databaseHelper.getDateForGivenID("1");
        //Cursor testCursor = databaseHelper.getDateForGivenID2(1);
        startDate = databaseHelper.getDateForGivenID(0);
        int scoreTemp = databaseHelper.getScoreForGivenDate(310);

//        if(databaseHelper.hasData()){
//            startDate = databaseHelper.findDateWithID(1);
//        }

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

        int scoreSum = 0;
        if(databaseHelper.hasData()) {
            for (int i = todayDayOfYear; i >= startDate; i--) {
                scoreSum += databaseHelper.getScoreForGivenDate(i);
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
        Intent intent = new Intent(this, QuestionsActivity.class);

        String todayDayOfYearStr = Integer.toString(todayDayOfYear);
        intent.putExtra("info", todayDayOfYearStr);

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
        if (scoreSum > 60 && scoreSum <= 120) {
            return R.drawable.stage2;

        }

        return R.drawable.stage1;

    }
}