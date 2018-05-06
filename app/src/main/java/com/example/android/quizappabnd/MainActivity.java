package com.example.android.quizappabnd;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // 1 point per question.  4/4 represents 100%
    private int totalPoints = 0;
    //Attempts
    private int attempts;
    // Number of submissions
    private int submissions = 0;


    private EditText editTextQuestionOne;

    private ImageView image;

    private RadioGroup rgquestion2;

    private RadioButton jones;
    private RadioButton felix;
    private RadioButton joyner;
    private RadioButton kersee;

    private RadioGroup rgquestion3;

    private RadioButton sneakers;
    private RadioButton fleets;
    private RadioButton spikes;

    private CheckBox checkBox100;
    private CheckBox checkBox150;
    private CheckBox checkBox200;
    private CheckBox checkBox1600;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set status bar to transparent -- http://www.androidlearner.com/2017/02/android-transparent-status-bar.html
        //Attribution - Sapan Dang
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){ //  For SDK version of KITKAT or later...
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS); // Set status bar tranparent and extend ViewGroup to edge
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION); // Set bar translucent and ViewGroup to edge
            } // NOTE: Main Root ViewGroup set with bottom padding to allow clearance of navigation buttons

        setContentView(R.layout.activity_main);

        // Initialize various Views----------------------------------------------------

        image = (ImageView) findViewById(R.id.track_image);

        // Use PorterDuff.Mode.Multiply to overlay grey layer
        // https://stackoverflow.com/questions/8193447/i-want-to-add-a-color-filter-to-the-imageview
        // Attribution - Sufian
        image.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        // QUESTION ONE
        editTextQuestionOne = (EditText) findViewById(R.id.question_one_answer);

        // QUESTION TWO
        //Initialize fields for respective radio group
        rgquestion2 = (RadioGroup) findViewById(R.id.question_two_group);

        jones = (RadioButton) findViewById(R.id.question_two_answer_marion_jones);
        felix = (RadioButton) findViewById(R.id.question_two_answer_allyson_felix);
        joyner = (RadioButton) findViewById(R.id.question_two_answer_florence_griffith_joyner);
        kersee = (RadioButton) findViewById(R.id.question_two_answer_jackie_joyner_kersee);

        //QUESTION THREE
        rgquestion3 = (RadioGroup) findViewById(R.id.question_three_group);

        sneakers = (RadioButton) findViewById(R.id.question_three_answer_sneakers);
        fleets = (RadioButton) findViewById(R.id.question_three_answer_fleets);
        spikes = (RadioButton) findViewById(R.id.question_three_answer_spikes);

        // QUESTION FOUR
        checkBox100 = (CheckBox) findViewById(R.id.question_four_check_box_100);
        checkBox150 = (CheckBox) findViewById(R.id.question_four_check_box_150);
        checkBox200 = (CheckBox) findViewById(R.id.question_four_check_box_200);
        checkBox1600 = (CheckBox) findViewById(R.id.question_four_check_box_1600);


    }


    public void answer1() {

        // Capture EditText input
        String firstAnswerSubmitted = editTextQuestionOne.getText().toString();

        // Upon submission, If EditText is empty, throw an error message
        if (TextUtils.isEmpty(firstAnswerSubmitted)) {
            editTextQuestionOne.setError(getResources().getString(R.string.answer_one_toast_message));
            resetCode(); // Reset quiz

            // If answer in EditText matches regex according to answer
        } else if (firstAnswerSubmitted.matches("[Uu]sain\\s[Bb]olt")) {
            attempts++; // Increment attempts
            totalPoints++; // Increment totalPoints for correct answer
        } else {
            attempts++;  // If no match, only increment attempt  <All EditText handled in like manner>
        }

    }


    public void answer2() {

        // If RadioGroup has no selection
        if (rgquestion2.getCheckedRadioButtonId() == -1) {
            // Make Toast message to remind to answer second question
            Toast.makeText(this, getResources().getString(R.string.answer_two_toast_message), Toast.LENGTH_SHORT).show();
            resetCode();

            //  If correct radio button is chosen...
        } else if (joyner.isChecked()) {
            attempts++; // Increment attempts
            totalPoints++; // Increment totalPoints
        } else {
            attempts++;  // If different answer, only increment attempts  <All RadioGroup answers handled in like manner>

        }

    }

    public void answer3() {
        if (rgquestion3.getCheckedRadioButtonId() == -1) {

            Toast.makeText(this, getResources().getString(R.string.answer_three_toast_message), Toast.LENGTH_SHORT).show();
            resetCode();

        } else if (spikes.isChecked()) {
            attempts++;
            totalPoints++;
        } else {
            attempts++;
        }
    }


    public void answer4() {

        // If no Radio Buttons selected...
        if (!checkBox100.isChecked() && !checkBox150.isChecked() && !checkBox200.isChecked() && !checkBox1600.isChecked()) {

            Toast.makeText(this, getResources().getString(R.string.answer_four_toast_message), Toast.LENGTH_SHORT).show();
            resetCode();  // Send Toast message and reset quiz

            // If correct answer given...
        } else if (checkBox100.isChecked() && !checkBox150.isChecked() && checkBox200.isChecked() && checkBox1600.isChecked()) {
            attempts++;
            totalPoints++; // Increment attempts and totalPoints...
        } else {
            attempts++; // otherwise...
        }

    }

    private void results() {

        // For each answer, there must be a completed attempt.  If answer is invalid, next answer doesn't trigger.

        if (attempts == 0) {
            answer1();
        }
        if (attempts == 1) {
            answer2();
        }
        if (attempts == 2) {
            answer3();
        }
        if (attempts == 3) {
            answer4();
        }


    }


    public void submitAnswers(View view) {
        // If answers have not yet been submitted-- pressing submit button with trigger code. If not, nothing happing.  Must reset.
        if (submissions < 1) {
            results();
            Toast.makeText(this, finalMessage(), Toast.LENGTH_LONG).show();
            submissions++;
        }
        finalMessage(); // Trigger message method
    }

    // Reset method connected to reset button
    public void reset(View view) {
        resetCode();
    }

    // Reset method, able to be used without button
    private void resetCode() {
        // Sets totalPoints, attempts, submissions etc
        totalPoints = 0;
        attempts = 0;
        submissions = 0;

        // Selected answers are cleared.
        editTextQuestionOne.getText().clear();
        rgquestion2.clearCheck();
        rgquestion3.clearCheck();


        if (checkBox100.isChecked()) {
            checkBox100.toggle();
        }
        if (checkBox150.isChecked()) {
            checkBox150.toggle();
        }
        if (checkBox200.isChecked()) {
            checkBox200.toggle();
        }
        if (checkBox1600.isChecked()) {
            checkBox1600.toggle();
        }
        // Create ScrollView
        ScrollView sv = (ScrollView) findViewById(R.id.scroll_view);
        sv.fullScroll(ScrollView.FOCUS_UP); //Scroll back to top of, to start again.

    }


    private String finalMessage() {

        String message;

        // Quiz only complete if there are  4 total attempts.  If < 4, then some questions missed/invalid.
        if (totalPoints == 4 && attempts == 4) {
            String four_point_string = getResources().getString(R.string.four_points_message);
            imgToastGold();
            message = four_point_string;
            resetCode();

        } else if (totalPoints == 3 && attempts == 4) {
            String three_point_string = getResources().getString(R.string.three_points_message);
            imgToastSilver();
            message = three_point_string;
            resetCode();

        } else if (totalPoints == 2 && attempts == 4) {
            String two_point_string = getResources().getString(R.string.two_points_message);
            imgToastBronze();
            message = two_point_string;
            resetCode();

        } else if (totalPoints == 1 && attempts == 4) {
            String one_point_string = getResources().getString(R.string.one_point_message);
            imgToastDonut();
            message = one_point_string;
            resetCode();

        } else if (totalPoints == 0 && attempts == 4) {
            String zero_point_string = getResources().getString(R.string.zero_point_message);
            imgToastDonut();
            message = zero_point_string;
            resetCode();

        } else if (totalPoints == 0 && attempts == 0) {
            String zero_point_missing_string = getResources().getString(R.string.zero_point_message_missing);
            message = zero_point_missing_string;
            resetCode();

        } else {

            message = "";
            resetCode();
        }

        return message;

    }

    // Toast a donut is one or zero points, unless mistake
    private void imgToastDonut() {
        Toast toast = new Toast(this);
        ImageView view = new ImageView(this);
        view.setImageResource(R.drawable.donut);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    // Toast a gold medal
    private void imgToastGold() {
        Toast toast = new Toast(this);
        ImageView view = new ImageView(this);
        view.setImageResource(R.drawable.medal_gold);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    // Toast a silver medal
    private void imgToastSilver() {
        Toast toast = new Toast(this);
        ImageView view = new ImageView(this);
        view.setImageResource(R.drawable.medal_silver);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    // Toast a bronze medal
    private void imgToastBronze() {
        Toast toast = new Toast(this);
        ImageView view = new ImageView(this);
        view.setImageResource(R.drawable.medal_bronze);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


}
