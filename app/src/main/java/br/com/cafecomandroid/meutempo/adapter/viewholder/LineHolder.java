package br.com.cafecomandroid.meutempo.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import br.com.cafecomandroid.meutempo.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Lucas Marciano on 24/01/2017.
 * MeuTempo
 * br.com.cafecomandroid.meutempo.adapter.viewholder
 */

public class LineHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.main_line_title) public TextView title;
    @BindView(R.id.main_line_more) public ImageButton moreButton;
    @BindView(R.id.main_line_delete) public ImageButton deleteButton;

    public LineHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

}
