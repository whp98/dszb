package xyz.intellij.player.util.entity;

import java.util.Objects;

public class RateEntity {
    private long streamId;
    private long userId;
    private Double cRate;
    private Double qRate;

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

    public Double getcRate() {
        return cRate;
    }

    public void setcRate(Double cRate) {
        this.cRate = cRate;
    }

    public Double getqRate() {
        return qRate;
    }

    public void setqRate(Double qRate) {
        this.qRate = qRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateEntity that = (RateEntity) o;
        return streamId == that.streamId &&
                userId == that.userId &&
                Objects.equals(cRate, that.cRate) &&
                Objects.equals(qRate, that.qRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streamId, userId, cRate, qRate);
    }
}
