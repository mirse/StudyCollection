package com.wdz.module_basis.others.dialog;

public abstract class CommonDialogBuilder {
    public abstract CommonDialogBuilder setTitle(String title);
    public abstract CommonDialogBuilder setPositiveButtonText(String positiveButtonText,OnClickListener onClickListener);
    public abstract CommonDialogBuilder setNegativeButtonText(String negativeButtonText,OnClickListener onClickListener);
    public abstract CommonDialogFragment bulid();
    public abstract CommonDialogFragment show();

    interface OnClickListener{
        void onClick();
    }
}
