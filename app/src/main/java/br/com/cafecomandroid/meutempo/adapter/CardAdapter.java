package br.com.cafecomandroid.meutempo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Locale;

import br.com.cafecomandroid.meutempo.ItemActivity;
import br.com.cafecomandroid.meutempo.R;
import br.com.cafecomandroid.meutempo.adapter.viewholder.CardHolder;
import br.com.cafecomandroid.meutempo.entitys.WetherEntity;

/**
 * Created by Lucas Marciano on 27/01/2017.
 * MeuTempo
 * br.com.cafecomandroid.meutempo.adapter
 */

public class CardAdapter extends RecyclerView.Adapter<CardHolder> {

    private final List<WetherEntity> mWether;
    private Context context;

    public CardAdapter(List<WetherEntity> mWether, Context context) {
        this.mWether = mWether;
        this.context = context;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CardHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.main_line_card, parent, false));
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        holder.tempAtual.setText(String.format(Locale.getDefault(), "%s°", mWether.get(position).getDay()));
        holder.tempMin.setText(String.format(Locale.getDefault(), "%s°", mWether.get(position).getLow()));
        holder.tempMax.setText(String.format(Locale.getDefault(), "%s°", mWether.get(position).getHigh()));
        holder.climaAtual.setText(String.format(Locale.getDefault(), "%s", setupTexts(mWether.get(position).getMain())));
        holder.pressao.setText(String.format(Locale.getDefault(), "%s", mWether.get(position).getPressure()));
        holder.umidade.setText(mWether.get(position).getHumidity() + "%");
        holder.dataAtual.setText(String.format(Locale.getDefault(), "%s", mWether.get(position).getDate()));
        holder.climaImage.setImageResource(setupImage(mWether.get(position).getMain()));
        holder.cardItem.setCardBackgroundColor(setupColor(mWether.get(position).getMain()));
        holder.cardItem.setOnClickListener(v -> openActivity(mWether.get(position), v));
    }

    private void openActivity(WetherEntity entity, View view) {
        Intent intent = new Intent(context, ItemActivity.class);
        intent.putExtra("entidade", entity);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View tvTempAtual = view.findViewById(R.id.tv_temp_atual);
            View ivRepre = view.findViewById(R.id.iv_clima_representacao);

            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                    Pair.create(tvTempAtual, "tvTempAtual"),
                    Pair.create(ivRepre, "ivRepre"));
            context.startActivity(intent, options.toBundle());
        } else {
            context.startActivity(intent);
        }

    }

    private String setupTexts(String description) {
        switch (description) {
            case "Rain":
                return "Chuva";
            case "Snow":
                return "Neve";
            case "Clear":
                return "Sol";
            case "Clouds":
                return "Nublado";
            default:
                return "?";
        }
    }

    private int setupColor(String description) {
        switch (description) {
            case "Rain":
            case "Snow":
                return context.getResources().getColor(R.color.colorPrimary);
            case "Clear":
            case "Clouds":
                return context.getResources().getColor(R.color.colorValencia);
            default:
                return context.getResources().getColor(R.color.colorBackgroundGrey);
        }
    }

    private int setupImage(String description) {
        switch (description) {
            case "Rain":
            case "Drizzle":
                return R.drawable.ic_rainyweather;
            case "Extreme":
                return R.drawable.ic_showcase;
            case "Thunderstorm":
                return R.drawable.ic_stormweather;
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
    public int getItemCount() {
        return mWether != null ? mWether.size() : 0;
    }


}
