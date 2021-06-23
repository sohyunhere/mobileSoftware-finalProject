package dduwcom.mobile.finalreport;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private ArrayList<MovieData> movieDataArrayList = null;
    EditText searchWord;
    private MyAdapter myAdapter;
    private ListView listView;
    MovieDBManager movieDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_movie);
        searchWord = findViewById(R.id.keyword);

        movieDBManager = new MovieDBManager(this);
        movieDataArrayList = movieDBManager.getAllMovie();

        myAdapter = new MyAdapter(SearchActivity.this, R.layout.custom_adapter_view, movieDataArrayList);
        listView = findViewById(R.id.searchView);
        listView.setAdapter(myAdapter);
    }

    public void onClick(View v){
        switch (v.getId()){
            //영화 제목으로 검색
            case R.id.search_title_btn:
                movieDataArrayList.clear();
                String title = searchWord.getText().toString();

                Log.d(TAG, "제목은? " + title);

                movieDataArrayList = movieDBManager.getMovieByTitle(title);
                Log.d(TAG, "이름" + movieDataArrayList.get(0).getDirector());

                myAdapter.updateMovieList(movieDataArrayList);
                break;

                //감독 이름으로 검색
            case R.id.search_director_btn:
                movieDataArrayList.clear();

                String name = searchWord.getText().toString();

                Log.d(TAG, "이름은? " + name);
                movieDataArrayList = movieDBManager.getMovieByDirector(name);
                Log.d(TAG, "제목? " + movieDataArrayList.get(0).getMovieTitle());

                myAdapter.updateMovieList(movieDataArrayList);
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        myAdapter.updateMovieList(movieDataArrayList);
    }
}
