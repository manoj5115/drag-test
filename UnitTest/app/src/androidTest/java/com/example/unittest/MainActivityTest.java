package com.example.unittest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import org.junit.Test;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mainActivity;
    private TextView textView;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        textView = (TextView) mainActivity.findViewById(R.id.txtView);
    }

    @Test
    public void testMainActivityTextView_labelText() {
        final String expected =
                mainActivity.getString(R.string.hello_string);
        final String actual = textView.getText().toString();
        assertEquals(expected, actual);
    }
}