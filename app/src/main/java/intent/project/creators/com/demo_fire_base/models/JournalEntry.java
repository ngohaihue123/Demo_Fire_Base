package intent.project.creators.com.demo_fire_base.models;

/**
 * Created by ngohaihue on 10/11/17.
 */

public class JournalEntry {
    private String journalId;
    private String title;
    private String content;
    private long dateCreated;
    private long dateModified;
    private String tagId;
    private String tagName;

    public JournalEntry(String journalId, String title, String content, long dateCreated, long dateModified, String tagId, String tagName) {
        this.journalId = journalId;
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.tagId = tagId;
        this.tagName = tagName;
    }

    public JournalEntry() {
    }

    public String getJournalId() {
        return journalId;
    }

    public void setJournalId(String journalId) {
        this.journalId = journalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateModified() {
        return dateModified;
    }

    public void setDateModified(long dateModified) {
        this.dateModified = dateModified;
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

    @Override
    public String toString() {
        return "JournalEntry{" +
                "journalId='" + journalId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", dateCreated=" + dateCreated +
                ", dateModified=" + dateModified +
                ", tagId='" + tagId + '\'' +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
