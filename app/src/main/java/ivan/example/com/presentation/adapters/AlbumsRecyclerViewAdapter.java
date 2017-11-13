package ivan.example.com.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ivan.example.com.R;
import ivan.example.com.databinding.ItemAlbumBinding;
import ivan.example.com.databinding.ItemPlaceholderBinding;
import ivan.example.com.domain.models.Album;
import ivan.example.com.presentation.DialogConstructor;
import ivan.example.com.presentation.FragmentRequestListener;
import ivan.example.com.presentation.PhotoActionListener;
import ivan.example.com.presentation.ui.fragments.PhotosFragment;

/**
 * Created by ivan on 12.11.17.
 */

public class AlbumsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int PLACEHOLDER_TYPE = 1;

    private final Context context;
    private List<Album> items;
    private FragmentRequestListener fragmentRequestListener;
    private PhotoActionListener photoActionListener;

    public AlbumsRecyclerViewAdapter(Context context, List<Album> albumList, FragmentRequestListener fragmentRequestListener, PhotoActionListener photoActionListener) {
        this.context = context;
        this.items = albumList;
        this.fragmentRequestListener = fragmentRequestListener;
        this.photoActionListener = photoActionListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PLACEHOLDER_TYPE) {
            return new Placeholder(ItemPlaceholderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
        return new AlbumHolder(ItemAlbumBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Placeholder) {
            ((Placeholder) holder).binding.placeholderTv.setText(context.getString(R.string.albums_placeholder));
            return;
        }
        AlbumHolder albumHolder = (AlbumHolder) holder;
        Album album = items.get(position);
        photoActionListener.loadPhoto(albumHolder.binding.photoTitleIv, album.getPhotoUrl());
        albumHolder.binding.textTitleTv.setText(album.getTitle());
        albumHolder.binding.likesCounterTv.setText(String.valueOf(album.getLikeCount()));
        albumHolder.binding.dislikesCounterTv.setText(String.valueOf(album.getDislikeCount()));
    }

    public void setItems(List<Album> list) {
        this.items = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (items == null || items.isEmpty()) {
            return 1;
        }
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items == null || items.isEmpty()) {
            return PLACEHOLDER_TYPE;
        }
        return super.getItemViewType(position);
    }

    private class AlbumHolder extends RecyclerView.ViewHolder {

        private ItemAlbumBinding binding;

        AlbumHolder(ItemAlbumBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            initListeners();
        }

        private void initListeners() {
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Album album = items.get(getAdapterPosition());
                    fragmentRequestListener.startPhotosFragment(
                            album.getId(),
                            album.getLikeCount(),
                            album.getDislikeCount(),
                            new PhotosFragment.OnFinishMarkPhotoListener() {
                                @Override
                                public void onFinishMarkPhoto(int likeCounter, int dislikeCounter) {
                                    if (likeCounter == 0 && dislikeCounter == 0) {
                                        return;
                                    }
                                    album.setLikeCount(likeCounter);
                                    album.setDislikeCount(dislikeCounter);
                                    notifyItemChanged(getAdapterPosition());
                                }
                            });
                }
            });
            binding.infoIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Album album = items.get(getAdapterPosition());
                    DialogConstructor.showAlbumInfoDialog(
                            context,
                            album.getCreateAt(),
                            album.getTitle(),
                            album.getDescription(),
                            album.getPhotoCount()
                    );
                }
            });
        }

    }

    private class Placeholder extends RecyclerView.ViewHolder {
        private ItemPlaceholderBinding binding;

        Placeholder(ItemPlaceholderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
