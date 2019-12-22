package xyz.intellij.player.util.entity;

import java.io.Serializable;
import java.util.Objects;

public class StreamEntity implements Serializable {
    private int streamId;
    private String streamUrl;
    private String streamName;
    private String streamInterduce;
    private byte streamFail;
    private Double streamCrate;
    private Double streamQrate;

    public int getStreamId() {
        return streamId;
    }

    public void setStreamId(int streamId) {
        this.streamId = streamId;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getStreamInterduce() {
        return streamInterduce;
    }

    public void setStreamInterduce(String streamInterduce) {
        this.streamInterduce = streamInterduce;
    }
    public byte getStreamFail() {
        return streamFail;
    }

    public void setStreamFail(byte streamFail) {
        this.streamFail = streamFail;
    }

    public Double getStreamCrate() {
        return streamCrate;
    }

    public void setStreamCrate(Double streamCrate) {
        this.streamCrate = streamCrate;
    }

    public Double getStreamQrate() {
        return streamQrate;
    }

    public void setStreamQrate(Double streamQrate) {
        this.streamQrate = streamQrate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StreamEntity that = (StreamEntity) o;
        return streamId == that.streamId &&
                streamFail == that.streamFail &&
                Objects.equals(streamUrl, that.streamUrl) &&
                Objects.equals(streamName, that.streamName) &&
                Objects.equals(streamInterduce, that.streamInterduce) &&
                Objects.equals(streamCrate, that.streamCrate) &&
                Objects.equals(streamQrate, that.streamQrate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streamId, streamUrl, streamName, streamInterduce, streamFail, streamCrate, streamQrate);
    }
}
