package dduwcom.mobile.finalreport;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class MovieDBHelper extends SQLiteOpenHelper {
    final static String TAG = "MovieDBHelper";
    final static String DB_NAME = "movie.db";
    public final static String TABLE_NAME = "movie_table";
    public final static String COL_ID = "_id";
    public final static String COL_MOVIE_TITLE = "movieTitle";
    public final static String COL_DIRECTOR = "director";
    public final static String COL_SCORE = "score";
    public final static String COL_RELEASE = "releaseDate";
    public final static String COL_CONTENT = "content";
    public final static String COL_CHARACTER = "character";
    public final static String COL_IMAGE = "imageRes";

    public MovieDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " integer primary key autoincrement, " +
                COL_MOVIE_TITLE + " TEXT, " + COL_DIRECTOR + " TEXT, " + COL_SCORE + " TEXT, " + COL_RELEASE + " TEXT, "
                + COL_CONTENT + " TEXT, " + COL_CHARACTER + " TEXT, " + COL_IMAGE + " TEXT)";

        Log.d(TAG, sql);
        db.execSQL(sql);

        db.execSQL("insert into " + TABLE_NAME + " values (null, '특종: 량첸살인기', '노덕', 3.5, '2015.10.22', " +
                        "'뉴스라는 게 그런 거잖아. 뭐가 진짜고 가려내는 거. 그거 우리 일 아니야. 보는 사람들 일이지 그들이 진짜라고 믿으면 그게 진실인 거야', "+
                "'이미숙, 조정석, 이하나', 'scoop');");

        db.execSQL("insert into " + TABLE_NAME + " values (null, '핵소고지', '멜 깁슨', 3.8, '2017.02.22', " +
                "'영화는 2차 세계대전 당시 오키나와 전투에서 무기 없이 총 75명의 부상병을 구한 의무병 데스몬드 T. 도스를 다룬 내용이다.', "+
                "'앤드류 가필드', 'hacksaw');");

        db.execSQL("insert into " + TABLE_NAME + " values (null, '더 스파이', '도미닉 쿡', 4.1, '2021.04.28', " +
                "'1960년 냉전시대, 실화를 바탕으로 한 영화이다. \n" +
                " 이전에 영화를 따로 알아보고 간 것이 아니라 베네딕트 컴버배치가 나온 영화를 골라 본 건인데 연기가 너무 좋았어서 집에 오늘 길에도 계속해서 생각이 났다. \n" +
                "영화가 실화를 기반으로 해서 그런지 다른 스파이물과 비교하면 액션도 없고 스릴도 엄청 크지 않지만 다른 영화에서 느낄 수 없는 감정을 느낄 수 있다.\n " +
                "이런 영화는 영화관이 아닌 공간에서 보면 지루할 수 있어서 되도록 영화관에서 보는 걸 추천한다.', "+
                "'베네딕트 컴버배치', 'courier');");

        db.execSQL("insert into " + TABLE_NAME + " values (null, '글래스', 'M.나이트 샤밀란', 3.2, '2019.01.17', " +
                "'영화는 제임스 맥어보이의 연기가 소름돋았고 스토리가 반전의 반전이라서 조금 생각해야했다.', "+
                "'제임스 맥어보이', 'glass');");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
