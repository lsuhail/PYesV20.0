package dev.alamalbank.amb.Util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import dev.alamalbank.amb.R;

public class ListViewRowAdapter extends ArrayAdapter<ListViewBean> {

    private final Activity activity;
    private final int resource;
    private final List<ListViewBean> items;
    private final String type;
    private final String lang;
    private final Typeface myFont;

    public ListViewRowAdapter(Activity act, int resource, List<ListViewBean> arrayList, String type, String lang) {
        super(act, resource, arrayList);

        this.activity = act;
        this.resource = resource;
        this.items = arrayList;
        this.type = type;
        this.lang = lang;
        this.myFont = Typeface.createFromAsset(((Context) act).getAssets(), "font/jannat_bold.otf");
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(resource, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if ((items == null) || ((position + 1) > items.size())) {
            return view;
        }

        ListViewBean objBean = items.get(position);
        if (type.equalsIgnoreCase("services")) {
            holder.icon_run = view.findViewById(R.id.run_image);
            if (lang.equalsIgnoreCase("ar")) {
                holder.icon_run.setImageDrawable(view.getResources().getDrawable(R.drawable.arrow_l));
            } else {
                holder.icon_run.setImageDrawable(view.getResources().getDrawable(R.drawable.arrow_r));
            }

            holder.icon = view.findViewById(R.id.service_image);
            holder.title = view.findViewById(R.id.service_title);
            holder.title.setTypeface(myFont);
            holder.description = view.findViewById(R.id.service_help);
            holder.description.setTypeface(myFont);
            if (objBean.getIsLeave().equalsIgnoreCase("0")) {
                holder.icon_run.setVisibility(View.VISIBLE);
            } else {
                holder.icon_run.setVisibility(View.GONE);
            }
            if (holder.title != null && null != objBean.getTitle()
                    && objBean.getTitle().trim().length() > 0) {
                holder.title.setText(Html.fromHtml(objBean.getTitle()));
            }
            if (holder.description != null && null != objBean.getDescription()
                    && objBean.getDescription().trim().length() > 0) {
                holder.description.setText(Html.fromHtml(objBean.getDescription()));
            }
            try {
                if (holder.icon != null && (objBean.getIcon() > 0)) {
                    holder.icon.setImageResource(objBean.getIcon());
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            view.setBackgroundResource(R.drawable.l_list_border);

        } else if (type.equalsIgnoreCase("note")) {
            holder.note_id = view.findViewById(R.id.note_id);
            holder.note_id.setTypeface(myFont);
            holder.note_title = view.findViewById(R.id.note_title);
            holder.note_title.setTypeface(myFont);
            holder.note_body = view.findViewById(R.id.note_body);
            holder.note_body.setTypeface(myFont);
            holder.note_datetime = view.findViewById(R.id.note_datetime);
            holder.note_datetime.setTypeface(myFont);
            holder.note_delete = view.findViewById(R.id.note_delete);
            holder.linearLayout3 = view.findViewById(R.id.ll_3_id);

            if (objBean.getmsgPriority().equalsIgnoreCase("3")) {
                holder.note_delete.setBackgroundResource(R.color.color_red);
            } else if (objBean.getmsgPriority().equalsIgnoreCase("2")) {
                holder.note_delete.setBackgroundResource(R.color.color_lightred);
            } else {
                holder.note_delete.setBackgroundResource(R.color.color_green);
            }
            holder.note_delete.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0);
                }
            });
            if (holder.note_id != null && null != objBean.getmsgId()
                    && objBean.getmsgId().trim().length() > 0) {
                holder.note_id.setText(Html.fromHtml(objBean.getmsgId()));
            }
            if (holder.note_title != null && null != objBean.getmsgTitle()
                    && objBean.getmsgTitle().trim().length() > 0) {

                holder.note_title.setText(Html.fromHtml(objBean.getmsgTitle()));
            }
            if (holder.note_body != null && null != objBean.getmsgBody()
                    && objBean.getmsgBody().trim().length() > 0) {
                final String myReallyLongText = objBean.getmsgBody();
                holder.note_body.setText(myReallyLongText);
            }
            if (holder.note_datetime != null && null != objBean.getmsgDateTime()
                    && objBean.getmsgDateTime().trim().length() > 0) {
                holder.note_datetime.setText(Html.fromHtml(objBean.getmsgDateTime()));
            }

            if (objBean.getmsgStatus().equalsIgnoreCase("1")) {
                view.setBackgroundResource(R.color.color_background);

            } else {
                view.setBackgroundResource(R.color.color_gold);
            }
        }

        return view;
    }

    public static class ViewHolder {

        public ImageView icon;
        public ImageView icon_run;
        public TextView title;
        public TextView description;
        public TextView note_id;
        public TextView note_title;
        public TextView note_body;
        public TextView note_datetime;
        public ImageView note_delete;
        public LinearLayout linearLayout3;
    }
}
