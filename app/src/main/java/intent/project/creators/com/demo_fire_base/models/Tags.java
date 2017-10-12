package intent.project.creators.com.demo_fire_base.models;

/**
 * Created by ngohaihue on 10/11/17.
 */

public class Tags {
    private String tagId;
    private String tagName;
    private int journalCount;

    public Tags() {
    }

    public Tags(String tagId, String tagName, int journalCount) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.journalCount = journalCount;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public int getJournalCount() {
        return journalCount;
    }

    public void setJournalCount(int journalCount) {
        this.journalCount = journalCount;
    }

    @Override
    public String toString() {
        return "Tags{" +
                "tagId='" + tagId + '\'' +
                ", tagName='" + tagName + '\'' +
                ", journalCount=" + journalCount +
                '}';
    }
}
