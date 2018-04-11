/*
 * Copyright (c) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidtv.webviewdriver.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.androidtv.webviewdriver.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int FETCH_JOB_REQUEST = 1;
        final String URL_STRING = "http://www.formula1.com/";

        Intent fetchVideoURLIntent = new Intent(this,FetchVideoURLActivity.class);
        fetchVideoURLIntent.putExtra("urlString", URL_STRING);
        startActivityForResult(fetchVideoURLIntent, FETCH_JOB_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            Log.d("activity result", data.getData().toString());
        } else {
            Log.d("activity result", "Failed");
        }
    }

}
