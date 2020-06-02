package com.softsquared.template.src.main.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.softsquared.template.R;

public class SortDialog extends Dialog {
    private Context mContext;

    public interface ICustomDialogEventListener {
        void customDialogEvent(int valueYouWantToSendBackToTheActivity);
    }

    private ICustomDialogEventListener onCustomDialogEventListener;

    public SortDialog(@NonNull Context context, int themeResId, ICustomDialogEventListener onCustomDialogEventListener) {
        super(context, themeResId);
        mContext = context;
        this.onCustomDialogEventListener = onCustomDialogEventListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sort_dialog);
        final ImageButton okButton = findViewById(R.id.ibtn_dialog_ok);
        final ImageButton cancelButton = findViewById(R.id.ibtn_dialog_no);
        final RadioGroup radioGroup = findViewById(R.id.dialog_rg);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) radioGroup.findViewById(checkedId);
                if (rb.getText().toString().compareTo(" 거리순") == 0) {
                    onCustomDialogEventListener.customDialogEvent(1);
                } else if (rb.getText().toString().compareTo(" 관련도순") == 0) {
                    onCustomDialogEventListener.customDialogEvent(0);
                } else {
                    onCustomDialogEventListener.customDialogEvent(-1);
                }
                dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCustomDialogEventListener.customDialogEvent(-1);
                dismiss();
            }
        });
    }
}
