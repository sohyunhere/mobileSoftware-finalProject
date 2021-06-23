package dduwcom.mobile.finalreport;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

public class MovieData {

    private static final String TAG = "MovieData";
    private int _id;
    private String movieTitle;
    private String director;
    private double score;
    private String releaseDate;
    private String content;
    private String character;
    private int imageRes;
    private String imageString;

    Context context;

//    public MovieData(int id, String movieTitle, String director, double score, String releaseDate, String content, String character, int imageRes) {
//        this.movieTitle = movieTitle;
//        this.director = director;
//        this.score = score;
//        this.releaseDate = releaseDate;
//        this.content = content;
//        this.character = character;
//
//    }
    public MovieData(Context context, int _id, String movieTitle, String director, double score, String releaseDate, String content, String character, String imageRes) {
        this.context = context;
        this._id = _id;
        this.movieTitle = movieTitle;
        this.director = director;
        this.score = score;
        this.releaseDate = releaseDate;
        this.content = content;
        this.character = character;
        setImageString(imageRes);

        String packName = "dduwcom.mobile.finalreport";
        int imageInt = context.getResources().getIdentifier(imageRes, "mipmap", packName);
        Log.d(TAG, "int 이미지 아이디" + imageString);

        setImageRes(imageInt);
    }


    public MovieData(int _id, String movieTitle, String director, double score, String releaseDate, String content, String character, int imageRes) {
        this._id = _id;
        this.movieTitle = movieTitle;
        this.director = director;
        this.score = score;
        this.releaseDate = releaseDate;
        this.content = content;
        this.character = character;
        this.imageRes = imageRes;
    }
    public MovieData( String movieTitle, String director, double score, String releaseDate, String content, String character, int imageRes) {
        this.movieTitle = movieTitle;
        this.director = director;
        this.score = score;
        this.releaseDate = releaseDate;
        this.content = content;
        this.character = character;
        this.imageRes = imageRes;
    }

    public void setImageString(String setImage){
        this.imageString = setImage;
    }
    public String getImageString(){
        return imageString;
    }
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
        this.setImageString(String.valueOf(imageRes));
    }

    @Override
    public String toString() {
        String str= String.valueOf(get_id());

        return str;
    }
}
