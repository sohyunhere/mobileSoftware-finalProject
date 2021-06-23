package dduwcom.mobile.finalreport;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.constraintlayout.motion.widget.MotionHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieDBManager {

    private static final String TAG = "MovieDBManager";

    MovieDBHelper movieDBHelper = null;
    Context context;
    ArrayList<MovieData> movieDataArrayList;
    ArrayList<MovieData> searchMovie;


    public MovieDBManager(Context context) {
        movieDBHelper = new MovieDBHelper(context);
        this.context = context;
    }

    public ArrayList<MovieData> getAllMovie() {

        movieDataArrayList = new ArrayList<MovieData>();

        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + MovieDBHelper.TABLE_NAME, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
            String movieTitle = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_MOVIE_TITLE));
            String director = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
            double score = cursor.getDouble(cursor.getColumnIndex(MovieDBHelper.COL_SCORE));
            String releaseDate = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RELEASE));
            String content = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_CONTENT));
            String character = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_CHARACTER));

            String imageString = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_IMAGE));
            Log.d(TAG, imageString + "하하");

            MovieData newMovieData = new MovieData(context, id, movieTitle, director, score, releaseDate, content, character, imageString);
            movieDataArrayList.add(newMovieData);

            Log.d(TAG, "index 구하기" + movieDataArrayList.indexOf(newMovieData));

        }
        Log.d(TAG, "사이즈: " + movieDataArrayList.size());

        cursor.close();
        movieDBHelper.close();


        return movieDataArrayList;
    }

    //add data
    public boolean addNewData(MovieData newMovie) {

        SQLiteDatabase db = movieDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MovieDBHelper.COL_MOVIE_TITLE, newMovie.getMovieTitle());
        values.put(MovieDBHelper.COL_DIRECTOR, newMovie.getDirector());
        values.put(MovieDBHelper.COL_SCORE, newMovie.getScore());
        values.put(MovieDBHelper.COL_RELEASE, newMovie.getReleaseDate());
        values.put(MovieDBHelper.COL_CONTENT, newMovie.getContent());
        values.put(MovieDBHelper.COL_CHARACTER, newMovie.getCharacter());
        values.put(MovieDBHelper.COL_IMAGE, newMovie.getImageRes());

        long count = db.insert(MovieDBHelper.TABLE_NAME, null, values);
        movieDBHelper.close();
        if (count > 0)
            return true;
        else
            return false;

    }

    //remove data
    public boolean removeData(int idx) {

        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();
        String whereClause = MovieDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(idx)};

        Log.d(TAG, "whereClause: " + whereClause);
        Log.d(TAG, "index: " + idx);
        Log.d(TAG, "whereArgs: " + whereArgs[0]);

        long result = sqLiteDatabase.delete(MovieDBHelper.TABLE_NAME, whereClause, whereArgs);
        movieDBHelper.close();

        if (result > 0)
            return true;
        else
            return false;
    }

    //revise data
    public boolean reviseData(int idx, MovieData movie) {
        SQLiteDatabase sqLiteDatabase = movieDBHelper.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put(MovieDBHelper.COL_MOVIE_TITLE, movie.getMovieTitle());
        row.put(MovieDBHelper.COL_DIRECTOR, movie.getDirector());
        row.put(MovieDBHelper.COL_SCORE, movie.getScore());
        row.put(MovieDBHelper.COL_RELEASE, movie.getReleaseDate());
        row.put(MovieDBHelper.COL_CONTENT, movie.getContent());
        row.put(MovieDBHelper.COL_CHARACTER, movie.getCharacter());
        row.put(MovieDBHelper.COL_IMAGE, movie.getImageRes());

        String whereClause = MovieDBHelper.COL_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(movie.get_id())};

        long result = sqLiteDatabase.update(MovieDBHelper.TABLE_NAME, row, whereClause, whereArgs);

        movieDBHelper.close();
        if (result > 0)
            return true;
        else
            return false;

    }

    //영화 이름으로 DB검색
    public ArrayList<MovieData> getMovieByTitle(String title) {
        searchMovie = new ArrayList<>();

        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        MovieData movieData;

        String selection = "movieTitle=?";
        String[] selectArgs = new String[]{title};

        Cursor cursor = db.query(MovieDBHelper.TABLE_NAME, null, selection, selectArgs, null, null, null, null);
        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
            movieData = getMovieByID(id);

            searchMovie.add(movieData);
        }
        cursor.close();
        movieDBHelper.close();

        return searchMovie;
    }


    //    감독 이름으로 DB검색
    public ArrayList<MovieData> getMovieByDirector(String director) {
        searchMovie = new ArrayList<>();

        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        MovieData movieData;

        String selection = "director=?";
        String[] selectArgs = new String[]{director};

        Cursor cursor = db.query(MovieDBHelper.TABLE_NAME, null, selection, selectArgs, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_ID));
            movieData = getMovieByID(id);

            searchMovie.add(movieData);
        }
        cursor.close();
        movieDBHelper.close();

        return searchMovie;
    }

    //id로 DB검색
    //return movieData by ID
    public MovieData getMovieByID(int id) {
        SQLiteDatabase db = movieDBHelper.getReadableDatabase();
        MovieData movieData = null;

        String selection = "_id=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursor = db.query(MovieDBHelper.TABLE_NAME, null, selection, selectionArgs, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String movieTitle = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_MOVIE_TITLE));
                String director = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_DIRECTOR));
                double score = cursor.getDouble(cursor.getColumnIndex(MovieDBHelper.COL_SCORE));
                String releaseDate = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_RELEASE));
                String content = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_CONTENT));
                String character = cursor.getString(cursor.getColumnIndex(MovieDBHelper.COL_CHARACTER));
                int imageRes = cursor.getInt(cursor.getColumnIndex(MovieDBHelper.COL_IMAGE));

                movieData = new MovieData(id, movieTitle, director, score, releaseDate, content, character, imageRes);

            }
        }

        cursor.close();
        movieDBHelper.close();

        return movieData;

    }
}
