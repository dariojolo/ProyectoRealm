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
import dariojolo.com.proyectorealm.models.Note;

/**
 * Created by rodrigrl on 14/03/2017.
 */

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note>list;
    private int layout;

    public NoteAdapter(Context context, List<Note> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Note> getList() {
        return list;
    }

    public void setList(List<Note> list) {
        this.list = list;
    }

    public int getLayout() {
        return layout;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Note getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder vh;
        if (convertView == null){

            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new ViewHolder();
            vh.desc = (TextView)convertView.findViewById(R.id.textViewNoteDesc);
            vh.date = (TextView)convertView.findViewById(R.id.textViewNoteDate);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        Note note = list.get(position);

        vh.desc.setText(note.getDescription());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(note.getCreateAt());
        vh.date.setText(date);


        return convertView;
    }
    public class ViewHolder{
        TextView desc;
        TextView date;
    }
}
