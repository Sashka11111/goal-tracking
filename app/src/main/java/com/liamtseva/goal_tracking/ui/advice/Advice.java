// Advice.java
package com.liamtseva.goal_tracking.ui.advice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.liamtseva.goal_tracking.R;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Advice extends Fragment {

    private TextView adviceTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advice, container, false);
        adviceTextView = view.findViewById(R.id.text_advice);

        // Зчитуємо поради з файлу і встановлюємо текст у TextView
        String adviceText = readAdviceFromFile();
        adviceTextView.setText(adviceText);

        return view;
    }

    private String readAdviceFromFile() {
        StringBuilder adviceText = new StringBuilder();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.advice);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                adviceText.append(line).append("\n");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adviceText.toString();
    }
}
