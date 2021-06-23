package dduwcom.mobile.finalreport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<MovieData> movieDataArrayList;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, int layout, ArrayList<MovieData> movieDataArrayList) {
        this.context = context;
        this.layout = layout;
        this.movieDataArrayList = movieDataArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return movieDataArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return movieDataArrayList.get(position).get_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final  int pos = position;
        ViewHolder holder;

        if(convertView == null) {
            convertView = layoutInflater.inflate(layout, parent, false);

            holder = new ViewHolder();
            holder.image = convertView.findViewById(R.id.image);
            holder.title = convertView.findViewById(R.id.movieTitle);
            holder.director = convertView.findViewById(R.id.director);
            holder.score = convertView.findViewById(R.id.score);
            holder.release = convertView.findViewById(R.id.release);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.image.setImageResource(movieDataArrayList.get(pos).getImageRes());
        holder.title.setText(movieDataArrayList.get(pos).getMovieTitle());
        holder.director.setText(movieDataArrayList.get(pos).getDirector());
        holder.score.setText(String.valueOf(movieDataArrayList.get(pos).getScore()));
        holder.release.setText(movieDataArrayList.get(pos).getReleaseDate());

        return convertView;
    }
    static class ViewHolder{
        ImageView image;
        TextView title;
        TextView director;
        TextView score;
        TextView release;
    }

    public void updateMovieList(ArrayList<MovieData> list){
        movieDataArrayList = list;
        notifyDataSetChanged();
    }
}
