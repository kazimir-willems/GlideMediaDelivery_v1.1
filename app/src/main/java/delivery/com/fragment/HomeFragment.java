package delivery.com.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import delivery.com.R;
import delivery.com.application.DeliveryApplication;
import delivery.com.consts.StateConsts;
import delivery.com.db.DespatchDB;
import delivery.com.db.OutletDB;
import delivery.com.db.StockDB;
import delivery.com.event.DespatchStoreEvent;
import delivery.com.event.DownloadDespatchEvent;
import delivery.com.event.MakeUploadDataEvent;
import delivery.com.event.RemoveAllDataEvent;
import delivery.com.event.UploadDespatchEvent;
import delivery.com.model.DespatchItem;
import delivery.com.model.OutletItem;
import delivery.com.model.StockItem;
import delivery.com.task.DespatchStoreTask;
import delivery.com.task.DownloadDespatchTask;
import delivery.com.task.MakeUploadDataTask;
import delivery.com.task.RemoveAllDataTask;
import delivery.com.task.UploadDespatchTask;
import delivery.com.ui.LoginActivity;
import delivery.com.ui.MainActivity;
import delivery.com.vo.DownloadDespatchResponseVo;
import delivery.com.vo.UploadDespatchResponseVo;

public class HomeFragment extends Fragment {

    private ProgressDialog progressDialog;

    private static final int LOGIN_REQUEST = 1;
    private int mode = 0;       //1: download, 0: upload

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDownloadDespatchEvent(DownloadDespatchEvent event) {
        hideProgressDialog();
        DownloadDespatchResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            parseDespatches(responseVo.despatch);
        } else {
            networkError();
        }
    }

    @Subscribe
    public void onUploadDespatchEvent(UploadDespatchEvent event) {
        hideProgressDialog();
        UploadDespatchResponseVo responseVo = event.getResponse();
        if (responseVo != null) {
            uploadSuccess();
        } else {
            networkError();
        }
    }

    @Subscribe
    public void onDespatchStoreEvent(DespatchStoreEvent event) {
        hideProgressDialog();
        int result = event.getResponse();
        if(result == 1) {
            downloadSuccess();
        } else if (result == 2) {
            noDespatch();
        } else if (result == 0){
            dbError();
        }
    }

    @Subscribe
    public void onRemoveDespatchesEvent(RemoveAllDataEvent event) {
        hideProgressDialog();
        boolean result = event.getRemoveResult();
        if(result) {
            Toast.makeText(getActivity(), R.string.remove_success, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), R.string.remove_failed, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe
    public void onMakeUploadDataEvent(MakeUploadDataEvent event) {
        hideProgressDialog();
        String result = event.getResponse();
        if(result == null || result.isEmpty()) {
            noCompletedDespatch();
        } else {
            startUploadDespatch(result);
        }
    }

    @OnClick(R.id.btn_download_dispatch)
    void onClickBtnDownDespatch() {
        if(DeliveryApplication.bLoginStatus) {
            requestDownload();
            return;
        }
        mode = 1;

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra("from", 1);

        startActivityForResult(intent, LOGIN_REQUEST);
    }

    @OnClick(R.id.btn_upload_dispatches)
    void onClickBtnUploadDespatch() {
        if(DeliveryApplication.bLoginStatus) {
            requestUpload();
            return;
        }
        mode = 0;
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.putExtra("from", 1);

        startActivityForResult(intent, LOGIN_REQUEST);
    }

    private void requestDownload() {
        progressDialog.setMessage(getResources().getString(R.string.downloading));
        progressDialog.show();
        startDownloadDespatch();
    }

    private void requestUpload() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.msg_enter_the_passcode);

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String passcode = input.getText().toString();

                if(passcode.equals(DeliveryApplication.passcode)) {
                    startUpload();
                } else {
                    Toast.makeText(getActivity(), R.string.msg_passcode_incorrect, Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void startUpload() {
        progressDialog.setMessage(getResources().getString(R.string.processing));
        progressDialog.show();

        MakeUploadDataTask task = new MakeUploadDataTask(getActivity());
        task.execute();
    }

    @OnClick(R.id.btn_remove_datas)
    void onClickBtnRemoveData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_ask_remove_datas));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.setMessage(getResources().getString(R.string.removing));
                progressDialog.show();
                RemoveAllDataTask task = new RemoveAllDataTask(getActivity());
                task.execute();
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    private void startDownloadDespatch() {
        DownloadDespatchTask task = new DownloadDespatchTask();
        task.execute();
    }

    private void startUploadDespatch(String data) {
        progressDialog.setMessage(getResources().getString(R.string.uploading));
        progressDialog.show();

        UploadDespatchTask task = new UploadDespatchTask();
        task.execute(data);
    }

    private void hideProgressDialog() {
        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void uploadSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.upload_success));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    private void networkError() {
        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
    }

    private void noCompletedDespatch() {
        Toast.makeText(getActivity(), R.string.no_completed_despatch, Toast.LENGTH_SHORT).show();
    }

    private void dbError() {
        Toast.makeText(getActivity(), R.string.db_error, Toast.LENGTH_SHORT).show();
    }

    private void downloadSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.download_accomplished));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((MainActivity) getActivity()).showDespatchFragment();
            }
        }).create().show();
    }

    private void noDespatch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.no_despatch));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    private void parseDespatches(String despatches) {
        progressDialog.setMessage(getResources().getString(R.string.processing));
        progressDialog.show();
        DespatchStoreTask task = new DespatchStoreTask(getActivity());
        task.execute(despatches);
    }

    private void showPermissionDenied() {
        Toast.makeText(getActivity(), R.string.permission_denied, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == LOGIN_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                if(mode == 0) {
                    requestUpload();
                } else if (mode == 1) {
                    requestDownload();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
