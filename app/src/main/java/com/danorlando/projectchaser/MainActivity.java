package com.danorlando.projectchaser;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;



public class MainActivity extends SingleFragmentActivity
    implements ProjectListFragment.Callbacks, ProjectFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new ProjectListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }


    public void onProjectSelected(Project project) {
        Log.d("onProjectSelected", "MainActivity.onProjectSelected");
        if (findViewById(R.id.detailFragmentContainer) == null) {
            // start an instance of CrimePagerActivity
            Intent i = new Intent(this, ProjectPagerActivity.class);
            i.putExtra(ProjectFragment.EXTRA_PROJECT_ID, project.getId());
            startActivityForResult(i, 0);
        } else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            Fragment oldDetail = fm.findFragmentById(R.id.detailFragmentContainer);
            Fragment newDetail = ProjectFragment.newInstance(project.getId());

            if (oldDetail != null) {
                ft.remove(oldDetail);
            }

            ft.add(R.id.detailFragmentContainer, newDetail);
            ft.commit();
        }
    }

    public void onProjectUpdated(Project project) {
        FragmentManager fm = getSupportFragmentManager();
        ProjectListFragment listFragment = (ProjectListFragment)
                fm.findFragmentById(R.id.fragmentContainer);
        listFragment.updateUI();
    }

}
