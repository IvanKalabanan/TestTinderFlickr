package ivan.example.com.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import ivan.example.com.R;
import ivan.example.com.databinding.FragmentAlbumsBinding;
import ivan.example.com.domain.models.Album;
import ivan.example.com.domain.repository.base.RestAPICommunicator;
import ivan.example.com.presentation.adapters.AlbumsRecyclerViewAdapter;
import ivan.example.com.presentation.presenters.impl.AlbumsPresenterImpl;
import ivan.example.com.presentation.presenters.interfaces.AlbumsPresenter;

/**
 * Created by ivan on 12.11.17.
 */

public class AlbumsFragment extends BaseFragment implements AlbumsPresenter.View {
    public static final String TAG = "AlbumsFragment";

    private AlbumsPresenter presenter;
    private FragmentAlbumsBinding binding;
    private AlbumsRecyclerViewAdapter albumsRecyclerViewAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAlbumsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSwipeToRefresh();
        presenter = new AlbumsPresenterImpl(
                RestAPICommunicator.getInstance().getCalls(),
                this
        );
        binding.swipeRefreshLayout.setRefreshing(true);
        presenter.getAlbums();
    }

    private void initSwipeToRefresh() {
        binding.swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getContext(), R.color.colorPrimary),
                ContextCompat.getColor(getContext(), R.color.colorAccent));
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getAlbums();
            }
        });
    }

    @Override
    public void showAlbums(List<Album> albumList) {
        if (albumsRecyclerViewAdapter != null) {
            albumsRecyclerViewAdapter.setItems(albumList);
            return;
        }
        albumsRecyclerViewAdapter = new AlbumsRecyclerViewAdapter(
                getContext(),
                albumList,
                fragmentRequestListener,
                photoActionListener
        );
        binding.albumsRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.albumsRv.setAdapter(albumsRecyclerViewAdapter);
    }

    @Override
    public void showError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void hideProgress() {
        if (getContext() != null) {
            binding.swipeRefreshLayout.setRefreshing(false);
        }
    }
}
