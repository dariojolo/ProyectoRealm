package dariojolo.com.proyectorealm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import dariojolo.com.proyectorealm.R;
import dariojolo.com.proyectorealm.models.Board;

import static android.R.attr.id;

/**
 * Created by rodrigrl on 14/03/2017.
 */

public class BoardAdapter extends BaseAdapter{

    private Context context;
    private List<Board>list;
    private int layout;

    public BoardAdapter(Context context, List<Board> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Board getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.textViewBoardTitles);
            viewHolder.notes = (TextView) convertView.findViewById(R.id.textViewBoardNotes);
            viewHolder.createdAt = (TextView) convertView.findViewById(R.id.textViewBoardDate);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Board board = list.get(position);
        viewHolder.title.setText(board.getTitle());

        int numberOfNotes = board.getNotes().size();
        String textForNotes = (numberOfNotes == 0 ) ? numberOfNotes + " note" : numberOfNotes + " notes";
        viewHolder.notes.setText(textForNotes);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String createdAt = dateFormat.format(board.getCreateAt());
        viewHolder.createdAt.setText(createdAt);

        return convertView;
    }
    public class ViewHolder{
        TextView title;
        TextView notes;
        TextView createdAt;
    }
}
