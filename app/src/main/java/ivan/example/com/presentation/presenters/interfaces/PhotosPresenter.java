package ivan.example.com.presentation.presenters.interfaces;

import java.util.List;

import ivan.example.com.domain.models.Album;
import ivan.example.com.domain.models.Photo;

/**
 * Created by ivan on 12.11.17.
 */

public interface PhotosPresenter {

    interface View {

        void showPhotos(List<Photo> photoList);

        void showLikeCount(int count);

        void showDislikeCount(int count);

        void showError(String message);

        void hideProgress();

    }

    void getPhotos();

    void increaseLikeCount();

    void increaseDislikeCount();

    int getLikeCounter();

    int getDislikeCounter();

}
