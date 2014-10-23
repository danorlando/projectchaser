package com.danorlando.projectchaser;

import java.util.Date;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.UUID;

/**
 * Created by danorlando on 10/20/14.
 */
public class ProjectFragment extends Fragment {
    public static final String EXTRA_PROJECT_ID = "projectchaser.PROJECT_ID";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_IMAGE = "image";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_CONTACT = 2;

    Project mProject;
    EditText mTitleField;
    Button mDateButton;
    CheckBox mSolvedCheckBox;
    ImageButton mPhotoButton;
    ImageView mPhotoView;
    Button mManagerButton;
    Callbacks mCallbacks;

    public interface Callbacks {
        void onProjectUpdated(Project project);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (Callbacks)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    public static ProjectFragment newInstance(UUID projectId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PROJECT_ID, projectId);

        ProjectFragment fragment = new ProjectFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID projectId = (UUID)getArguments().getSerializable(EXTRA_PROJECT_ID);
        mProject = ProjectLab.get(getActivity()).getProject(projectId);

        setHasOptionsMenu(true);
    }

    public void updateDate() {
        mDateButton.setText(mProject.getDate().toString());
    }

    @Override
    @TargetApi(11)
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_project, parent, false);

        mTitleField = (EditText)v.findViewById(R.id.project_title);
        mTitleField.setText(mProject.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mProject.setTitle(c.toString());
                mCallbacks.onProjectUpdated(mProject);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable p) {
                // this one too
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.project_solved);
        mSolvedCheckBox.setChecked(mProject.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // set the project's solved property
                mProject.setComplete(isChecked);
                mCallbacks.onProjectUpdated(mProject);
            }
        });

        mDateButton = (Button)v.findViewById(R.id.project_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mProject.getDate());
                dialog.setTargetFragment(ProjectFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mPhotoButton = (ImageButton)v.findViewById(R.id.project_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // launch the camera activity
                Intent i = new Intent(getActivity(), ProjectCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        // if camera is not available, disable camera functionality
        PackageManager pm = getActivity().getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) &&
                !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            mPhotoButton.setEnabled(false);
        }

        mPhotoView = (ImageView)v.findViewById(R.id.project_imageView);
        mPhotoView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Photo p = mProject.getPhoto();
                if (p == null)
                    return;

                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                String path = getActivity()
                        .getFileStreamPath(p.getFilename()).getAbsolutePath();
                ImageFragment.createInstance(path)
                        .show(fm, DIALOG_IMAGE);
            }
        });

        mManagerButton = (Button)v.findViewById(R.id.project_managerButton);
        mManagerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, REQUEST_CONTACT);
            }
        });
        if (mProject.getManager() != null) {
            mManagerButton.setText(mProject.getManager());
        }

        Button reportButton = (Button)v.findViewById(R.id.project_reportButton);
        reportButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getProjectReport());
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.project_report_subject));
                i = Intent.createChooser(i, getString(R.string.send_report));
                startActivity(i);
            }
        });

        return v;
    }

    private void showPhoto() {
        // (re)set the image button's image based on our photo
        Photo p = mProject.getPhoto();
        BitmapDrawable b = null;
        if (p != null) {
            String path = getActivity()
                    .getFileStreamPath(p.getFilename()).getAbsolutePath();
            b = PictureUtils.getScaledDrawable(getActivity(), path);
        }
        mPhotoView.setImageDrawable(b);
    }

    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mPhotoView);
    }

    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mProject.setDate(date);
            mCallbacks.onProjectUpdated(mProject);
            updateDate();
        } else if (requestCode == REQUEST_PHOTO) {
            // create a new Photo object and attach it to the project
            String filename = data
                    .getStringExtra(ProjectCameraFragment.EXTRA_PHOTO_FILENAME);
            if (filename != null) {
                Photo p = new Photo(filename);
                mProject.setPhoto(p);
                mCallbacks.onProjectUpdated(mProject);
                showPhoto();
            }
        } else if (requestCode == REQUEST_CONTACT) {
            Uri contactUri = data.getData();
            String[] queryFields = new String[] { ContactsContract.Contacts.DISPLAY_NAME_PRIMARY };
            Cursor c = getActivity().getContentResolver()
                    .query(contactUri, queryFields, null, null, null);

            if (c.getCount() == 0) {
                c.close();
                return;
            }

            c.moveToFirst();
            String manager = c.getString(0);
            mProject.setManager(manager);
            mCallbacks.onProjectUpdated(mProject);
            mManagerButton.setText(manager);
            c.close();
        }
    }

    private String getProjectReport() {
        String solvedString = null;
        if (mProject.isSolved()) {
            solvedString = getString(R.string.project_report_solved);
        } else {
            solvedString = getString(R.string.project_report_unsolved);
        }

        String dateFormat = "EEE, MMM dd";
        String dateString = DateFormat.format(dateFormat, mProject.getDate()).toString();

        String manager = mProject.getManager();
        if (manager == null) {
            manager = getString(R.string.project_report_no_manager);
        } else {
            manager = getString(R.string.project_report_manager, manager);
        }

        String report = getString(R.string.project_report, mProject.getTitle(), dateString, solvedString, manager);

        return report;
    }

    @Override
    public void onPause() {
        super.onPause();
        ProjectLab.get(getActivity()).saveProjects();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
