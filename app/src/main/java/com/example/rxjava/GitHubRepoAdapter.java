package com.example.rxjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GitHubRepoAdapter extends BaseAdapter {
    private final List<String> tweetHeadingList = new ArrayList<>();

    @Override public int getCount() {
        return tweetHeadingList.size();
    }

    @Override public String getItem(int position) {
        if (position < 0 || position >= tweetHeadingList.size()) {
            return null;
        } else {
            return tweetHeadingList.get(position);
        }
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        final View view = (convertView != null ? convertView : createView(parent));
        final GitHubRepoViewHolder viewHolder = (GitHubRepoViewHolder) view.getTag();
        viewHolder.setGitHubRepo(getItem(position));
        return view;
    }

    public void setGitHubRepos(@Nullable List<String> repos) {
        if (repos == null) {
            return;
        }
        tweetHeadingList.clear();
        tweetHeadingList.addAll(repos);
        notifyDataSetChanged();
    }

    private View createView(ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.item_github_repo, parent, false);
        final GitHubRepoViewHolder viewHolder = new GitHubRepoViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    private static class GitHubRepoViewHolder {

        private TextView textRepoName;

        public GitHubRepoViewHolder(View view) {
            textRepoName = (TextView) view.findViewById(R.id.text_repo_name);
        }

        public void setGitHubRepo(String gitHubRepo) {
            textRepoName.setText(gitHubRepo);
        }
    }
}
