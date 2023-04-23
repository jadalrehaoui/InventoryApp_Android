package com.jadrehaoui.inventoryv3.utils;

import android.text.TextUtils;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FormValidator {

    public boolean validateRequiredFields(EditText[] requiredFields) {
        int errors = 0;
        for(EditText input: requiredFields) {
            if(TextUtils.isEmpty(input.getText())){
                errors ++;
                input.setError("This field is required.");
            }
        }
        if(errors > 0) {
            return false;
        }
        return true;
    }

    public boolean validateConfirmationFields(EditText [] matchingFields) {
        EditText first = matchingFields[0];
        int errors = 0;
        for(int i = 0; i < matchingFields.length; i++){
            if(!TextUtils.equals(first.getText(), matchingFields[i].getText())){
                matchingFields[i].setError("This field does not match.");
                errors ++;
            }
        }
        if(errors == 0) {
            return true;
        }
        return false;

    }

    public void showErrorOnEmptyFields(EditText [] requiredFields) {
        for(EditText input: requiredFields) {
            if(TextUtils.isEmpty(input.getText())) {
                input.setError("This field is required.");
            }
        }
    }

}
