package com.josea.mymangalist.activities;

import android.content.Context;
import android.content.Intent;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.josea.mymangalist.R;
import com.josea.mymangalist.helpers.MangaDbHelper;
import com.josea.mymangalist.model.MangaDB;
import com.josea.mymangalist.views.adapters.AdaptadorMyMangaList;

import java.util.List;

public class MyMangas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    MangaDbHelper mangaDbHelper = new MangaDbHelper(this);
    static ListView myMangaListView;
    static AdaptadorMyMangaList adaptador;
    static List<MangaDB> mangaList;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_mangas);
        myMangaListView = (ListView) findViewById(R.id.myMangaList);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.my_mangas_title));
        setSupportActionBar(toolbar);

        Toast.makeText(getApplicationContext(), getString(R.string.my_mangas_toast), Toast.LENGTH_SHORT).show();

        drawer = findViewById(R.id.drawer_layout_my_mangas);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        mangaList = getMyMangaList();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarMyMangaList);

        progressBar.setVisibility(View.INVISIBLE);

        adaptador = new AdaptadorMyMangaList(this, mangaList);
        myMangaListView.setAdapter(adaptador);

        myMangaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent visorDetalles = new Intent(view.getContext(), MangaDetailsActivity.class);
                visorDetalles.putExtra("ID", mangaList.get(position).getId());
                visorDetalles.putExtra("TITLE", mangaList.get(position).getTitle());
                visorDetalles.putExtra("RANK", mangaList.get(position).getRank() );
                startActivity(visorDetalles);
            }

        });
    }

    public List<MangaDB> getMyMangaList(){
       return mangaDbHelper.getMyMangas();
    }

    @Override
    public void onResume(){
        super.onResume();
        drawer.closeDrawer(GravityCompat.START);
        drawer.clearFocus();
        mangaList = getMyMangaList();

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBarMyMangaList);
        progressBar.setVisibility(View.INVISIBLE);

        adaptador = new AdaptadorMyMangaList(this, mangaList);
        myMangaListView.setAdapter(adaptador);
        myMangaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent visorDetalles = new Intent(view.getContext(), MangaDetailsActivity.class);
                visorDetalles.putExtra("ID", mangaList.get(position).getId());
                visorDetalles.putExtra("TITLE", mangaList.get(position).getTitle());
                visorDetalles.putExtra("RANK", mangaList.get(position).getRank() );
                startActivity(visorDetalles);
            }

        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id=item.getItemId();
        Intent intent;
        navigationView.getItemBackground();
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
