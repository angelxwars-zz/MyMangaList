package com.josea.mymangalist.views.adapters;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Notification;
import android.graphics.Color;

import com.josea.mymangalist.model.TopManga;
import com.josea.mymangalist.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorMangaList extends BaseAdapter {
    private Context context;
    private ArrayList<TopManga> listItems;

    public AdaptadorMangaList(Context context, ArrayList<TopManga> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return this.listItems.size();
    }

    @Override
    public TopManga getItem(int position) {
        return this.listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.top_manga_list_element, null);

        final TopManga item = this.getItem(position);

        ImageView coverImg = (ImageView) convertView.findViewById(R.id.coverImg);
        TextView title = (TextView) convertView.findViewById(R.id.title);
//        TextView description = (TextView) convertView.findViewById(R.id.description);
//        RatingBar score = (RatingBar) convertView.findViewById(R.id.score);
        TextView score = (TextView) convertView.findViewById(R.id.score);
        TextView rank = (TextView) convertView.findViewById(R.id.rank);

        Picasso.get().load(item.getImage_url()).into(coverImg);
        title.setText(item.getTitle());
        score.setText("Score: " + Float.toString(item.getScore()));
        rank.setText("Rank: " + Integer.toString(item.getRank()));

        return convertView;
    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void notificationDialog(String title, String description) {
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        String NOTIFICATION_CHANNEL_ID = "tutorialspoint_01";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
//            // Configure the notification channel.
//            notificationChannel.setDescription("Sample Channel description");
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
//            notificationChannel.enableVibration(true);
//            notificationManager.createNotificationChannel(notificationChannel);
//        }
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID);
//        notificationBuilder.setAutoCancel(true)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setTicker("Tutorialspoint")
//                //.setPriority(Notification.PRIORITY_MAX)
//                .setContentTitle("sample notification")
//                .setContentText("This is sample notification")
//                .setContentInfo("Information");
//        notificationManager.notify(1, notificationBuilder.build());
//    }
}
