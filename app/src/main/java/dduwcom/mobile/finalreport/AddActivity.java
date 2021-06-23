package dduwcom.mobile.finalreport;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";
    ImageView image;
    EditText title;
    EditText director;
    EditText score;
    EditText release;

    EditText content;
    EditText character;
    private int imageRes;

    MovieDBManager movieDBManager;
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
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_movie);

        image = findViewById(R.id.add_image);
        title = findViewById(R.id.add_title);
        director = findViewById(R.id.add_director);
        score = findViewById(R.id.add_score);
        release = findViewById(R.id.add_release);
        content = findViewById(R.id.add_content);
        character = findViewById(R.id.add_character);

        imageRes = R.mipmap.ic_launcher;

        movieDBManager = new MovieDBManager(this);

        EditText et_Date = (EditText) findViewById(R.id.add_release);
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddActivity.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.add_btn:
                if(!(title.getText().toString().isEmpty() && director.getText().toString().isEmpty() && score.getText().toString().isEmpty() &&
                    release.getText().toString().isEmpty() && content.getText().toString().isEmpty() && character.getText().toString().isEmpty())) {


                    movieData = new MovieData(title.getText().toString(), director.getText().toString(), Double.parseDouble(score.getText().toString()),
                            release.getText().toString(), content.getText().toString(), character.getText().toString(), imageRes);


                    boolean result = movieDBManager.addNewData(movieData);
                    Log.d(TAG, "ㅇk : "+ result);

                    if (result) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("resultdata", "addactivity");
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                }else{
                    Log.d(TAG, "No NO");
                    Toast.makeText(AddActivity.this, "영화 추가를 실패 했습니다.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                }

                break;
            case R.id.cancel_btn:
                setResult(RESULT_CANCELED);
                finish();
                break;

        }

    }

    private void updateLabel() {
        String myFormat = "yyyy.MM.dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.add_release);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

}
