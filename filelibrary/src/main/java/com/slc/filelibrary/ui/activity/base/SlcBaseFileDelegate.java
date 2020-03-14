package com.slc.filelibrary.ui.activity.base;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.Toast;

import com.slc.filelibrary.R;
import com.slc.filelibrary.entity.FileParameter;
import com.slc.filelibrary.ui.fragment.base.SlcBaseFileListView;
import com.slc.filelibrary.ui.utils.Functions;
import com.slc.filelibrary.utils.FileConstant;

public class SlcBaseFileDelegate {
    public final static String FUNCTION_NAME_PATH_REFRESH = "pathRefresh";
    private final static int CODE_FOR_WRITE_PERMISSION = 100;
    private SlcBaseFileView slcBaseFileView;
    private String currentPath;
    private FileParameter fileParameter;
    private Functions pathRefreshFunctions = new Functions();
    private boolean isInitData;

    public SlcBaseFileDelegate(SlcBaseFileView slcBaseFileView) {
        this.slcBaseFileView = slcBaseFileView;
    }

    public void onCreate() {
        int hasWriteStoragePermission =
                ContextCompat.checkSelfPermission(slcBaseFileView.getAppCompatActivity().getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission == PackageManager.PERMISSION_GRANTED) {
            //拥有权限，执行操作
            slcBaseFileView.initDate();
        } else {
            //没有权限，向用户请求权限
            ActivityCompat.requestPermissions(slcBaseFileView.getAppCompatActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CODE_FOR_WRITE_PERMISSION);
        }
    }

    public void setFunctionsForFragment(SlcBaseFileListView slcBaseFileListView) {
        slcBaseFileListView.setFunctions(pathRefreshFunctions);
    }

    public void initDate() {
        if (!isInitData) {
            isInitData = true;
            fileParameter =
                    (FileParameter) slcBaseFileView.getAppCompatActivity().getIntent().getSerializableExtra(FileConstant.KEY_FILE_PARAMETER);
            if (fileParameter == null) {
                fileParameter = new FileParameter();
            }
            currentPath = fileParameter.getStartPath();
            pathRefreshFunctions.addFunction(new Functions.FunctionWithParam<String>(FUNCTION_NAME_PATH_REFRESH) {
                @Override
                public void function(String currentPath) {
                    slcBaseFileView.pathChange(currentPath);
                }
            });
        }
    }

    public void pathChange(String currentPath) {
        this.currentPath = currentPath;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CODE_FOR_WRITE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                slcBaseFileView.initDate();
            } else {
                //用户不同意，向用户展示该权限作用
                Toast.makeText(slcBaseFileView.getAppCompatActivity().getApplicationContext(),
                        R.string.toast_slc_grant_write_external_storage_Permissions,
                        Toast.LENGTH_LONG).show();
                slcBaseFileView.initDate();
            }
        }
    }

    public void onDestroy() {

    }

    public String getCurrentPath() {
        return currentPath;
    }

    public FileParameter getFileParameter() {
        return fileParameter;
    }

}
