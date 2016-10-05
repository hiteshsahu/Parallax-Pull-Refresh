package com.hiteshsahu.parallaxpullrefreshconcept.data;

import java.util.ArrayList;

/**
 * Created by Hitesh on 05-10-2016.
 */
public class CenterRepository {

    ArrayList<TestDataModel> testData = new ArrayList<>();
    private static CenterRepository ourInstance = new CenterRepository();

    public static CenterRepository getInstance() {
        return ourInstance;
    }

    private CenterRepository() {
    }

    public TestDataModel getItemAt(int position) {
        return testData.get(position);
    }

    public ArrayList<TestDataModel> getListOfItems() {
        return testData;
    }
}
