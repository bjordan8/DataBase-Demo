package edu.ncc.databasedemo;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {
    private static final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_search);

    }

    static final int DEPARTMENT_REQUEST = 1;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void storeName(View view){

        EditText editText = (EditText)findViewById(R.id.edit_name);
        String result = editText.getText().toString();
        Log.i("Zip Code Result", "The zip code is " + result);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",result);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

}
