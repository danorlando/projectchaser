package com.danorlando.projectchaser;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by danorlando on 10/20/14.
 */
public class ProjectPagerActivity extends FragmentActivity implements ProjectFragment.Callbacks {
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        final ArrayList<Project> projects = ProjectLab.get(this).getProjects();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return projects.size();
            }
            @Override
            public Fragment getItem(int pos) {
                UUID projectId =  projects.get(pos).getId();
                return ProjectFragment.newInstance(projectId);
            }
        });

        UUID projectId = (UUID)getIntent().getSerializableExtra(ProjectFragment.EXTRA_PROJECT_ID);
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getId().equals(projectId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    public void onProjectUpdated(Project project) {
        // do nothing
    }
}
