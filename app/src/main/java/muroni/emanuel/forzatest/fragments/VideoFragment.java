package muroni.emanuel.forzatest.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.PlayerSelector;
import im.ene.toro.widget.Container;
import muroni.emanuel.forzatest.R;
import muroni.emanuel.forzatest.adapters.VideoAdapter;
import muroni.emanuel.forzatest.models.VideoModel;
import muroni.emanuel.forzatest.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {

    private final static String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle bundleRecyclerViewState;

    @BindView(R.id.videos_container)
    Container container;

    ArrayList<VideoModel> videoList = new ArrayList<>();

    public VideoFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_video, container, false);

        ButterKnife.bind(this, v);

        initVideoList();

        settingContainer();

        return v;
    }

    private void initVideoList() {
        videoList.add(new VideoModel("One", Constants.URL_VIDEO_4));
        videoList.add(new VideoModel("Two", Constants.URL_VIDEO_2));
        videoList.add(new VideoModel("Three", Constants.URL_VIDEO_3));
        videoList.add(new VideoModel("Four", Constants.URL_VIDEO_1));
        videoList.add(new VideoModel("Five", Constants.URL_VIDEO_5));
    }

    private void settingContainer() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        container.setLayoutManager(linearLayoutManager);
        VideoAdapter videoAdapter = new VideoAdapter(getContext(), videoList);
        container.setAdapter(videoAdapter);
        container.setPlayerSelector(selector);
    }

    PlayerSelector selector = PlayerSelector.DEFAULT; // visible to user by default.

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            selector = PlayerSelector.DEFAULT;
        } else {
            selector = PlayerSelector.NONE;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        bundleRecyclerViewState = new Bundle();
        Parcelable state = container.getLayoutManager().onSaveInstanceState();
        bundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, state);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (bundleRecyclerViewState != null) {
            Parcelable state = bundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            container.getLayoutManager().onRestoreInstanceState(state);
        }
    }
}
