package dduwcom.mobile.finalreport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    // 과제명: 영화 리뷰(정리) 관리 앱
    // 분반: 01 분반
    // 학번: 20190963 성명: 박소현
    // 제출일: 2021년 6월 23일

    private static final String TAG = "MainActivity";

    private ArrayList<MovieData> movieDataArrayList = null;
    private MyAdapter myAdapter;
    private ListView listView;
    MovieDBManager movieDBManager;

    final static int ADD_ACTIVITY_CODE = 100;
    final static int REVISE_ACTIVITY_CODE = 200;
    Intent intent;
    int selectedPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieDBManager = new MovieDBManager(this);
        movieDataArrayList = new ArrayList<>();

        myAdapter = new MyAdapter(this, R.layout.custom_adapter_view, movieDataArrayList);
        listView = findViewById(R.id.customListView);
        listView.setAdapter(myAdapter);

        //항목클릭하면 수정하기
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedPos = position;

                intent = new Intent(MainActivity.this, ReviseActivity.class);
                Log.d(TAG, "고른거 : " + selectedPos);
                intent.putExtra("index", selectedPos);

                startActivityForResult(intent, REVISE_ACTIVITY_CODE);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                String whichMovie = movieDataArrayList.get(pos).getMovieTitle();

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("영화 리뷰 삭제")
                        .setMessage(whichMovie + "를 삭제하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                boolean result = movieDBManager.removeData(movieDataArrayList.get(pos).get_id());


                                Log.d(TAG, "position: "+ pos);
                                Log.d(TAG, "result: "+ result);

                                Log.d(TAG,"movieDataArrayList.get(pos): "  +movieDataArrayList.get(pos).get_id());

                                if(result){
                                    movieDataArrayList.clear();
                                    movieDataArrayList.addAll(movieDBManager.getAllMovie());
                                    myAdapter.notifyDataSetChanged();
                                }

                                else{
                                    Toast.makeText(MainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(false)
                        .show();

               return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        movieDataArrayList.clear();
        movieDataArrayList.addAll(movieDBManager.getAllMovie());
        myAdapter.notifyDataSetChanged();
    }

    //옵션 메뉴 구성
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //옵션 메뉴 클릭
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        AlertDialog.Builder builder;
        switch (item.getItemId()){
            case R.id.add_review:
                intent = new Intent(MainActivity.this, AddActivity.class);

                startActivityForResult(intent, ADD_ACTIVITY_CODE);
                break;
            case R.id.introduce:
                builder = new AlertDialog.Builder(this);

                builder.setTitle("개발자 소개")
                        .setMessage("모바일 소프트웨어 01분반\n 컴퓨터학과 3학년\n 20190963 박소현")
                        .setIcon(R.mipmap.face)
                        .setPositiveButton("확인", null)
                        .setNegativeButton("취소", null)
                        .setCancelable(true)
                        .show();

                break;
            case R.id.close_app:
                builder = new AlertDialog.Builder(this);

                builder.setTitle("앱 종료")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setNegativeButton("취소", null)
                        .setCancelable(true)
                        .show();
                break;

                //긴급전화
            case R.id.emergency:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: 112"));
                startActivity(intent);
                break;

                //검색기능
            case R.id.search_movie:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;

                //현재 상영중인 영화 찾기
            case R.id.showing:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://search.naver.com/search.naver?where=nexearch&sm=top_sug.pre&fbm=1&acr=1&acq=%ED%98%84%EC%9E%AC+%EC%83%81%EC%97%AC&qdt=0&ie=utf8&query=%ED%98%84%EC%9E%AC+%EC%83%81%EC%98%81%EC%A4%91%EC%9D%B8+%EC%98%81%ED%99%94"));
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ADD_ACTIVITY_CODE: //영화 추가하기
                if (resultCode == RESULT_OK) {
                    myAdapter.notifyDataSetChanged();
                }
                break;
            case REVISE_ACTIVITY_CODE: //영화 수정하기
                if(resultCode == RESULT_OK){
                    myAdapter.notifyDataSetChanged();
                }
                break;
        }
    }


}