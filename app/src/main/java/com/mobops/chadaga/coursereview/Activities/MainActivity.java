package com.mobops.chadaga.coursereview.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobops.chadaga.coursereview.Adapters.MainAdapter;
import com.mobops.chadaga.coursereview.Adapters.SearchAndFilterAdapter;
import com.mobops.chadaga.coursereview.Databases.DatabaseMain;
import com.mobops.chadaga.coursereview.Databases.DatabaseForSearch;
import com.mobops.chadaga.coursereview.Objects.Course;
import com.mobops.chadaga.coursereview.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseMain db;
    int depStatus = 0;
    Context context;
    Spinner spinner;
    TextView tvError;

    public final static String EXTRA_MESSAGE1 = "TheExtraMessage1";
    public final static String EXTRA_MESSAGE2 = "TheExtraMessage2";
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Some operations that is run only for the first time the app is run
        boolean firstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("firstRun", true);
        if (firstRun) firstRunOperations();

        setContentView(R.layout.activity_main);
        context = this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        tvError = (TextView) findViewById(R.id.tv_error);

        spinner = (Spinner) findViewById(R.id.spinner);
        setSpinner(spinner);

        SearchAndFilterAdapter filterAdapter = new SearchAndFilterAdapter();

        List<String> name = new ArrayList<String>();
        List<String> number = new ArrayList<String>();
        int cnt;

        Intent intent = getIntent();
        cnt = intent.getIntExtra("COUNT", 0);
        name = intent.getStringArrayListExtra("NAME_LIST");
        number = intent.getStringArrayListExtra("NUMBER_LIST");
        Boolean isFromSubmit = intent.getBooleanExtra("BOOLEAN_FROM_ADD_REVIEW_ACTIVITY", false);

        //If main activity started after a review was submitted then show the snackBar
        View parentLayout = findViewById(android.R.id.content);
        if (isFromSubmit) {
            showSubmitSnackBar(parentLayout);
        }

        filterAdapter.setCount(cnt);
        filterAdapter.setCourseName(name);
        filterAdapter.setCourseCode(number);
        recyclerView.setAdapter(filterAdapter);

    }

    private void firstRunOperations() {
        DatabaseMain db = new DatabaseMain(this);
        //Filling up the databse on first run

        db.addCourse(new Course("Basic Electrical Engineering",
                "EE1100",
                "Bijoy Krishna Das, Soumya Dutta, Deleep R Nair, Kalyaan Kumar B"));
        db.addCourse(new Course("Computer Organization",
                "EE2003",
                "Jhunjhunwala Ashok, Sridharan K"));
        db.addCourse(new Course("Digital Signal Processing",
                "EE2004",
                "Ramalingam C S, Umesh S"));
        db.addCourse(new Course("Electrical Machines and components",
                "EE2005",
                "Kamalesh Hatua, Krishna Vasudevan"));
        db.addCourse(new Course("Electrical Circuits and Analysis",
                "EE2015",
                "Anjan Chakraborty, Devendra Jalihal"));

        db.addCourse(new Course("Engineering Mechanics",
                "AM1100",
                "Lakshmana Rao, Sujatha N, Ramesh K"));
        db.addCourse(new Course("Life Sciences in Biotechnology",
                "BT1010",
                "Amal Kanthi, Chandra T S"));
        db.addCourse(new Course("Biomechanics theory and Lab",
                "AM5110",
                "Rama Subba Reddy, Ramakrishnan"));
        db.addCourse(new Course("Introduction to Aerospace lab",
                "AS2100",
                "Joel George, Sujith R I, Ranjith Mohan"));
        db.addCourse(new Course("Structural Design Aerospace Engineers",
                "AS5220",
                "Anjan"));
        db.addCourse(new Course("Surveying in Chemical Engineering",
                "CE2080",
                "Ananthnarayan, Thayagaraj"));
        db.addCourse(new Course("Mechanics of Material",
                "CE2310",
                "Apparao K, RaghuKanth S T G"));
        db.addCourse(new Course("Heat and Mass Transfer Lab",
                "CH3520",
                "Ravi Krishna R"));
        db.addCourse(new Course("Momentum Transfer in Chemistry",
                "CH2030",
                "Preethi Agalayam"));
        db.addCourse(new Course("Computational Engineering",
                "CS1100",
                "Narayanaswamy N S, Pandurangan C"));
        db.addCourse(new Course("Data structures and Algorithm",
                "CS2800",
                "Pandurangan C"));
        db.addCourse(new Course("Functional and Conceptual Design",
                "ED1100",
                "Ashok T"));
        db.addCourse(new Course("Graphic Art for Engineering Design",
                "ED1300",
                "Nilesh Vasa"));
        db.addCourse(new Course("Thermodynamics for Mechanical Engineers",
                "ME1100",
                "Mani A, Ramesh A, Raghavan V"));
        db.addCourse(new Course("Engineering Drawing basic course",
                "ED1120",
                "Krishna Kannan, Amithava Gosh"));


        getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .edit()
                .putBoolean("firstRun", false)
                .commit();
    }

    private void setSpinner(Spinner spinner) {

        spinner.setAdapter(new MySpinnerAdapter(
                toolbar.getContext(),
                new String[]{
                        "All departments",
                        "Applied Mechanics",
                        "Aerospace Engineering",
                        "Biotechnology",
                        "Civil Engineering",
                        "Chemical Engineering",
                        "Computer Science",
                        "Engineering Design",
                        "Electrical Engineering",
                        "Mechanical Engineering"
                }));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                depStatus = position;
                chooseSpinnerTab(position, 0);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private void chooseSpinnerTab(int position, int sortCode) {

        switch (position) {
            //case 0: All departments
            case 0: {
                MainAdapter mainAdapter = new MainAdapter();

                if (sortCode == 0) {

                    mainAdapter.setQuery("SELECT * FROM courses ORDER BY courseNumber ASC");
                    db = new DatabaseMain(context);
                    int cnt = (int) db.getCoursesCount("SELECT * FROM courses");
                    mainAdapter.setCnt(cnt);
                    recyclerView.setAdapter(mainAdapter);

                } else if (sortCode == 1) {

                    mainAdapter.setQuery("SELECT * FROM courses ORDER BY courseName ASC");
                    db = new DatabaseMain(context);
                    int cnt = (int) db.getCoursesCount("SELECT * FROM courses");
                    mainAdapter.setCnt(cnt);
                    recyclerView.setAdapter(mainAdapter);

                }

                break;
            }
            //Following cases: these are specific departments
            case 1: {
                setRecyclerView("AM", sortCode);
                break;
            }
            case 2: {
                setRecyclerView("AS", sortCode);
                break;
            }
            case 3: {
                setRecyclerView("BT", sortCode);
                break;
            }
            case 4: {
                setRecyclerView("CE", sortCode);
                break;
            }
            case 5: {
                setRecyclerView("CH", sortCode);
                break;
            }
            case 6: {
                setRecyclerView("CS", sortCode);
                break;
            }
            case 7: {
                setRecyclerView("ED", sortCode);
                break;
            }
            case 8: {
                setRecyclerView("EE", sortCode);
                break;
            }
            case 9: {
                setRecyclerView("ME", sortCode);
                break;
            }
        }

    }

    private void setRecyclerView(String query, int sortCode) {

        SearchAndFilterAdapter searchAndFilterAdapter = new SearchAndFilterAdapter();

        List<String> nameList = new ArrayList<String>();
        List<String> codeList = new ArrayList<String>();
        int count = 0;

        DatabaseForSearch databaseForSearch = new DatabaseForSearch(context);
        Cursor cursor = databaseForSearch.getCourseMatches(query/*Search for this string*/, null/*in column*/, sortCode/*sort asc or desc*/);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String nameString = cursor.getString(1);
                    String codeString = cursor.getString(0);
                    nameList.add(nameString);
                    codeList.add(codeString);
                    count++;
                } while (cursor.moveToNext());
            }
        }
        searchAndFilterAdapter.setCount(count);
        searchAndFilterAdapter.setCourseName(nameList);
        searchAndFilterAdapter.setCourseCode(codeList);
        recyclerView.setAdapter(searchAndFilterAdapter);
    }

    private void showSubmitSnackBar(View parentLayout) {
        Snackbar snackbar;
        snackbar = Snackbar.make(parentLayout, "We will verify your response and make it public soon. Thank you!", Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setTextSize(14);
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                //Once the search bar is closed, start the mainActivity freshly (freshly: to avoid some annoying errors)
                ArrayList<String> nameList = new ArrayList<String>();
                ArrayList<String> codeList = new ArrayList<String>();
                int count = 0;

                DatabaseForSearch db2 = new DatabaseForSearch(getApplicationContext());
                Cursor cursor = db2.getCourseMatches("*", null, 0);

                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        do {
                            String nameString = cursor.getString(1);
                            String codeString = cursor.getString(0);
                            nameList.add(nameString);
                            codeList.add(codeString);
                            count++;
                        } while (cursor.moveToNext());
                    }
                }

                Intent intent = new Intent(MainActivity.this, MainActivity.class);

                intent.putStringArrayListExtra("NAME_LIST", nameList);
                intent.putStringArrayListExtra("NUMBER_LIST", codeList);
                intent.putExtra("COUNT", count);

                startActivity(intent);
                return true;
            }
        });

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Search course code");
        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextChange(String newText) {

                        MenuItem menuItem = menu.findItem(R.id.action_sort);

                        menuItem.setVisible(false);

                        doMySearch(newText);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextSubmit(String query) {

                        MenuItem menuItem = menu.findItem(R.id.action_sort);

                        menuItem.setVisible(false);

                        searchView.clearFocus();
//                        searchView.setQuery("", false);
//                        searchView.setIconified(false);
//                        searchItem.collapseActionView();

                        doMySearch(query);

                        return true;

                    }
                }
        );
        return true;
    }

    private void doMySearch(String query) {

        List<String> nameList = new ArrayList<String>();
        List<String> codeList = new ArrayList<String>();
        int count = 0;

        DatabaseForSearch databaseForSearch = new DatabaseForSearch(context);
        Cursor cursor = databaseForSearch.getCourseMatches(query, null/*column*/, 0);

        if (cursor == null) {
            if (query.length() == 0) tvError.setText(null);
            else tvError.setText("No result found!");
        } else {
            if (cursor.moveToFirst()) {
                tvError.setText(null);
                do {
                    String nameString = cursor.getString(1);
                    String codeString = cursor.getString(0);
                    nameList.add(nameString);
                    codeList.add(codeString);
                    count++;
                } while (cursor.moveToNext());
            }
        }


        SearchAndFilterAdapter searchAdapter = new SearchAndFilterAdapter();
        searchAdapter.setCount(count);
        searchAdapter.setCourseName(nameList);
        searchAdapter.setCourseCode(codeList);
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            spinner.setVisibility(View.GONE);

            return true;
        }
        if (id == R.id.action_sort_code) {
            chooseSpinnerTab(depStatus, 0);
        }
        if (id == R.id.action_sort_name) {
            chooseSpinnerTab(depStatus, 1);
            return true;
        }
        if (id == R.id.action_contact) {
            Intent intent = new Intent(context, ContactActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_about) {
            Intent intent = new Intent(context, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private static class MySpinnerAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MySpinnerAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Resources.Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Resources.Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }

    }

}
