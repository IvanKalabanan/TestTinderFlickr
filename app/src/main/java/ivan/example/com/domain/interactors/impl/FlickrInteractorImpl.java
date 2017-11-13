package ivan.example.com.domain.interactors.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ivan.example.com.domain.interactors.interfaces.FlickrInteractor;
import ivan.example.com.domain.models.Album;
import ivan.example.com.domain.models.Photo;
import ivan.example.com.domain.repository.APICalls;
import ivan.example.com.domain.utils.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ivan on 12.11.17.
 */

public class FlickrInteractorImpl implements FlickrInteractor {

    private APICalls calls;

    public FlickrInteractorImpl(APICalls calls) {
        this.calls = calls;
    }

    @Override
    public void getAlbums(final OnRetrieveAlbumsListener onRetrieveAlbumsListener) {
        calls.getAlbums().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constants.CODE_OK) {
                    // I used only few field from response
                    // so I didn't want to create 'wrapper' classes for auto parse data to model
                    try {
                        JSONObject responseObject = new JSONObject(response.body().string());
                        JSONArray photosetList = responseObject.getJSONObject("photosets").getJSONArray("photoset");
                        List<Album> albumList = new ArrayList<>();
                        for (int i = 0; i < photosetList.length(); i++) {
                            JSONObject photosetObject = photosetList.getJSONObject(i);
                            albumList.add(new Album(
                                    photosetObject.getString("id"),
                                    photosetObject.getInt("photos"),
                                    photosetObject.getJSONObject("title").getString("_content"),
                                    photosetObject.getJSONObject("description").getString("_content"),
                                    photosetObject.getString("date_create"),
                                    photosetObject.getJSONObject("primary_photo_extras").getString("url_o")
                            ));
                        }
                        onRetrieveAlbumsListener.onRetrieve(albumList);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        onRetrieveAlbumsListener.onFailure(e.getMessage());
                    }
                return;
                }
                onRetrieveAlbumsListener.onFailure(Constants.SERVER_OCCURRED_ERROR);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onRetrieveAlbumsListener.onFailure(Constants.SERVER_OCCURRED_ERROR);
            }
        });
    }

    @Override
    public void getPhotos(String albumId, final OnRetrievePhotosListener onRetrievePhotosListener) {
        calls.getPhotos(albumId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == Constants.CODE_OK) {
                    // I used only few field from response
                    // so I didn't want to create 'wrapper' classes for auto parse data to model
                    try {
                        JSONObject responseObject = new JSONObject(response.body().string());
                        JSONArray photoJsonList = responseObject.getJSONObject("photoset").getJSONArray("photo");
                        List<Photo> photoList = new ArrayList<>();
                        for (int i = 0; i < photoJsonList.length(); i++) {
                            JSONObject photoJsonObject = photoJsonList.getJSONObject(i);
                           photoList.add(new Photo(
                                   photoJsonObject.getString("url_o"),
                                   photoJsonObject.getString("title"),
                                   photoJsonObject.getString("dateupload")
                           ));
                        }
                        onRetrievePhotosListener.onRetrieve(photoList);
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                        onRetrievePhotosListener.onFailure(e.getMessage());
                    }
                    return;
                }
                onRetrievePhotosListener.onFailure(Constants.SERVER_OCCURRED_ERROR);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onRetrievePhotosListener.onFailure(Constants.SERVER_OCCURRED_ERROR);
            }
        });
    }
}
