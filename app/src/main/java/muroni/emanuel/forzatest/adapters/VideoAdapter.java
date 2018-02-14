package muroni.emanuel.forzatest.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerHelper;
import im.ene.toro.exoplayer.MediaSourceBuilder;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;
import muroni.emanuel.forzatest.R;
import muroni.emanuel.forzatest.models.VideoModel;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    private List<VideoModel> videoList;
    private LayoutInflater inflater;

    public VideoAdapter(Context context, List<VideoModel> videoList) {
        this.context = context;
        this.videoList = videoList;
        this.inflater = LayoutInflater.from(context.getApplicationContext());
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(inflater.inflate(R.layout.video_list_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {

        holder.bind(videoList.get(position));
        holder.tvTitle.setText(videoList.get(position).getTitle());

        holder.btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.changeVolume();
            }
        });

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder implements ToroPlayer {

        @BindView(R.id.player)
        SimpleExoPlayerView playerView;
        @BindView(R.id.title)
        TextView tvTitle;
        @BindView(R.id.btn_audio)
        ImageView btnAudio;


        VideoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Nullable
        ExoPlayerHelper helper;
        @Nullable
        private Uri mediaUri;

        void bind(VideoModel media) {
            String mediaUrl = media.getUrl();
            Uri uri = Uri.parse(mediaUrl);
            this.mediaUri = uri;
            Log.v("mediaUri", mediaUri.toString());
        }

        @NonNull
        @Override
        public View getPlayerView() {
            return playerView;
        }

        @NonNull
        @Override
        public PlaybackInfo getCurrentPlaybackInfo() {
            return helper != null ? helper.getPlaybackInfo() : new PlaybackInfo();
        }

        @Override
        public void initialize(@NonNull Container container, @Nullable PlaybackInfo playbackInfo) {
            if (helper == null) {
                try {
                    helper = new ExoPlayerHelper(playerView);
                    helper.prepare(new MediaSourceBuilder(context, mediaUri));
                    helper.setVolume(1f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                helper.setPlaybackInfo(playbackInfo);
            }
        }

        @Override
        public void release() {
            if (helper != null) {
                helper.release();
                helper = null;
            }
        }

        void changeVolume() {
            if (helper != null) {
                Float volume = helper.getVolume();
                if (volume > 0) {
                    mute();
                } else {
                    unmute();
                }
            }
        }

        void mute() {
            if (helper != null) {
                helper.setVolume(0f);
                btnAudio.setImageResource(R.drawable.ic_mute);
            }
        }

        void unmute() {
            if (helper != null) {
                helper.setVolume(1f);
                btnAudio.setImageResource(R.drawable.ic_unmute);
            }
        }

        @Override
        public void play() {
            if (helper != null) helper.play();
        }

        @Override
        public void pause() {
            if (helper != null) helper.pause();
        }

        @Override
        public boolean isPlaying() {
            return helper != null && helper.isPlaying();
        }

        @Override
        public boolean wantsToPlay() {
            return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
        }

        @Override
        public int getPlayerOrder() {
            return getAdapterPosition();
        }

        @Override
        public void onSettled(Container container) {

        }
    }


}
