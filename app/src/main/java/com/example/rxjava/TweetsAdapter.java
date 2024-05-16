package com.example.rxjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TweetsAdapter extends BaseAdapter {
    private final List<TweetModel> tweetsList;

    TweetsAdapter(List<TweetModel> tweetsList){
        this.tweetsList = tweetsList;
    }
    @Override
    public int getCount() {
        return tweetsList.size();
    }

    @Override
    public TweetModel getItem(int position) {
        if (position < 0 || position >= tweetsList.size()) {
            return null;
        } else {
            return tweetsList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final TweetsViewHolder viewHolder = (TweetsViewHolder) view.getTag();
        viewHolder.setTweetRepo(getItem(position));
        return view;
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.tweet_item, parent, false);
        final TweetsViewHolder viewHolder = new TweetsViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static class TweetsViewHolder {

        private TextView textTweet;

        public TweetsViewHolder(View view) {
            textTweet = (TextView) view.findViewById(R.id.tweet_tv);
        }

        public void setTweetRepo(TweetModel tweetModel) {
            textTweet.setText(tweetModel.text);
        }
    }
}
