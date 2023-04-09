package dev.alamalbank.amb.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import dev.alamalbank.amb.R;
import dev.alamalbank.amb.model.Spennar;

import java.util.List;


public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {

    Context context;
    List<Spennar> Hesas;

    public SpinnerAdapter(Context context, List<Spennar> Hesas) {
        this.Hesas = Hesas;
        this.context = context;
    }
    @Override
    public int getCount() {
        return (null != Hesas ? Hesas.size() : 0);
    }

    @Override
    public Object getItem(int i) {
        return Hesas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =  View.inflate(context, R.layout.spaner_main, null);
        TextView textView = (TextView) view.findViewById(R.id.main);
        textView.setText(Hesas.get(position).getName());
        return textView;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view;
        view =  View.inflate(context, R.layout.custom_dropdown, null);
        final CheckedTextView textView = (CheckedTextView) view.findViewById(R.id.dd);
        textView.setText(Hesas.get(position).getName());
        return view;
    }
}
