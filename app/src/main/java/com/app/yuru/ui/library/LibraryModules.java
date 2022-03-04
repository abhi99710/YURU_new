package com.app.yuru.ui.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.app.yuru.R;
import com.app.yuru.ui.transition.TransitionActivity;

import java.util.ArrayList;
import java.util.List;

public class LibraryModules extends AppCompatActivity {
    private ImageView backlibrary;
    private RecyclerView recy_library;
    private List<ModelLibrary> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_modules);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backlibrary = findViewById(R.id.backlibrary);
        recy_library = findViewById(R.id.recy_library);

        list.add(new ModelLibrary(
                "1",
                "5-Minute Meditation You Can Do Anywhere",
                "Goodful",
                "05:16",
                "https://i.ytimg.com/vi/inpok4MKVLM/hqdefault.jpg",
                "https://www.youtube.com/watch?v=inpok4MKVLM",
                "https://i.ytimg.com/vi/inpok4MKVLM/hqdefault.jpg",
                "In just 5 minutes you can reset your day in a positive way.\n" +
                        "\n" +
                        "Special thanks to John Davisi for lending us his incredibly soothing voice. "
        ));

        list.add(new ModelLibrary(
                "2",
                "Daily Calm | 10 Minute Mindfulness Meditation | Be Present",
                "Calm",
                "10:30",
                "https://i.ytimg.com/vi/ZToicYcHIOU/hqdefault.jpg",
                "https://www.youtube.com/watch?v=ZToicYcHIOU",
                "https://i.ytimg.com/vi/ZToicYcHIOU/hqdefault.jpg",
                "Tamara Levitt guides this 10 minute Daily Calm mindfulness meditation to powerfully restore and re-connect with the present."
        ));

        list.add(new ModelLibrary(
                "3",
                "5-Minute Meditation You Can Do Anywhere",
                "Goodful",
                "05:16",
                "https://i.ytimg.com/vi/inpok4MKVLM/hqdefault.jpg",
                "https://www.youtube.com/watch?v=inpok4MKVLM",
                "https://i.ytimg.com/vi/inpok4MKVLM/hqdefault.jpg",
                "In just 5 minutes you can reset your day in a positive way.\n" +
                        "\n" +
                        "Special thanks to John Davisi for lending us his incredibly soothing voice. "
        ));

        AdapterLibrary adapterLibrary = new AdapterLibrary(this, list);
        recy_library.setHasFixedSize(true);
        recy_library.setLayoutManager(new LinearLayoutManager(this));
        recy_library.setAdapter(adapterLibrary);

        backlibrary.setOnClickListener(v->{
            startActivity(new Intent(LibraryModules.this, TransitionActivity.class));
        });



    }
}