package ivan.example.com.domain.interactors.interfaces;

import java.util.List;

import ivan.example.com.domain.models.Album;
import ivan.example.com.domain.models.Photo;

/**
 * Created by ivan on 12.11.17.
 */

public interface FlickrInteractor {
    void getAlbums(OnRetrieveAlbumsListener onRetrieveAlbumsListener);

    void getPhotos(String albumId, OnRetrievePhotosListener onRetrievePhotosListener);

    interface OnRetrieveAlbumsListener{
        void onRetrieve(List<Album> albumList);
        void onFailure(String message);
    }

    interface OnRetrievePhotosListener{
        void onRetrieve(List<Photo> photoList);
        void onFailure(String message);
    }

}
