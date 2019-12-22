package xyz.intellij.player.util.entity;

import java.util.Objects;

public class RateEntityPK {
    private long streamId;
    private long userId;
    public long getStreamId() {
        return streamId;
    }

    public void setStreamId(long streamId) {
        this.streamId = streamId;
    }
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateEntityPK that = (RateEntityPK) o;
        return streamId == that.streamId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(streamId, userId);
    }
}
