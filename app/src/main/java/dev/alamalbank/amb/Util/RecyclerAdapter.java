package dev.alamalbank.amb.Util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dev.alamalbank.amb.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    final Context context;
    final ArrayList<ListViewBean> listViewBeans;
    private final Typeface myFont;
    private boolean on_attach = true;

    public RecyclerAdapter(Context context, ArrayList<ListViewBean> listViewBeans) {
        this.context = context;
        this.listViewBeans = listViewBeans;
        this.myFont = Typeface.createFromAsset((context).getAssets(), "font/jannat_bold.otf");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("latef","listViewBeans"+listViewBeans.toString());

        try {
            holder.icon.setImageResource(listViewBeans.get(position).getIcon());
            Log.e("latef","position"+position);
        }catch (Exception e){
            holder.icon.setImageResource(R.drawable.go);
        }
        holder.title.setText(listViewBeans.get(position).getTitle());
        holder.title.setTypeface(myFont);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.d(Utils.APP_TAG, "onScrollStateChanged: Called " + newState);
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return listViewBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView icon;
        final TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.im_view);
            title = itemView.findViewById(R.id.tv_view);

        }
    }
}
