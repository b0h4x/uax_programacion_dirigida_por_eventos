package com.example.feedback2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GameResultListAdapter extends RecyclerView.Adapter<GameResultListAdapter.GameResultHolder>{
    private final LayoutInflater mInflater;
    private List<GameResult> gameResults;
    private Button mButton;

    public GameResultListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public GameResultHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // not sure
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
       return new GameResultHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameResultHolder holder, int position) {
        if (gameResults != null) {
            GameResult current = gameResults.get(position);
            holder.gameResultView.setText("The score is: " + String.valueOf(current.getScore()));
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, mInflater.getContext().getString(R.string.share_score_text) + String.valueOf(current.getScore()));
                    sendIntent.setType("text/plain");

                    Intent shareIntent = Intent.createChooser(sendIntent, "" );
                    mInflater.getContext().startActivity(shareIntent);
                }
            });

        } else {
            holder.gameResultView.setText(R.string.no_score);
        }
    }

    void setGameResults(List<GameResult> results) {
        gameResults = results;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (gameResults != null) {return gameResults.size();}
        else return 0;
    }

    public class GameResultHolder extends RecyclerView.ViewHolder {
        final TextView gameResultView;
        Button button;

        public GameResultHolder(View itemView) {
            super(itemView);
            gameResultView = itemView.findViewById(R.id.textViewHolder);
            button = itemView.findViewById(R.id.button4);
        }
    }
}