package de.teberhardt.ablams.domain;

public class AudioCharacteristics {
    private String channels;
    private int trackLength;
    private String bitRate;
    private int sampleRateAsNumber;
    private String encodingType;

    public void setChannels(String channels) {
        this.channels = channels;
    }

    public String getChannels() {
        return channels;
    }

    public void setTrackLength(int trackLength) {
        this.trackLength = trackLength;
    }

    public int getTrackLength() {
        return trackLength;
    }

    public void setBitRate(String bitRate) {
        this.bitRate = bitRate;
    }

    public String getBitRate() {
        return bitRate;
    }

    public void setSampleRateAsNumber(int sampleRateAsNumber) {
        this.sampleRateAsNumber = sampleRateAsNumber;
    }

    public int getSampleRateAsNumber() {
        return sampleRateAsNumber;
    }

    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    public String getEncodingType() {
        return encodingType;
    }
}
