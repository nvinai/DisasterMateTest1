package com.example.calorietracker_v02;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DietScreen extends Fragment implements AdapterView.OnItemSelectedListener {

    View vDietScreen;
    Spinner categorySpinner;
    Spinner foodSpinner;
    TextView tvFood;
    TextView foodResult;
    TextView tvCalories;
    TextView tvFat;
    ImageView searchedImage;
    Button addButton;
    EditText enterUnits;
    TextView foodNotFound;
    EditText enterNewFood;
    Button btnFoodAndPost;
    TextView tvFoodDescription;

    TextView nalFat;
    TextView nalCalories;


    ArrayList<String> categoryList = new ArrayList<>();
    ArrayList<String> foodList = new ArrayList<>();

    int id;
    String categoryName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vDietScreen = inflater.inflate(R.layout.my_daily_diet, container, false);


        categorySpinner = (Spinner) vDietScreen.findViewById(R.id.categorySpinner);
        foodSpinner = (Spinner) vDietScreen.findViewById(R.id.foodSpinner);
        tvFood = (TextView) vDietScreen.findViewById(R.id.tvfood);
        tvCalories = (TextView) vDietScreen.findViewById(R.id.calorieResult);
        tvFat = (TextView) vDietScreen.findViewById(R.id.fatResult);
//        searchedImage = (ImageView) vDietScreen.findViewById(R.id.imgFoodImage);
        addButton = (Button) vDietScreen.findViewById(R.id.buttonAdd);
        enterUnits = (EditText) vDietScreen.findViewById(R.id.enterUnits);
        foodNotFound = vDietScreen.findViewById(R.id.tv_FoodNotFound);
        foodNotFound.setVisibility(View.GONE);
        enterNewFood = vDietScreen.findViewById(R.id.et_enterNewFood);
        enterNewFood.setVisibility(View.GONE);
        btnFoodAndPost = vDietScreen.findViewById(R.id.btnPostFoodandConsumption);
        btnFoodAndPost.setVisibility(View.GONE);
        tvFoodDescription = vDietScreen.findViewById(R.id.tvFoodDescription);
        tvFoodDescription.setVisibility(View.GONE);

        nalFat = vDietScreen.findViewById(R.id.tv_NalFat);
        nalCalories = vDietScreen.findViewById(R.id.tv_NalCalories);


        enterUnits.setVisibility(View.GONE);
        addButton.setVisibility(View.GONE);


        final GetCategories getCategories = new GetCategories();
        getCategories.execute();

        //To bridge the UI and the Data Source
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(vDietScreen.getContext(), android.R.layout.simple_spinner_item, categoryList);

        categorySpinner.setAdapter(categoryAdapter);


        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryName = categoryList.get(position);
                //TODO Call the rest method to get all the items from the given string.
                //get itemsFromCategory()
                //itemsFromCategory.execute(categoryName)


                GetFoodFromCategories getFoodFromCategories = new GetFoodFromCategories();
                getFoodFromCategories.execute(categoryName);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        foodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO call Rest to display food name, calories and fat
                //TODO call google API to display food image and description
                GetDescriptionFromFood getDescriptionFromFood = new GetDescriptionFromFood();
                String foodName = foodSpinner.getItemAtPosition(position).toString();
                getDescriptionFromFood.execute(foodName);

                enterUnits.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.VISIBLE);

                //focuses cursor to user and asks him to enter values
                enterUnits.requestFocus();

                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GetFoodIdTask getFoodIdTask = new GetFoodIdTask();
                        getFoodIdTask.execute();
                    }
                });

            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //give a delay of 5 seconds before asking the user about the food search
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // yourMethod();
                foodNotFound.setVisibility(View.VISIBLE);
                enterNewFood.setVisibility(View.VISIBLE);
                btnFoodAndPost.setVisibility(View.VISIBLE);
                tvFoodDescription.setVisibility(View.VISIBLE);

            }
        }, 5000);


        btnFoodAndPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = enterNewFood.getText().toString();
                SearchAsyncTask searchAsyncTask = new SearchAsyncTask();
                searchAsyncTask.execute(keyword);
//                searchAsyncImageTask.execute(keyword);
                DownloadImageFromInternet blah = new DownloadImageFromInternet((ImageView) vDietScreen.findViewById(R.id.imgFoodImage));
                blah.execute(keyword);
                SearchAsyncFood searchAsyncFood = new SearchAsyncFood();
                searchAsyncFood.execute(keyword);

            }
        });


        return vDietScreen;
    }


    private class SearchAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return SearchGoogleAPI.search(params[0], new String[]{"num"}, new String[]{"1"});
        }


        @Override
        protected void onPostExecute(String result) {
//            TextView tv = (TextView) vDietScreen.findViewById(R.id.tvFoodDescription);

            tvFoodDescription.setText(SearchGoogleAPI.getSnippet(result));
        }
    }


