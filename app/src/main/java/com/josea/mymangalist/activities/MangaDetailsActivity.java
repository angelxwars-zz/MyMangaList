package com.josea.mymangalist.activities;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.josea.mymangalist.R;
import com.josea.mymangalist.helpers.MangaDbHelper;
import com.josea.mymangalist.model.DetailsManga;
import com.josea.mymangalist.utils.JikanService;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;


public class MangaDetailsActivity extends AppCompatActivity {
    int id;
    String title;
    int rank;
    DetailsManga manga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_details);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            id = bundle.getInt("ID");
            title = bundle.getString("TITLE");
            rank = bundle.getInt("RANK");

            apiBackground a = new apiBackground(this);
            a.execute();

            GetMangaByIdTask getManga = new GetMangaByIdTask(this);
            getManga.execute();
        }
    }


    private class apiBackground extends AsyncTask<String, Integer, Boolean> {

        Context context;

        public apiBackground(Context context) {
            this.context = context;
        }

        protected Boolean doInBackground(String... params) {
            JikanService jikanService = new JikanService();
            manga = jikanService.getDetailsManga(id, title);

            return true;
        }

        protected void onPostExecute(Boolean result) {
//          Pasamos los valores a la vista
            ImageView coverImg = (ImageView) findViewById(R.id.coverImg);
            TextView title = (TextView) findViewById(R.id.title);
            TextView description = (TextView) findViewById(R.id.description);
            CheckBox fav = (CheckBox) findViewById(R.id.favoriteButton);
            ImageView shareButton =(ImageView) findViewById(R.id.share);


//            Table layout information
            TextView manga_status_data = (TextView) findViewById(R.id.manga_status_data);
            TextView manga_score_data = (TextView) findViewById(R.id.manga_score_data);
            TextView manga_start_data = (TextView) findViewById(R.id.manga_start_data);
            TextView manga_end_data = (TextView) findViewById(R.id.manga_end_data);
            TextView manga_volumes_data = (TextView) findViewById(R.id.manga_volumes_data);
            TextView manga_chapters_data = (TextView) findViewById(R.id.manga_chapters_data);

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text) + " " + manga.getTitle());
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);

                }
            });

            Picasso.get().load(manga.getImage_url()).into(coverImg);
            title.setText(manga.getTitle());
            description.setText(manga.getSynopsis());
            fav.setVisibility(View.VISIBLE);

            manga_status_data.setText(manga.isPublishing() ? "En emision": "Finalizada");
            manga_score_data.setText(String.format("%.2f", manga.getScore()));
            String formated_start_date = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                    DateFormat.SHORT).format(manga.getStart_date());
            manga_start_data.setText(formated_start_date);
            String formated_end_date = manga.getEnd_date() == null ? "En emision" : DateFormat.getDateTimeInstance(DateFormat.SHORT,
                    DateFormat.SHORT).format(manga.getEnd_date());
            manga_end_data.setText(formated_end_date);
            manga_volumes_data.setText(String.format("%d", manga.getVolumes()));
            manga_chapters_data.setText(String.format("%d", manga.getChapters()));

            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            TableLayout data_table = (TableLayout) findViewById(R.id.table_data);
            TextView sinopsis_text = (TextView) findViewById(R.id.sinopsis_text);

            progressBar.setVisibility(View.INVISIBLE);
            data_table.setVisibility(View.VISIBLE);
            sinopsis_text.setVisibility(View.VISIBLE);
            shareButton.setVisibility(View.VISIBLE);


        }
    }


    private class GetMangaByIdTask extends AsyncTask<Void, Void, Cursor> {
        Context context;

        public GetMangaByIdTask(Context context) {
            this.context = context;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            MangaDbHelper mangaDbHelper = new MangaDbHelper(context);

            return mangaDbHelper.getMangaById(Integer.toString(manga.getId()));
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            boolean existInDb = false;
            if (cursor != null && cursor.moveToLast()) {
                existInDb = true;
            }
            favorite(existInDb); // Va aqui para que podamos comprobar si el manga ya existe
        }

    }

    private void favorite(boolean existInDb){
        final CheckBox favoriteButton =(CheckBox) findViewById(R.id.favoriteButton);
        final MangaDbHelper mangaDbHelper = new MangaDbHelper(this);

        if (existInDb){
            favoriteButton.setChecked(true);
            favoriteButton.setButtonDrawable(R.drawable.star_on);
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteButton.setSelected(!favoriteButton.isPressed());
                String notifyMsg = "";
                if (favoriteButton.isChecked()) {
                    favoriteButton.setChecked(true);
                    favoriteButton.setButtonDrawable(R.drawable.star_on);
                    mangaDbHelper.saveManga(manga, rank);
                    notifyMsg = getString(R.string.manga_added);
                }
                else {
                    favoriteButton.setChecked(false);
                    favoriteButton.setButtonDrawable(R.drawable.star_off);
                    mangaDbHelper.deleteManga(manga);
                    notifyMsg = getString(R.string.manga_deleted);

                }
                Toast.makeText(getApplicationContext(), notifyMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
