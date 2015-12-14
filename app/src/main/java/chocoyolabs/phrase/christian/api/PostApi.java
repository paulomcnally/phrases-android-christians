package chocoyolabs.phrase.christian.api;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import chocoyolabs.phrase.christian.models.PostModel;
import chocoyolabs.phrase.christian.ui.adapters.PostAdapter;

public class PostApi extends AsyncTask<String, Void, JSONArray> {

    private static AdapterListener mAdapterListener;

    public interface AdapterListener {
        void onReady(PostAdapter adapter);
    }

    public static void setAdapterListener(AdapterListener adapterListener) {
        mAdapterListener = adapterListener;
    }

    private RecyclerView mRecyclerView;
    private PostAdapter mPostAdapter;
    private Context mContext;
    private boolean mIsNew;
    private int mSkip;

    public PostApi(Context context, RecyclerView recyclerView, PostAdapter postAdapter, boolean isNew, int skip) {
        mContext = context;
        mRecyclerView = recyclerView;
        mPostAdapter = postAdapter;
        mIsNew = isNew;
        mSkip = skip;
    }

    protected void onPreExecute() {

    }

    protected JSONArray doInBackground(String... strings) {
        JSONArray data = null;

        Request request = new Request.Builder()
                .url(Api.getResource(String.format("/Posts/feed?type=cristianas&skip=%d", mSkip)))
                .build();
        Response response;
        try {
            response = Api.getClient().newCall(request).execute();

            if (!response.isSuccessful()) throw new IOException(response.body().toString());

            data = new JSONArray(response.body().string());

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    protected void onPostExecute(JSONArray result) {
        if (result != null) {
            if (result.length() > 0) {
                ArrayList<PostModel> items = new ArrayList<>();

                for (int i = 0; i < result.length(); i++) {
                    JSONObject item = null;
                    try {
                        item = result.getJSONObject(i);

                        PostModel postModel = new PostModel();
                        postModel.setUrl(item.getString("url"));
                        items.add(postModel);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                if (mIsNew) {
                    mPostAdapter = new PostAdapter(items);

                    if (mAdapterListener != null) {
                        mAdapterListener.onReady(mPostAdapter);
                    }

                    mRecyclerView.setAdapter(mPostAdapter);
                }
                else {
                    mPostAdapter.addRow(items);
                }

            }
        }
    }
}
