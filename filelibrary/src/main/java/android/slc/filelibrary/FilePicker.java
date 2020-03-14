package android.slc.filelibrary;

import android.app.Activity;
import android.content.Intent;
import android.slc.filelibrary.ui.activity.SlcFilePickerActivity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.slc.filelibrary.entity.FileParameter;
import android.slc.filelibrary.utils.FileConstant;

import java.io.FileFilter;
import java.util.regex.Pattern;

public class FilePicker {
    private Activity activity;
    private Fragment supportFragment;
    private android.app.Fragment fragment;
    private FileParameter fileParameter = new FileParameter();
    private Class<?> mFilePickerClass = SlcFilePickerActivity.class;

    public FilePicker(@NonNull Activity activity) {
        this.activity = activity;
    }

    public FilePicker(@NonNull Fragment supportFragment) {
        this.supportFragment = supportFragment;
    }

    public FilePicker(@NonNull android.app.Fragment fragment) {
        this.fragment = fragment;
    }

    public FilePicker isShowHiddenFile(boolean showHiddenFile) {
        fileParameter.setIsShowHiddenFile(showHiddenFile);
        return this;
    }

    public FilePicker sePattern(Pattern pattern) {
        fileParameter.setPattern(pattern);
        return this;
    }


    public FilePicker setDirectoriesFilter(boolean directoriesFilter) {
        fileParameter.setDirectoriesFilter(directoriesFilter);
        return this;
    }

    public FilePicker setFileFilter(FileFilter fileFilter) {
        fileParameter.setFileFilter(fileFilter);
        return this;
    }

    public FilePicker setStartPath(String startPath) {
        fileParameter.setStartPath(startPath);
        return this;
    }

    public FilePicker setFilePickerActivityClass(@NonNull Class<?> filePickerClass) {
        this.mFilePickerClass = filePickerClass;
        return this;
    }

    private Intent getIntent() {
        Activity activityTemp = null;
        if (activity != null) {
            activityTemp = activity;
        } else if (supportFragment != null) {
            activityTemp = supportFragment.getActivity();
        } else if (fragment != null) {
            activityTemp = fragment.getActivity();
        }
        Intent intent = new Intent(activityTemp, this.mFilePickerClass);
        intent.putExtra(FileConstant.KEY_FILE_PARAMETER, fileParameter);
        return intent;
    }

    public void start(int requestCode) {
        if (activity != null) {
            activity.startActivityForResult(getIntent(), requestCode);
        } else if (supportFragment != null) {
            supportFragment.startActivityForResult(getIntent(), requestCode);
        } else if (fragment != null) {
            fragment.startActivityForResult(getIntent(), requestCode);
        }
    }
}
