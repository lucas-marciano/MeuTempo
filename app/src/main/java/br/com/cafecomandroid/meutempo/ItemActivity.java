package br.com.cafecomandroid.meutempo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import br.com.cafecomandroid.meutempo.entitys.WetherEntity;
import br.com.cafecomandroid.meutempo.utils.SharedPreferenceDefault;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemActivity extends AppCompatActivity {
    @BindView(R.id.activity_item)
    public CoordinatorLayout mCoordinator;

    @BindView(R.id.bottom_view)
    public ViewGroup mBottom;

    @BindView(R.id.info_linha)
    public ViewGroup mBottomDesc;

    @BindView(R.id.toolbar_item_activity)
    public Toolbar mToolbar;

    @BindView(R.id.tv_velocidade_vento)
    public TextView mVelVento;

    @BindView(R.id.tv_temperatura)
    public TextView mTemp;

    @BindView(R.id.tv_data_item)
    public TextView mData;

    @BindView(R.id.tv_descricao_item)
    public TextView mDescricao;

    @BindView(R.id.tv_temp_max)
    public TextView mTempMax;

    @BindView(R.id.tv_temp_min)
    public TextView mTempMin;

    @BindView(R.id.tv_umidade_item)
    public TextView mUmidade;

    @BindView(R.id.tv_pressao_item)
    public TextView mPressao;

    @BindView(R.id.iv_clima)
    public ImageView mClima;

    @BindView(R.id.fab_action)
    public FloatingActionButton mFabShare;

    private Context context;
    private WetherEntity wether;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupAnimation();
        super.onCreate(savedInstanceState);
        wether = getIntent().getExtras().getParcelable("entidade");
        setupViews();
        setupLabels();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupAnimation();
    }

    private void setupAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.transitions);
            getWindow().setSharedElementEnterTransition(transition);

            Transition bottom = getWindow().getSharedElementEnterTransition();
            bottom.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {

                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        TransitionManager.beginDelayedTransition(mBottom, new Slide());
                        TransitionManager.beginDelayedTransition(mBottomDesc, new Slide());
                        mBottom.setVisibility(View.VISIBLE);
                        mBottomDesc.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onTransitionCancel(Transition transition) {

                }

                @Override
                public void onTransitionPause(Transition transition) {

                }

                @Override
                public void onTransitionResume(Transition transition) {

                }
            });
        }
    }


    private void setupViews() {
        setContentView(R.layout.activity_item);
        ButterKnife.bind(this);
        context = this;
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mFabShare.setOnClickListener(v -> {
            Intent compartilha = new Intent(Intent.ACTION_SEND);
            compartilha.setType("text/plain");
            compartilha.putExtra(Intent.EXTRA_SUBJECT, "Meu Tempo App");
            compartilha.putExtra(Intent.EXTRA_TEXT, "Oi! Estou em " + wether.getCityName() +
                    " e está um clima de " + wether.getDay()+"°"+
                    SharedPreferenceDefault.getSahredPreferenceUnidade(context) == "imperial"? "F" : "C" +
                    ". O dia está com cara de " + wether.getDescription());
            startActivity(Intent.createChooser(compartilha, "Compartilhando"));
        });

    }

    private void setupLabels() {
        ArrayList<Integer> resposta;
        resposta = setupColor(wether.getMain());
        mTemp.setText(String.format(Locale.getDefault(), "%s°", wether.getDay()));
        mData.setText(String.format(Locale.getDefault(), "%s", wether.getDate()));
        mDescricao.setText(String.format(Locale.getDefault(), "%s", wether.getDescription()));
        mTempMax.setText(String.format(Locale.getDefault(), "%s°", wether.getHigh()));
        mTempMin.setText(String.format(Locale.getDefault(), "%s°", wether.getLow()));
        mVelVento.setText(String.format(Locale.getDefault(), "%s m/s", wether.getWindSpeed()));
        mUmidade.setText(wether.getHumidity() + "%");
        mPressao.setText(String.format(Locale.getDefault(), "%s hPa", wether.getPressure()));
        mClima.setImageResource(setupImage(wether.getMain()));

        mCoordinator.setBackgroundColor(resposta.get(0));
        mBottom.setBackgroundColor(resposta.get(1));
        mToolbar.setBackgroundColor(resposta.get(0));
        mBottomDesc.setBackgroundColor(resposta.get(2));
    }

    private ArrayList<Integer> setupColor(String description) {
        ArrayList<Integer> respota = new ArrayList();
        switch (description) {
            case "Rain":
            case "Snow":
                respota.add(this.getResources().getColor(R.color.colorPrimary));
                respota.add(this.getResources().getColor(R.color.colorPrimaryDark));
                respota.add(this.getResources().getColor(R.color.colorAccent));

                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryDark));
                }

                return respota;
            case "Clear":
            case "Clouds":
                respota.add(this.getResources().getColor(R.color.colorValencia));
                respota.add(this.getResources().getColor(R.color.colorValenciaDark));
                respota.add(this.getResources().getColor(R.color.colorValenciaLight));

                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(this.getResources().getColor(R.color.colorValenciaDark));
                }

                return respota;
            default:
                respota.add(this.getResources().getColor(R.color.colorDefault));
                respota.add(this.getResources().getColor(R.color.colorDefaultDark));
                respota.add(this.getResources().getColor(R.color.colorDefaultDark));

                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = this.getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(this.getResources().getColor(R.color.colorDefault));
                }

                return respota;
        }
    }

    private int setupImage(String description) {
        switch (description) {
            case "Rain":
                return R.drawable.ic_rainyweather;
            case "Snow":
                return R.drawable.ic_snowweather;
            case "Clear":
                return R.drawable.ic_clearday;
            case "Clouds":
                return R.drawable.ic_cloudy;
            default:
                return R.drawable.ic_unknow;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TransitionManager.beginDelayedTransition(mBottom, new Slide());
            TransitionManager.beginDelayedTransition(mBottomDesc, new Slide());
            mBottom.setVisibility(View.INVISIBLE);
            mBottomDesc.setVisibility(View.INVISIBLE);
        }
        super.onBackPressed();
    }
}
