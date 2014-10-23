package com.danorlando.projectchaser;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by danorlando on 10/20/14.
 */
public class ProjectLab {

    private static final String TAG = "ProjectLab";
    private static final String FILENAME = "projects.json";

    private ArrayList<Project> mProjects;
    private ProjectChaserJSONSerializer mSerializer;

    private static ProjectLab sProjectLab;
    private Context mAppContext;

    private ProjectLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new ProjectChaserJSONSerializer(mAppContext, FILENAME);

        try {
            mProjects = mSerializer.loadProjects();
        } catch (Exception e) {
            mProjects = new ArrayList<Project>();
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public static ProjectLab get(Context c) {
        if (sProjectLab == null) {
            sProjectLab = new ProjectLab(c.getApplicationContext());
        }
        return sProjectLab;
    }

    public Project getProject(UUID id) {
        for (Project p : mProjects) {
            if (p.getId().equals(id))
                return p;
        }
        return null;
    }

    public void addProject(Project c) {
        mProjects.add(c);
        saveProjects();
    }

    public ArrayList<Project> getProjects() {
        return mProjects;
    }

    public void deleteProject(Project c) {
        mProjects.remove(c);
        saveProjects();
    }

    public boolean saveProjects() {
        try {
            mSerializer.saveProjects(mProjects);
            Log.d(TAG, "projects saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving projects: " + e);
            return false;
        }
    }
}
