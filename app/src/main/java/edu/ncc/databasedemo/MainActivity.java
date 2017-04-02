package edu.ncc.databasedemo;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class MainActivity extends ListActivity {

    private DepartmentInfoSource datasource;
    private ArrayAdapter<DepartmentEntry> adapter;
    static final int DEPARTMENT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datasource = new DepartmentInfoSource(this);
        datasource.open();

        List<DepartmentEntry> values = datasource.getAllDepartments();

        // add departments to the database if it is currently empty
        if (values.isEmpty())
        {
            new ParseURL().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        DepartmentEntry dept;
        List<DepartmentEntry> values;
        switch (view.getId()) {

            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    dept = (DepartmentEntry) getListAdapter().getItem(0);
                    datasource.deleteDept(dept);
                    adapter.remove(dept);
                }
                break;
            case R.id.show:
				values = datasource.getAllDepartments();
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                break;
             case R.id.dean:
                values = datasource.query(1);
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                break;

            case R.id.Tower:
                values = datasource.query(2);
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                Toast.makeText(MainActivity.this, "Search Currently Unavailable",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.Cluster:
                values = datasource.query(3);
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                Toast.makeText(MainActivity.this, "Search Currently Unavailable",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.Center:
                values = datasource.query(4);
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                break;
            case R.id.input:
                Intent intent = new Intent(this,SearchActivity.class );
                startActivityForResult(intent, DEPARTMENT_REQUEST, null);
                break;

        }
       // adapter.notifyDataSetChanged();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == DEPARTMENT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                List<DepartmentEntry> values = datasource.chooseQuery(result);
                adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
                setListAdapter(adapter);
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        }
    }
    public void onDestroy()
    {
        datasource.close();
        super.onDestroy();
    }

    private class ParseURL extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String str;
            String deptName;
            String deptPhone;
            String deptLocation;
            Document doc;
            int count = 0;

            try {
                // connect to the Web page
                doc = Jsoup.connect("http://www.ncc.edu/contactus/deptdirectory.shtml").get();

                // find the body of the Web page
                Elements tableEntries = doc.select("tbody");
                for (Element e : tableEntries)
                {
                    // look for a row in the table
                    Elements trs = e.getElementsByTag("tr");

                    // for each element in the row (there are 5)
                    for (Element e2 : trs)
                    {
                        // get the table descriptor
                        Elements tds = e2.getElementsByTag("td");

                        // ignore the first row
                        if (count > 0) {
                            // get the department name and remove the formatting tags
                            deptName = tds.get(0).text();

                            // get the department phone number
                            deptPhone = tds.get(1).text();

                            // get the department location
                            deptLocation = tds.get(4).text();

                            datasource.addDept(deptName, deptLocation, deptPhone);
                        }
                        count++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            //if you had a ui element, you could display the title
            Log.d("PARSING", "async task has completed");
        }
    }
}