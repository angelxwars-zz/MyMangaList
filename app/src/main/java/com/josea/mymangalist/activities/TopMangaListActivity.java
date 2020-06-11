package com.josea.mymangalist.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.josea.mymangalist.model.TopManga;
import com.josea.mymangalist.R;
import com.josea.mymangalist.utils.JikanService;
import com.josea.mymangalist.views.adapters.AdaptadorMangaList;

import java.util.ArrayList;

public class TopMangaListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    static ListView topMangaListView;
    static AdaptadorMangaList adaptador;
    private NavigationView navigationView;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_manga_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.top_50_title));
        setSupportActionBar(toolbar);

        Toast.makeText(getApplicationContext(), getString(R.string.top_50_toast), Toast.LENGTH_SHORT).show();

        drawer = findViewById(R.id.drawer_layout_top_manga_list);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

//        topMangaListView = (ListView) findViewById(R.id.mangaList);
        apiBackground a = new apiBackground(this);
        a.execute();


    }

    @Override
    public void onResume(){
        super.onResume();
        drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawer(GravityCompat.START);
    }

    private class apiBackground extends AsyncTask<String,Integer,Boolean> {
        ArrayList<TopManga> listItems = new ArrayList<>();
        Context context;
        public apiBackground (Context context){
            this.context = context;
        }

        protected Boolean doInBackground(String... params) {
            JikanService jikanService = new JikanService();
            listItems = jikanService.getTopMangaList();
            if (listItems == null){
                listItems = new ArrayList<TopManga>();
            }
            return true;
        }

        protected void onPostExecute(Boolean result) {
            topMangaListView = (ListView) findViewById(R.id.topMangaList);
            ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);

            progressBar.setVisibility(View.INVISIBLE);
            adaptador = new AdaptadorMangaList(this.context, listItems);
            topMangaListView.setAdapter(adaptador);

            topMangaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent visorDetalles = new Intent(view.getContext(), MangaDetailsActivity.class);
                    visorDetalles.putExtra("ID", listItems.get(position).getId());
                    visorDetalles.putExtra("TITLE", listItems.get(position).getTitle());
                    visorDetalles.putExtra("RANK", listItems.get(position).getRank() );
                    startActivity(visorDetalles);
                }

            });
            if (listItems.size() == 0){
                errorDialog();
            }

        }

        private void errorDialog (){
            new AlertDialog.Builder(this.context)
                    .setTitle("Fallo de la api")
                    .setMessage("La api Jikan ha devuelto un fallo por parte de servidor")
                    .setPositiveButton(android.R.string.yes, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        switch (id){
            case R.id.top_manga_menu:
                intent = new Intent(this,TopMangaListActivity.class);
                startActivity(intent);
                break;
            case R.id.my_mangas_menu:
                intent= new Intent(this,MyMangas.class);
                startActivity(intent);
                break;
            case R.id.coordenadas:
                intent= new Intent(this,LocationActivity.class);
                startActivity(intent);
                break;
            case R.id.multimedia:
                intent= new Intent(this,MultimediaActivity.class);
                startActivity(intent);
                break;
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_logout:
                signOut();
                return true;
        }
        return false;
    }

    private void signOut() {
        GoogleSignInClient mGoogleSignInClient;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        final Context context = this;
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent= new Intent(context, SignInGoogleActivity.class);
                        startActivity(intent);
                    }
                });
    }
}
