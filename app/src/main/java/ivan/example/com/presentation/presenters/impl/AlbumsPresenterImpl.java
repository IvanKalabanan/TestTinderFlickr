package ivan.example.com.presentation.presenters.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ivan.example.com.domain.interactors.impl.FlickrInteractorImpl;
import ivan.example.com.domain.interactors.interfaces.FlickrInteractor;
import ivan.example.com.domain.models.Album;
import ivan.example.com.domain.repository.APICalls;
import ivan.example.com.presentation.presenters.interfaces.AlbumsPresenter;

/**
 * Created by ivan on 12.11.17.
 */

public class AlbumsPresenterImpl implements AlbumsPresenter {

    private AlbumsPresenter.View view;
    private FlickrInteractor flickrInteractor;

    public AlbumsPresenterImpl(
            APICalls calls,
            AlbumsPresenter.View view) {
        this.view = view;
        flickrInteractor = new FlickrInteractorImpl(calls);
    }

    @Override
    public void getAlbums() {
        flickrInteractor.getAlbums(new FlickrInteractor.OnRetrieveAlbumsListener() {
            @Override
            public void onRetrieve(List<Album> albumList) {
                // sort list of albums by upload date
                Collections.sort(albumList, new Comparator<Album>() {
                    @Override
                    public int compare(Album o1, Album o2) {
                        return Long.valueOf(o2.getUploadDate()).compareTo(o1.getUploadDate());
                    }
                });
                //
                view.showAlbums(albumList);
                view.hideProgress();
            }

            @Override
            public void onFailure(String message) {
                view.showError(message);
                view.hideProgress();
            }
        });
    }

}
