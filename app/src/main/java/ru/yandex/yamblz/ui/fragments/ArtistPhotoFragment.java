package ru.yandex.yamblz.ui.fragments;


import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.model.Artist;

public class ArtistPhotoFragment extends BaseFragment {
    private static final String ARTIST_ARG = "artist_arg";
    private Artist mArtist;

    @BindView(R.id.fragment_artist_photo_image_cover)
    ImageView mCover;

    @BindView(R.id.fragment_artist_photo_blur_image_cover)
    ImageView mBlurredCover;

    public static ArtistPhotoFragment newInstance(Artist artist) {
        Bundle args = new Bundle();
        args.putParcelable(ARTIST_ARG, artist);
        ArtistPhotoFragment fragment = new ArtistPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface Callbacks {
        void onClickMoreInformation(Artist artist);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_artist_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mArtist = getArguments().getParcelable(ARTIST_ARG);
        Picasso.with(getContext()) // TODO: Glide?
                .load(mArtist.getUrlOfSmallCover())
                .placeholder(R.drawable.ic_album_black_400dp)
                .error(R.drawable.ic_album_black_400dp)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        mCover.setImageBitmap(bitmap);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            mBlurredCover.setImageBitmap(blurBitmap(bitmap));
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        mCover.setImageDrawable(errorDrawable);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        mCover.setImageDrawable(placeHolderDrawable);
                    }
                });
    }

    @OnClick(R.id.fragment_artist_photo_button_more)
    void showInformation() {
        Callbacks callbacks = (Callbacks) getActivity();
        if (callbacks != null) {
            callbacks.onClickMoreInformation(mArtist);
        }
    }

    // TODO: Подумать, где место блюрингу
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blurBitmap(Bitmap bitmap) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(getContext().getApplicationContext());

        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        blurScript.setRadius(25.0f);

        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        allOut.copyTo(outBitmap);

        rs.destroy();

        return outBitmap;

    }
}