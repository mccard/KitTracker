package edu.uco.mcamposcardoso.kittracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by matheuscamposcardoso on 9/20/16.
 */
public class RowAdapter extends BaseAdapter {

    Context context;
    private Log log;
    private List<Log> listLog;
    private static LayoutInflater inflater = null;

    public RowAdapter(Context context, List<Log> listLog){

        this.context = context;
        this.listLog = listLog;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listLog.size();
    }

    @Override
    public Object getItem(int position) {
        return listLog.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_row, null);

        final TextView txtNome = (TextView) view.findViewById(R.id.txtNome);
        final TextView txtTelefone = (TextView) view.findViewById(R.id.txtTelefone);
        final TextView txtCurso = (TextView) view.findViewById(R.id.txtCurso);
        final TextView txtPeriodo = (TextView) view.findViewById(R.id.txtPeriodo);
        final TextView txtItem = (TextView) view.findViewById(R.id.txtItem);

        log = listLog.get(position);

        txtNome.setText(log.getNome());
        txtTelefone.setText(log.getTelefone());
        txtCurso.setText(log.getCurso());
        txtPeriodo.setText(log.getPeriodo());
        txtItem.setText(log.getItem());

        return view;
    }
}
