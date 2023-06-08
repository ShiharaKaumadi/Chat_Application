package lk.ijse.mychat.assets.emojis;

public class Emoji {
    String smileEmoji = "\uD83D\uDE03";
    String redHeart = "U+2764 U+FE0F U+200D U+2764 U+FE0F";
    String thumbsUp = "U+1F44D";

    public Emoji(String smileEmoji, String redHeart, String thumbsUp) {
        this.smileEmoji = smileEmoji;
        this.redHeart = redHeart;
        this.thumbsUp = thumbsUp;
    }

    public Emoji(String smileEmoji) {
        this.smileEmoji = smileEmoji;
    }

    public Emoji() {
    }

    public String getSmileEmoji() {
        return smileEmoji;
    }

    public void setSmileEmoji(String smileEmoji) {
        this.smileEmoji = smileEmoji;
    }

    public String getRedHeart() {
        return redHeart;
    }

    public void setRedHeart(String redHeart) {
        this.redHeart = redHeart;
    }

    public String getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(String thumbsUp) {
        this.thumbsUp = thumbsUp;
    }
}
