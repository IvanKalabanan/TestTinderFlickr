package ivan.example.com.presentation;

import ivan.example.com.presentation.ui.fragments.PhotosFragment;

/**
 * Created by ivan on 12.11.17.
 */

public interface FragmentRequestListener {

    void startAlbumsFragment();

    void startPhotosFragment(String albumId, int likeCount, int dislikeCount, PhotosFragment.OnFinishMarkPhotoListener onFinishMarkPhotoListener);

}
