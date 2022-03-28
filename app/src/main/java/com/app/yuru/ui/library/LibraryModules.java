package com.app.yuru.ui.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.app.yuru.R;
import com.app.yuru.ui.retrofitApi.APIClient;
import com.app.yuru.ui.retrofitApi.APIInterface;
import com.app.yuru.ui.retrofitApi.LibraryResponse;
import com.app.yuru.ui.retrofitApi.Result;
import com.app.yuru.ui.transition.TransitionActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryModules extends AppCompatActivity {
    private ImageView backlibrary;
    private RecyclerView recy_library;
    private List<ModelLibrary> list = new ArrayList<>();

    APIInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library_modules);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        apiInterface = APIClient.getAPIClient().create(APIInterface.class);

        backlibrary = findViewById(R.id.backlibrary);
        recy_library = findViewById(R.id.recy_library);

        apiGetData();


        backlibrary.setOnClickListener(v->{
            startActivity(new Intent(LibraryModules.this, TransitionActivity.class));
        });

    }

    void apiGetData(){
        Call<LibraryResponse> call = apiInterface.getLibraryDetails();
        call.enqueue(new Callback<LibraryResponse>() {
            @Override
            public void onResponse(Call<LibraryResponse> call, Response<LibraryResponse> response) {

                if(response.isSuccessful()){
                    Result result = response.body().getResult();
                    for (int i = 0; i <result.getData().size() ; i++) {
                        list.add(new ModelLibrary(String.valueOf(i),
                                result.getData().get(i).getAuthorName(),
                                result.getData().get(i).getAuthorName(),
                                "05:30",
                                result.getData().get(i).getThumbnailUrl(),
                                result.getData().get(i).getMainlink(),
                                result.getData().get(i).getAuthorUrl(),
                                result.getData().get(i).getTitle()));
                    }

                }

                AdapterLibrary adapterLibrary = new AdapterLibrary(LibraryModules.this, list);
                recy_library.setHasFixedSize(true);
                recy_library.setLayoutManager(new GridLayoutManager(LibraryModules.this, 3));
                recy_library.setAdapter(adapterLibrary);
            }

            @Override
            public void onFailure(Call<LibraryResponse> call, Throwable t) {

            }
        });
    }
}