package com.wdz.module_basis.others.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.wdz.module_basis.R;
import com.wdz.module_basis.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wdz.module_basis.others.dialog.DialogType.DIALOG_TYPE_EDIT_TEXT;
import static com.wdz.module_basis.others.dialog.DialogType.DIALOG_TYPE_MESSAGE;

public class CommonDialogFragment extends DialogFragment {
    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R2.id.tv_cancel)
    TextView tvCancel;
    @BindView(R2.id.tv_sure)
    TextView tvSure;

    private String title = "";
    private int dialogType = DIALOG_TYPE_MESSAGE;
    private String message;
    private String positiveButtonText = "";
    private CommonDialogBuilder.OnClickListener mPositiveButtonListener;
    private String negativeButtonText = "";
    private CommonDialogBuilder.OnClickListener mNegativeButtonListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment_common, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * 初始化ui
     */
    private void initView() {
        tvTitle.setText(title);
        tvCancel.setText(negativeButtonText);
        tvSure.setText(positiveButtonText);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNegativeButtonListener!=null){
                    mNegativeButtonListener.onClick();
                }
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPositiveButtonListener!=null){
                    mPositiveButtonListener.onClick();
                }
            }
        });

        if (dialogType == DIALOG_TYPE_MESSAGE){
            View childView = getLayoutInflater().inflate(R.layout.dialog_fragment_message, null);
            TextView tvMessage = childView.findViewById(R.id.tv_message);
            tvMessage.setText(message);
            frameLayout.addView(childView);
        }
        else if (dialogType == DIALOG_TYPE_EDIT_TEXT){
            View childView = getLayoutInflater().inflate(R.layout.dialog_fragment_edittext, null);
            EditText etMessage = childView.findViewById(R.id.et_message);

            frameLayout.addView(childView);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getDialog() != null) {
            if (getDialog().getWindow() != null) {
                getDialog().getWindow().setLayout(dp2px(getContext(), 270), ViewGroup.LayoutParams.WRAP_CONTENT);
            }

        }

    }

    /**
     * dp->px
     */
    public int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

/*---------------------------------------------------------------------------------------------------------*/

    /**
     *
     */
    public static class MessageDialogFragmentBuilder extends CommonDialogBuilder{


        int DIALOG_TYPE_MESSAGE = 0;

        private final CommonDialogFragment commonDialogFragment;
        private final FragmentActivity mActivity;

        public MessageDialogFragmentBuilder(FragmentActivity activity) {
            mActivity = activity;
            commonDialogFragment = new CommonDialogFragment();
            commonDialogFragment.dialogType = DIALOG_TYPE_MESSAGE;
        }


        @Override
        public CommonDialogBuilder setTitle(String title) {
            commonDialogFragment.title = title;
            return this;
        }

        public CommonDialogBuilder setMessage(String message) {
            commonDialogFragment.message = message;
            return this;
        }

        @Override
        public CommonDialogBuilder setPositiveButtonText(String positiveButtonText, OnClickListener onClickListener) {
            commonDialogFragment.positiveButtonText = positiveButtonText;
            commonDialogFragment.mPositiveButtonListener = onClickListener;
            return this;
        }

        @Override
        public CommonDialogBuilder setNegativeButtonText(String negativeButtonText, OnClickListener onClickListener) {
            commonDialogFragment.negativeButtonText = negativeButtonText;
            commonDialogFragment.mNegativeButtonListener = onClickListener;
            return this;
        }
        public void get(){

        }


        @Override
        public CommonDialogFragment bulid() {
            return commonDialogFragment;
        }
        @Override
        public CommonDialogFragment show() {
            CommonDialogFragment dialogFragment = bulid();
            dialogFragment.show(mActivity.getSupportFragmentManager(),"dialog");
            return dialogFragment;
        }
    }




//    //创建普通dialog
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
//        return new AlertDialog.Builder(getActivity())
//                .setTitle("title")
//                .setPositiveButton("ok",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int whichButton) {
//
//                            }
//                        }
//                )
//                .setNegativeButton("cancel",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int whichButton) {
//
//                            }
//                        }
//                )
//                .create();
//    }
}
