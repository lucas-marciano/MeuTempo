package br.com.cafecomandroid.meutempo;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.cafecomandroid.meutempo.adapter.CardAdapter;
import br.com.cafecomandroid.meutempo.entitys.WetherEntity;
import br.com.cafecomandroid.meutempo.sync.SyncAdapter;
import br.com.cafecomandroid.meutempo.utils.Animations;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.rv_layout)
    RecyclerView mRecyclerView;

//    @BindView(R.id.fab_action)
//    FloatingActionButton mFabAction;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public CardAdapter mCardAdapter;
    public Context context;
    private ArrayList<WetherEntity> wetherList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.transitions);
            getWindow().setSharedElementEnterTransition(transition);
        }

        super.onCreate(savedInstanceState);
        setupViews();

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        setupViews();
//    }

    private void setupViews() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        context = this;
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
//        setupFab();

        new DownloadFilesTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu:
                Intent i = new Intent(context, ConfiguracaoActivity.class);
                context.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setupRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

//        GridLayoutManager layoutManagerGrid = new GridLayoutManager(this, 2);
//        StaggeredGridLayoutManager layoutManagerStaggered = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager layoutManagerHor = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mCardAdapter = new CardAdapter(wetherList, this);

        mRecyclerView.setAdapter(mCardAdapter);

    }

//    @OnClick(R.id.fab_action)
//    public void submit() {
//        mFabAction.setOnClickListener(view -> Toast.makeText(context, "Uma ação", Toast.LENGTH_SHORT).show());
//    }
//
//    private void setupFab() {
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if (dy > 0)
//                    mFabAction.hide();
//                else if (dy < 0)
//                    mFabAction.show();
//            }
//        });
//
//    }

    private class DownloadFilesTask extends AsyncTask<Void, Void, Void> {
        Animations animations;

        @Override
        protected void onPreExecute() {
            animations = new Animations(context);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                wetherList = new ArrayList<>();
                SyncAdapter sync = new SyncAdapter(context);
                wetherList = sync.executeSyncTask();
            } catch (Exception e) {
                e.printStackTrace();
                Log.i(LOG_TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            animations.crossfade(mRecyclerView, progressBar);
//            mFabAction.setVisibility(View.VISIBLE);

            if (wetherList.isEmpty()) {
                Toast.makeText(context, "Não foi retornado nenhum valor.", Toast.LENGTH_SHORT).show();
            } else {
                setupRecycler();
            }

        }

    }


}
