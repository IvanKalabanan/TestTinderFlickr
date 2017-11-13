package ivan.example.com.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mindorks.placeholderview.SwipeDecor;

import java.util.List;

import ivan.example.com.R;
import ivan.example.com.databinding.FragmentPhotosBinding;
import ivan.example.com.domain.models.Photo;
import ivan.example.com.domain.repository.base.RestAPICommunicator;
import ivan.example.com.presentation.presenters.impl.PhotosPresenterImpl;
import ivan.example.com.presentation.presenters.interfaces.PhotosPresenter;
import ivan.example.com.presentation.ui.view.PhotoCard;

/**
 * Created by ivan on 12.11.17.
 */

public class PhotosFragment extends BaseFragment implements PhotosPresenter.View {
    public static final String TAG = "PhotosFragment";

    private static final String ALBUM_ID = "album_id";
    private static final String LIKE_COUNT = "like_count";
    private static final String DISLIKE_COUNT = "dislike_count";

    private PhotosPresenter presenter;
    private FragmentPhotosBinding binding;

    private OnFinishMarkPhotoListener onFinishMarkPhotoListener;

    public static PhotosFragment newInstance(String albumId, int likeCount, int dislikeCount) {

        Bundle args = new Bundle();

        PhotosFragment fragment = new PhotosFragment();
        args.putString(ALBUM_ID, albumId);
        args.putInt(LIKE_COUNT, likeCount);
        args.putInt(DISLIKE_COUNT, dislikeCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentPhotosBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new PhotosPresenterImpl(
                RestAPICommunicator.getInstance().getCalls(),
                getArguments().getString(ALBUM_ID),
                getArguments().getInt(LIKE_COUNT),
                getArguments().getInt(DISLIKE_COUNT),
                this
        );
        presenter.getPhotos();
        setStartedLikeDislikeCount();
    }

    private void setStartedLikeDislikeCount() {
        showLikeCount(getArguments().getInt(LIKE_COUNT));
        showDislikeCount(getArguments().getInt(DISLIKE_COUNT));
    }

    @Override
    public void showPhotos(List<Photo> photoList) {
        binding.swipePhotoView.getBuilder()
                .setSwipeDecor(new SwipeDecor()
                        .setPaddingTop(20)
                        .setSwipeInMsgLayoutId(R.layout.item_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.item_swipe_out_msg_view));
        for (int i = 0; i < photoList.size(); i++) {
            binding.swipePhotoView.addView(new PhotoCard(
                    photoList.get(i),
                    i == (photoList.size() - 1), // if 'i' = last item position so we set 'true' else 'false'
                    photoActionListener,
                    new PhotoCard.OnPhotoActionListener() {
                        @Override
                        public void onLike() {
                            presenter.increaseLikeCount();
                        }

                        @Override
                        public void onDislike() {
                            presenter.increaseDislikeCount();
                        }

                        @Override
                        public void onLastItem() {
                            getActivity().onBackPressed();
                        }
                    }
            ));
        }
    }

    @Override
    public void showLikeCount(int count) {
        binding.likesCounterTv.setText(getString(
                R.string.like_counter,
                String.valueOf(count)
        ));
    }

    @Override
    public void showDislikeCount(int count) {
        binding.unlikesCounterTv.setText(getString(
                R.string.like_counter,
                String.valueOf(count)
        ));
    }

    @Override
    public void showError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void hideProgress() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        onFinishMarkPhotoListener.onFinishMarkPhoto(
                presenter.getLikeCounter(),
                presenter.getDislikeCounter()
        );
        super.onDestroyView();
    }

    public PhotosFragment setOnFinishMarkPhotoListener(OnFinishMarkPhotoListener listener) {
        this.onFinishMarkPhotoListener = listener;
        return this;
    }

    public interface OnFinishMarkPhotoListener {
        void onFinishMarkPhoto(int likeCounter, int dislikeCounter);
    }

}