//    private class SearchAsyncImageTask extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//            return SearchGoogleAPI.search(params[0], new String[]{"num", "searchType"}, new String[]{"1", "image"});
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            TextView tv = (TextView) findViewById(R.id.tvResultImgURL);
//            tv.setText(SearchGoogleAPI.getImageLink(result));
//        }
//    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView = imageView;
            Toast.makeText(vDietScreen.getContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
        }

        protected Bitmap doInBackground(String... urls) {
            String result = SearchGoogleAPI.search(urls[0], new String[]{"num", "searchType"}, new String[]{"1", "image"});
            String imageURL = SearchGoogleAPI.getImageLink(result);
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String categoryItem = parent.getItemAtPosition(position).toString();
        TextView textView = (TextView) vDietScreen.findViewById(R.id.selectedCategory);
        String tv_text = textView.getText().toString();

        //shows user which item is selected
        Toast.makeText(vDietScreen.getContext(), tv_text + " " + categoryItem, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*END OF SPINNER*/

    /*ASYNC FN TO RETRIEVE AND DISPLAY CATEGORIES*/
    private class GetCategories extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            try {
                //should return "[cat1,cat2,cat3]"
                return DietRESTClient.getAllCategories();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            List<String> categorylist = DietRESTClient.getSnippet(result);
            categoryList = (ArrayList<String>) categorylist;

            //To bridge the UI and the Data Source
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(vDietScreen.getContext(), android.R.layout.simple_spinner_item, categoryList);

            categorySpinner.setAdapter(categoryAdapter);

        }
    }

    /*ASYNC FN TO RETRIEVE AND Food from CATEGORIES*/
    private class GetFoodFromCategories extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return DietRESTClient.getFoodFromCategories(params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            List<String> foodFromCategorylist = DietRESTClient.getFoodNameSnippet(result);
            foodList = (ArrayList<String>) foodFromCategorylist;

            //To bridge the UI and the Data Source
            ArrayAdapter<String> foodAdapter = new ArrayAdapter<String>(vDietScreen.getContext(), android.R.layout.simple_spinner_item, foodList);

            foodSpinner.setAdapter(foodAdapter);

        }
    }


    private class GetDescriptionFromFood extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {

                //TODO define REST methods to get description of food
                return DietRESTClient.getDescriptionFromFood(params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            List<String> descriptionFromFood = DietRESTClient.getDescriptionFromFoodSnippet(result);
//            tvFood.setText("Name:" + descriptionFromFood.get(0));
            Toast.makeText(vDietScreen.getContext(), descriptionFromFood.get(0) + " selected", Toast.LENGTH_SHORT).show();
            tvCalories.setText("Calories:" + descriptionFromFood.get(1));
            tvFat.setText("Fat Content:" + descriptionFromFood.get(2));

        }
    }


    private class SearchAsyncFood extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            return NALFoodReportAPI.search(params[0], new String[]{"type"}, new String[]{"b"});
        }

        @Override
        protected void onPostExecute(String result) {
            String[] results = NALFoodReportAPI.getCaloriesAndFat(result).split(" ");
            nalCalories.setText(results[0] + "kcal");
            nalFat.setText(results[2] + "g");
        }
    }

    private class GetFoodIdTask extends AsyncTask<Void, Void, Integer> {
        @Override
        protected Integer doInBackground(Void... params) {

            return DietRESTClient.generateFoodId();
        }

        @Override
        protected void onPostExecute(Integer foodid) {
            id = foodid;
            if (id < 0) {
                Toast.makeText(vDietScreen.getContext(), "Something went wrong, are you offline?", Toast.LENGTH_LONG).show();
            }
            PostFood postFood = new PostFood();
            postFood.execute();
            Toast.makeText(vDietScreen.getContext(), "Adding food", Toast.LENGTH_LONG).show();
        }
    }


    private class PostFood extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
//            Food food = new Food(id,enterNewFood.getText().toString(),"1",categoryName,enterUnits.getText(),tvCalories.getText(),tvFat.getText());
            //TODO  must create the actual post, creating a dummy value to check functioning of post method, worked the first time, unable to post the second time.
            Food food = new Food(id, "Cocoroach", "2", "Fruits", BigDecimal.valueOf(2), Double.valueOf(2), BigDecimal.valueOf(4));
            Consumption consumption = new Consumption();
            consumption.setCid(5);

            DietRESTClient.PostFood(food);
            return null;
        }

        @Override
        protected void onPostExecute(Void param) {
            Toast.makeText(vDietScreen.getContext(), "Added new food" + enterNewFood.getText().toString() + "to database", Toast.LENGTH_LONG).show();

        }


    }
}



