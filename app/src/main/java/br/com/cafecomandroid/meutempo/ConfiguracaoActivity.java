package br.com.cafecomandroid.meutempo;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import br.com.cafecomandroid.meutempo.utils.Mensagens;
import br.com.cafecomandroid.meutempo.utils.SharedPreferenceDefault;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfiguracaoActivity extends AppCompatActivity {
    public final String CELCIUS = "metric";
    public final String FAREEINGHT = "imperial";

    @BindView(R.id.activity_configuracao)
    public CoordinatorLayout mConfiguration;

    @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @BindView(R.id.et_cnt_conf)
    public TextView mCnt;

    @BindView(R.id.et_locale_conf)
    public TextView mLocale;

    @BindView(R.id.rg_units)
    public RadioGroup mRadioGroup;

    @BindView(R.id.rb_fahrenheit)
    public RadioButton mRadioFareheint;

    @BindView(R.id.rb_celcius)
    public RadioButton mRadioCelcius;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayouts();
        initFocusEditText();
    }

    private void initLayouts() {
        setContentView(R.layout.activity_configuracao);
        ButterKnife.bind(this);
        context = this;
        mToolbar.setTitle(getResources().getString(R.string.app_configuration));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mCnt.setText(SharedPreferenceDefault.getSahredPreferenceCNT(context));
        mLocale.setText(SharedPreferenceDefault.getSahredPreferenceCidade(context));
        if (SharedPreferenceDefault.getSahredPreferenceUnidade(context).equals(FAREEINGHT))
            mRadioFareheint.setChecked(true);
        else if (SharedPreferenceDefault.getSahredPreferenceUnidade(context).equals(CELCIUS))
            mRadioCelcius.setChecked(true);
    }

    private void initFocusEditText() {
        mCnt.setOnFocusChangeListener((v, hasFocus) -> {
            SharedPreferenceDefault.setSahredPreferenceCNT(context, mCnt.getText().toString());
        });


        mLocale.setOnFocusChangeListener((v, hasFocus) -> {
            SharedPreferenceDefault.setSahredPreferenceCidade(context, mLocale.getText().toString());
        });

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_celcius) {
                SharedPreferenceDefault.setSahredPreferenceUnidade(context, CELCIUS);
            } else if (checkedId == R.id.rb_fahrenheit) {
                SharedPreferenceDefault.setSahredPreferenceUnidade(context, FAREEINGHT);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferenceDefault.setSahredPreferenceCNT(context, mCnt.getText().toString());
        SharedPreferenceDefault.setSahredPreferenceCidade(context, mLocale.getText().toString());
        Mensagens.showToast(context, "Suas configurações foram salvas.");
    }
}
