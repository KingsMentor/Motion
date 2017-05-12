package xyz.belvi.motion.viewmodels.holders;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import xyz.belvi.motion.R;

/**
 * Created by zone2 on 5/12/17.
 */

public class ReviewHolder extends RecyclerView.ViewHolder {
    private AppCompatTextView authorView, contentView;
    private Context mContext;

    public ReviewHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        authorView = (AppCompatTextView) itemView.findViewById(R.id.author);
        contentView = (AppCompatTextView) itemView.findViewById(R.id.content);
    }

    public AppCompatTextView getAuthorView() {
        return this.authorView;
    }

    public AppCompatTextView getContentView() {
        return this.contentView;
    }

    public Context getContext() {
        return this.mContext;
    }
}
