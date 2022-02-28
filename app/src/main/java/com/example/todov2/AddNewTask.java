package com.example.todov2;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import db.Task;
import db.TasksDatabaseHandler;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "ActionBottomDialog";

    private EditText newTaskText;
    private Button saveButton;
    private TasksDatabaseHandler db;

    public static AddNewTask newInstance(){
        return new AddNewTask();
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container , Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.new_task,container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view , Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        newTaskText = getView().findViewById(R.id.newTaskText);
        saveButton = getView().findViewById(R.id.saveButton);

        db = new TasksDatabaseHandler(getActivity());
        db.openDatabase();

        boolean isUpdate = false;
        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newTaskText.setText(task);
            if(task.length() >0){
                saveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));

            }
            newTaskText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(charSequence.toString().equals("")){
                        saveButton.setEnabled(false);
                        saveButton.setTextColor(Color.rgb(254,182,159));

                    }else{
                        saveButton.setEnabled(true);
                        saveButton.setTextColor(Color.rgb(69,69,69));

                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    saveButton.setEnabled(true);
                    saveButton.setTextColor(Color.rgb(69,69,69));

                }
            });
            boolean finalIsUpdate = isUpdate;
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = newTaskText.getText().toString();
                    if(finalIsUpdate){
                        db.updateTask(bundle.getInt("id"),text);
                    }else{
                        Task task = new Task();
                        task.setTask(text);
                        task.setStatus(0);
                        db.insertTask(task);
                    }
                    dismiss();
                }
            });
        }else{
            String text = newTaskText.getText().toString();
            Task task = new Task();
            task.setTask(text);
            task.setStatus(0);
            db.insertTask(task);
        }
    }
    @Override
    public void onDismiss(DialogInterface dialog){
        Activity activity = getActivity();
        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialog);
        }
    }
}
