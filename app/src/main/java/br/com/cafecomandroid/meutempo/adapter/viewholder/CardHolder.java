package br.com.cafecomandroid.meutempo.adapter.viewholder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.cafecomandroid.meutempo.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas Marciano on 27/01/2017.
 * MeuTempo
 * br.com.cafecomandroid.meutempo.adapter.viewholder
 */

public class CardHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.tv_temp_atual) public TextView tempAtual;
    @BindView(R.id.tv_temp_min) public TextView tempMin;
    @BindView(R.id.tv_temp_max) public TextView tempMax;
    @BindView(R.id.tv_clima_atual) public TextView climaAtual;
    @BindView(R.id.tv_pressao) public TextView pressao;
    @BindView(R.id.tv_umidade) public TextView umidade;
    @BindView(R.id.tv_data) public TextView dataAtual;

    @BindView(R.id.iv_clima_representacao) public ImageView climaImage;
    @BindView(R.id.card_item) public CardView cardItem;


    public CardHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
