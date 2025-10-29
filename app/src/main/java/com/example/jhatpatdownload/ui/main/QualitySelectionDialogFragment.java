package com.example.jhatpatdownload.ui.main;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.jhatpatdownload.data.model.VideoFormat;
import com.example.jhatpatdownload.databinding.DialogQualitySelectionBinding;

import java.util.List;

public class QualitySelectionDialogFragment extends DialogFragment {

    private final List<VideoFormat> formats;
    private final OnQualitySelectedListener listener;
    private DialogQualitySelectionBinding binding;

    public interface OnQualitySelectedListener {
        void onQualitySelected(String url);
    }

    public QualitySelectionDialogFragment(List<VideoFormat> formats, OnQualitySelectedListener listener) {
        this.formats = formats;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = DialogQualitySelectionBinding.inflate(getLayoutInflater());

        // Dynamically add radio buttons
        for (int i = 0; i < formats.size(); i++) {
            VideoFormat format = formats.get(i);
            String height = format.getResolution().split("x")[1];
            String label = height + "p - " + format.getExt().toUpperCase();

            RadioButton radioButton = new RadioButton(requireContext());
            radioButton.setText(label);
            radioButton.setId(i);
            binding.qualityRadioGroup.addView(radioButton);

            if (i == 0) radioButton.setChecked(true);
        }

        // Handle download button click
        binding.buttonDownload.setOnClickListener(v -> {
            int selectedId = binding.qualityRadioGroup.getCheckedRadioButtonId();
            if (selectedId != -1) {
                VideoFormat selected = formats.get(selectedId);
                listener.onQualitySelected(selected.getUrl());
                dismiss();
            }
        });

        binding.buttonCancel.setOnClickListener(v -> dismiss());

        return new AlertDialog.Builder(requireContext())
                .setView(binding.getRoot())
                .create();
    }
}
