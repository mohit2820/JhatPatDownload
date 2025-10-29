package com.example.jhatpatdownload.ui.main;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.jhatpatdownload.R;
import com.example.jhatpatdownload.data.model.Playlist;
import com.example.jhatpatdownload.data.model.VideoItem;
import com.example.jhatpatdownload.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  MainViewModel viewModel;
    private ActivityMainBinding binding;
    private PlaylistAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adapter = new PlaylistAdapter();
        binding.videosRecyclerView.setAdapter(adapter);
        binding.videosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        int spacingInPixel = getResources().getDimensionPixelSize(R.dimen.recycler_item_spacing);
        binding.videosRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(spacingInPixel));
        fetchButton();
        observeData();
        showDialog();
        setupDownloadSelectedButton();

    }

    private void fetchButton(){
        binding.fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = binding.urlInput.getText().toString().trim();

                // url empty nhi hona chiye
                if(!url.isEmpty()){
                    viewModel.fetchPlaylist(url);
                }


            }
        });
    }

    private void observeData(){
        viewModel.getPlaylist().observe(this, new Observer<Playlist>() {
            @Override
            public void onChanged(Playlist playlist) {

                if(playlist!=null){
                    adapter.setVideos(playlist.getVideos());
                    binding.playlistTitle.setText(playlist.getTitle());
                    binding.channelName.setText(playlist.getChannel());
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch playlist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setupDownloadSelectedButton() {
        binding.downloadSelectedButton.setOnClickListener(v -> {
            List<VideoItem> selectedVideos = adapter.getSelectedVideos();

            if (selectedVideos.isEmpty()) {
                Toast.makeText(this, "No video selected", Toast.LENGTH_SHORT).show();
                return;
            }

            // For now, just show dialog for first selected video
            VideoItem video = selectedVideos.get(0);
            viewModel.fetchVideoFormats(video.getVideoUrl());
        });

    }

    private void showDialog(){
        viewModel.getVideoFormats().observe(this, formats -> {
            if (formats != null && !formats.isEmpty()) {
                QualitySelectionDialogFragment dialog = new QualitySelectionDialogFragment(
                        formats,
                        selectedUrl -> {
                            // Start download for selected quality
                            Log.d("DOWNLOAD", "Start download: " + selectedUrl);
                            List<VideoItem> selectedVideos = adapter.getSelectedVideos();
                            String title = selectedVideos.get(0).getTitle();
                            startDownload(selectedUrl, title);
                        }
                );
                dialog.show(getSupportFragmentManager(), "quality_dialog");
            } else {
                Toast.makeText(this, "No formats found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void startDownload(String videoUrl, String title) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(videoUrl));
        request.setTitle(title);
        request.setDescription("Downloading video...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        // Save to Downloads directory
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, title + ".mp4");

        DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        if (downloadManager != null) {
            downloadManager.enqueue(request);
            Toast.makeText(this, "Download Started", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "DownloadManager not available", Toast.LENGTH_SHORT).show();
        }
    }


}