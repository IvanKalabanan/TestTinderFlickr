package ivan.example.com.presentation.presenters.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ivan.example.com.domain.interactors.impl.FlickrInteractorImpl;
import ivan.example.com.domain.interactors.interfaces.FlickrInteractor;
import ivan.example.com.domain.models.Album;
import ivan.example.com.domain.models.Photo;
import ivan.example.com.domain.repository.APICalls;
import ivan.example.com.presentation.presenters.interfaces.AlbumsPresenter;
import ivan.example.com.presentation.presenters.interfaces.PhotosPresenter;

/**
 * Created by ivan on 12.11.17.
 */

public class PhotosPresenterImpl implements PhotosPresenter {

    private View view;
    private FlickrInteractor flickrInteractor;

    private int likesCount;
    private int dislikeCount;

    private String albumId;

    public PhotosPresenterImpl(
            APICalls calls,
            String albumId,
            int likesCount,
            int dislikeCount,
            View view) {
        this.view = view;
        this.albumId = albumId;
        this.likesCount = likesCount;
        this.dislikeCount = dislikeCount;
        flickrInteractor = new FlickrInteractorImpl(calls);
    }

    @Override
    public void getPhotos() {
        flickrInteractor.getPhotos(albumId, new FlickrInteractor.OnRetrievePhotosListener() {
            @Override
            public void onRetrieve(List<Photo> photoList) {
                // sort list of photos by upload date
                Collections.sort(photoList, new Comparator<Photo>() {
                    @Override
                    public int compare(Photo o1, Photo o2) {
                        return Long.valueOf(o2.getUploadDate()).compareTo(o1.getUploadDate());
                    }
                });
                view.showPhotos(photoList);
                view.hideProgress();
            }

            @Override
            public void onFailure(String message) {
                view.showError(message);
                view.hideProgress();
            }
        });
    }

    @Override
    public void increaseLikeCount() {
        likesCount++;
        view.showLikeCount(likesCount);
    }

    @Override
    public void increaseDislikeCount() {
        dislikeCount++;
        view.showDislikeCount(dislikeCount);
    }

    @Override
    public int getLikeCounter() {
        return likesCount;
    }

    @Override
    public int getDislikeCounter() {
        return dislikeCount;
    }
}
