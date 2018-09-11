package cc.buddies.app.appdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.retrofit.AppRetrofit;
import cc.buddies.app.appdemo.retrofit.GitHubService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitActivity extends BaseActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        textView = findViewById(R.id.text_view);
    }

    public void onClickButton(View view) {

    }

    private void net() {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl(ServerConfig.BASE_URL).build();
//        GitHubService gitHubService = retrofit.create(GitHubService.class);

        GitHubService service = AppRetrofit.retrofit().create(GitHubService.class);
        Call<List<GitHubService.Repo>> call = service.listRepos("zhaowenliang");

//        call.execute();
        call.enqueue(new Callback<List<GitHubService.Repo>>() {
            @Override
            public void onResponse(Call<List<GitHubService.Repo>> call, Response<List<GitHubService.Repo>> response) {
//                response.body();
            }

            @Override
            public void onFailure(Call<List<GitHubService.Repo>> call, Throwable t) {

            }
        });
    }

}
