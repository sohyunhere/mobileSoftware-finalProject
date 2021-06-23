package dduwcom.mobile.finalreport;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReviseActivity extends AppCompatActivity {

    private static final String TAG = "ReviseActivity";

    private ArrayList<MovieData> movieDataArrayList = new ArrayList<>();
    int index;
    ImageView image;
    EditText title;
    EditText director;
    EditText score;
    EditText release;

    EditText content;
    EditText character;
    private int imageRes;

    MovieDBManager movieDBManager = new MovieDBManager(this);
    MovieData movieData;

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.revise_movie);
        movieDataArrayList.addAll(movieDBManager.getAllMovie());

        index = getIntent().getIntExtra("index" , -1);

        Log.d(TAG, "int는 이거고: "+ index);
        movieData = movieDataArrayList.get(index);
        Log.d(TAG, "아하:" + movieDataArrayList.get(index));

        image = findViewById(R.id.up_image);
        title = findViewById(R.id.up_title);
        director = findViewById(R.id.up_director);
        score = findViewById(R.id.up_score);
        release = findViewById(R.id.up_release);
        content = findViewById(R.id.up_content);
        character = findViewById(R.id.up_character);

        String imageString = movieData.getImageString();

        String packName = "dduwcom.mobile.finalreport";

        Log.d(TAG, "image Resource: " + movieData.getImageString());

        imageRes = this.getResources().getIdentifier(imageString, "mipmap", packName);

        Log.d(TAG, "image Resource: " + imageRes);

        image.setImageResource(imageRes);

        title.setText(movieData.getMovieTitle());
        director.setText(movieData.getDirector());
        score.setText(String.valueOf(movieData.getScore()));
        release.setText(movieData.getReleaseDate());
        content.setText(movieData.getContent());
        character.setText(movieData.getCharacter());

        movieDBManager = new MovieDBManager(this);

        EditText et_Date = (EditText) findViewById(R.id.up_release);
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReviseActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.revise_btn:
                if(!(title.getText().toString().isEmpty() && director.getText().toString().isEmpty() && score.getText().toString().isEmpty() &&
                        release.getText().toString().isEmpty() && content.getText().toString().isEmpty() && character.getText().toString().isEmpty())) {

                    movieData.setMovieTitle(title.getText().toString());
                    movieData.setImageRes(imageRes);
                    movieData.setDirector(director.getText().toString());

                    movieData.setScore(Double.parseDouble(score.getText().toString()));
                    movieData.setReleaseDate(release.getText().toString());
                    movieData.setContent(content.getText().toString());
                    movieData.setCharacter(character.getText().toString());

                    boolean result = movieDBManager.reviseData(index, movieData);

                    if (result) {
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK);
                        finish();
                    }
                }
                else{
                    Log.d(TAG, "No NO");
                    Toast.makeText(ReviseActivity.this, "영화 수정을 실패 했습니다.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                }
                break;
        }
    }
    private void updateLabel() {
        String myFormat = "yyyy.MM.dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.up_release);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }
}
