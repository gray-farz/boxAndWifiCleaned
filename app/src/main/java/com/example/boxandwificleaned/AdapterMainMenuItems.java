package com.example.boxandwificleaned;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMainMenuItems extends RecyclerView.Adapter<AdapterMainMenuItems.ViewHolder> {
    private List<ListMainMenuItems>listMainMenuItems;
    private Context context;
    private Activity activity;
    public static ProgressDialog progressDialog;

    public AdapterMainMenuItems(List<ListMainMenuItems> listMainMenuItems, Context context, Activity activity) {
        this.listMainMenuItems = listMainMenuItems;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_every_item_main_menu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListMainMenuItems listMainMenuItem=listMainMenuItems.get(position);
        holder.textMainMenuITEMS.setText(listMainMenuItem.getText());
        holder.imgMainMenuITEMS.setImageDrawable(context.getResources().getDrawable(listMainMenuItem.getImage()));

        holder.layoutMainMenuITEMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionSettingButtons(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listMainMenuItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView imgMainMenuITEMS;
        private TextView textMainMenuITEMS;
        private LinearLayout layoutMainMenuITEMS;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imgMainMenuITEMS=(ImageView)itemView.findViewById(R.id.imageBackgroundMainMenuItems);
            textMainMenuITEMS=(TextView)itemView.findViewById(R.id.textMainMenuItems);
            layoutMainMenuITEMS = itemView.findViewById(R.id.mainMenuItemsLayout);
        }
    }

    private void actionSettingButtons(int position)
    {
        if(listMainMenuItems.get(position).getItemListStr().equals("text"))
        {

            Intent intent = new Intent(activity, textActivity.class);
            //intent.putExtra("fileName",listFLASHFILES.get(position).getStrFlashFile());
            activity.startActivity(intent);
        }
        else if(listMainMenuItems.get(position).getItemListStr().equals("updateCode"))
        {
            Intent intent = new Intent(activity, UpdateWifiModuleCode.class);
            activity.startActivity(intent);
        }

        else if(listMainMenuItems.get(position).getItemListStr().equals("help"))
        {
            Intent intent = new Intent(activity, UpdateWifiModuleCode.class);
            activity.startActivity(intent);
        }
    }
}


