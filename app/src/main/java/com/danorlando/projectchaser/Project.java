package com.danorlando.projectchaser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

/**
 * Created by danorlando on 10/20/14.
 */
public class Project {

    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_DATE = "date";
    private static final String JSON_COMPLETE = "complete";
    private static final String JSON_PHOTO = "photo";
    private static final String JSON_MANAGER = "manager";

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mComplete;
    private Photo mPhoto;
    private String mManager;

    public Project() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public Project(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        mTitle = json.getString(JSON_TITLE);
        mComplete = json.getBoolean(JSON_COMPLETE);
        mDate = new Date(json.getLong(JSON_DATE));
        if (json.has(JSON_MANAGER))
            mManager = json.getString(JSON_MANAGER);
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_COMPLETE, mComplete);
        json.put(JSON_DATE, mDate.getTime());

        if (mPhoto != null)
            json.put(JSON_PHOTO, mPhoto.toJSON());
        json.put(JSON_MANAGER, mManager);
        return json;
    }

    @Override
    public String toString() { return mTitle; }

    public String getTitle() { return mTitle; }

    public void setTitle(String title) {
        mTitle = title;
    }

    public UUID getId() {
        return mId;
    }

    public boolean isSolved() {
        return mComplete;
    }

    public void setComplete(boolean complete) {
        mComplete = complete;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo p) {
        mPhoto = p;
    }

    public String getManager() {
        return mManager;
    }

    public void setManager(String manager) {
        mManager = manager;
    }
}
