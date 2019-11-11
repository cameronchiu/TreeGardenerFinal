package com.example.tree;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import static com.example.tree.MainActivity.caloriesConsumedGoal;
import static com.example.tree.MainActivity.cupsOfWaterGoal;
import static com.example.tree.MainActivity.hoursOfExerciseGoal;
import static com.example.tree.MainActivity.hoursOfSleepGoal;
import static com.example.tree.MainActivity.numberOfMealsGoal;
import static com.example.tree.MainActivity.stepsGoal;

public class QuestionsActivity extends AppCompatActivity {

    MyDatabaseHelper databaseHelper;
    private int todayScore = 0;
    private int todayDayOfYearInt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        databaseHelper = new MyDatabaseHelper(this, null, null, 1);

        Intent intent = getIntent();
        String todayDayOfYear = intent.getStringExtra("info");

        todayDayOfYearInt = Integer.valueOf(todayDayOfYear);

        if(databaseHelper.hasData()) {
            todayScore = databaseHelper.getScoreForGivenDate(todayDayOfYearInt);
        }


    }

    public void submit(View v) {
        // sends you back to Main Activity
        // gets score name and value from edittext and uses it to create a new Scores object
        // it adds this element to the database and then reprints the database to show the change

        EditText steps = (EditText) findViewById(R.id.editTextSteps);
        EditText water = (EditText) findViewById(R.id.editTextCups);
        EditText meals = (EditText) findViewById(R.id.editTextMeals);
        EditText calories = (EditText) findViewById(R.id.editTextCalories);
        EditText sleep = (EditText) findViewById(R.id.editTextSleep);
        EditText exercise = (EditText) findViewById(R.id.editTextExercise);


        int inputSteps = Integer.parseInt(steps.getText().toString());
        int inputCupsOfWater = Integer.parseInt(water.getText().toString());
        int inputMealsEaten = Integer.parseInt(meals.getText().toString());
        int inputCaloriesConsumed = Integer.parseInt(calories.getText().toString());
        int inputHoursOfSleep = Integer.parseInt(sleep.getText().toString());
        int inputHoursOfExercise = Integer.parseInt(exercise.getText().toString());



        if(inputSteps>=stepsGoal&&inputSteps<stepsGoal*1.5){
            todayScore += 5;
        }
        if(inputSteps>stepsGoal*1.5){
            todayScore += 10;
        }
        if(inputCupsOfWater>=cupsOfWaterGoal&&inputCupsOfWater<cupsOfWaterGoal*1.5){
            todayScore += 5;
        }
        if(inputCupsOfWater>cupsOfWaterGoal*1.5){
            todayScore += 10;
        }
        if(inputMealsEaten>=numberOfMealsGoal&&inputMealsEaten<numberOfMealsGoal*1.5){
            todayScore += 5;
        }
        if(inputMealsEaten>numberOfMealsGoal*1.5){
            todayScore += 10;
        }
        if(inputCaloriesConsumed>=caloriesConsumedGoal&&inputSteps<caloriesConsumedGoal*1.5){
            todayScore += 5;
        }
        if(inputCaloriesConsumed>caloriesConsumedGoal*1.5){
            todayScore += 10;
        }
        if(inputHoursOfSleep>=hoursOfSleepGoal&&inputHoursOfSleep<hoursOfSleepGoal*1.5){
            todayScore += 5;
        }
        if(inputHoursOfSleep>hoursOfSleepGoal*1.5){
            todayScore += 10;
        }
        if(inputHoursOfExercise>=hoursOfExerciseGoal&&inputHoursOfExercise<hoursOfExerciseGoal*1.5){
            todayScore += 5;
        }
        if(inputHoursOfExercise>hoursOfExerciseGoal*1.5){
            todayScore += 10;
        }

        Scores score = new Scores(todayScore, todayDayOfYearInt);

        databaseHelper.addScore(score);

        Intent intent2 = new Intent(this, MainActivity.class);
        startActivity(intent2);
    }
}
