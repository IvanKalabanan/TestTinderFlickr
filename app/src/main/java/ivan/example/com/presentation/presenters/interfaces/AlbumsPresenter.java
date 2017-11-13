package ivan.example.com.presentation.presenters.interfaces;

import java.util.List;

import ivan.example.com.domain.models.Album;

/**
 * Created by ivan on 12.11.17.
 */

public interface AlbumsPresenter {

    interface View {

        void showAlbums(List<Album> albumList);

        void showError(String message);

        void hideProgress();

    }

    void getAlbums();

}
