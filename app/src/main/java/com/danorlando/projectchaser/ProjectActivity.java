package com.danorlando.projectchaser;

import java.util.UUID;

import android.support.v4.app.Fragment;

/**
 * Created by danorlando on 10/21/14.
 */
public class ProjectActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        UUID projectId = (UUID)getIntent()
                .getSerializableExtra(ProjectFragment.EXTRA_PROJECT_ID);
        return ProjectFragment.newInstance(projectId);
    }
}
