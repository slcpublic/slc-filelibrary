package android.slc.filelibrary.ui.fragment;

import android.app.Activity;
import android.content.Intent;

import android.slc.filelibrary.entity.BaseFile;
import android.slc.filelibrary.utils.FileConstant;

public class SlcFilePickerFragment extends SlcSimpleFileFragment {
    @Override
    protected void onItemFileClick(BaseFile baseFile, int position) {
        super.onItemFileClick(baseFile, position);
        Intent intent = new Intent();
        intent.putExtra(FileConstant.KEY_FILE_PATH, baseFile.getPath());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    }

    @Override
    protected SlcSimpleFileFragment getFileFragment() {
        return new SlcFilePickerFragment();
    }
}
