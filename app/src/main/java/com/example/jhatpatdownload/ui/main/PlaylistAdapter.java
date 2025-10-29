package com.example.jhatpatdownload.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.jhatpatdownload.R;
import com.example.jhatpatdownload.data.model.VideoItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.VideoViewHolder> {

    private List<VideoItem> videos = new ArrayList<>();
    private Set<Integer> selectedPositions = new HashSet<>();



    public List<VideoItem> getSelectedVideos() {
        List<VideoItem> selected = new ArrayList<>();
        for (Integer pos : selectedPositions) {
            selected.add(videos.get(pos));
        }
        return selected;
    }


    public void setVideos(List<VideoItem> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_list_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        VideoItem video = videos.get(position);


        holder.title.setText(video.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(video.getThumbnailUrl())
                .into(holder.thumbnail);


        holder.checkBox.setChecked(selectedPositions.contains(position));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selectedPositions.add(position);
                } else {
                    selectedPositions.remove(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumbnail;
        CheckBox checkBox;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.video_title);
            thumbnail = itemView.findViewById(R.id.video_thumbnail);
            checkBox = itemView.findViewById(R.id.video_checkbox);
        }
    }
}

