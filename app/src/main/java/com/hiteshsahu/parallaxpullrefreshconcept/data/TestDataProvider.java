package com.hiteshsahu.parallaxpullrefreshconcept.data;

import java.util.ArrayList;

/**
 * Created by Hitesh on 05-10-2016.
 */
public class TestDataProvider {

    /**
     * List of image URls to fetch from internet in recycler view
     * so that apk size can be reduced
     */
    public static final String[] testImageUrls = {
            //Add Sky
            "http://wallpapercave.com/wp/0e12dgE.png",
            "http://wallpapercave.com/wp/3I32MKI.jpg",
            "http://wallpapercave.com/wp/251fACI.jpg",
            "http://wallpapercave.com/wp/kKsNXp9.jpg",
            "http://wallpapercave.com/wp/jGNTLTN.jpg",
            "http://wallpapercave.com/wp/HfzLYZY.jpg",
            "http://wallpapercave.com/wp/2l8fNqv.jpg",
            "http://wallpapercave.com/wp/gF9n5Lk.jpg",
            "http://wallpapercave.com/wp/cRHOcKE.jpg",
            "http://wallpapercave.com/wp/KBk1YBX.jpg",
            //Tree
            "http://img03.deviantart.net/7245/i/2013/134/3/c/tree_2__png_with_transparency__by_bupaje-d65amxg.png",
            "http://img04.deviantart.net/95e3/i/2013/136/e/a/tree_3__png_with_transparency__by_bupaje-d65gvf3.png",
            "http://img07.deviantart.net/38e2/i/2013/135/b/2/tree_1__png_with_transparency__by_bupaje-d65ctod.png",
            //Hills
            "http://www.pngall.com/wp-content/uploads/2016/06/Mountain-PNG-Picture.png",
            "http://www.pngall.com/wp-content/uploads/2016/06/Mountain-PNG-File.png",
            "http://david-freed.com/wp-content/uploads/2014/04/Slider-Mountain-1440-wide-1.png",
            "http://pre02.deviantart.net/0627/th/pre/f/2013/218/c/8/mountain_landscape_by_regus_ttef-d602k3h.png",
            "http://www.pngall.com/wp-content/uploads/2016/06/Mountain-PNG-Image-180x180.png"
    };
    /**
     * Singleton
     */
    private static TestDataProvider ourInstance = new TestDataProvider();
    /**
     * List of test data models for recyclerview
     */
    ArrayList<TestDataModel> listOfTestData = new ArrayList<>();

    private TestDataProvider() {
    }

    public static TestDataProvider getInstance() {
        return ourInstance;
    }

    /**
     * get Testdata at postion
     *
     * @param position
     * @return
     */
    public TestDataModel getItemAt(int position) {
        return listOfTestData.get(position);
    }

    /**
     * get all test data
     *
     * @return
     */
    public ArrayList<TestDataModel> getListOfItems() {
        return listOfTestData;
    }

    /**
     * generate test data for recyclerview
     */
    public void generateTestData() {

        TestDataProvider.getInstance().getListOfItems().clear();

        for (int i = 0; i < testImageUrls.length; i++) {

            if (i <= 9) {
                TestDataProvider.getInstance().getListOfItems().add(new TestDataModel("Awesome Sky #" + i,
                        "¯\\_(ツ)_/¯" + i, testImageUrls[i]));
            } else if (i > 9 && i <= 13) {
                TestDataProvider.getInstance().getListOfItems().add(new TestDataModel("Tree # " + i,
                        "(◕‿◕✿)" + i, testImageUrls[i]));
            } else {
                TestDataProvider.getInstance().getListOfItems().add(new TestDataModel("Beautiful Mountain # " + i,
                        "(▀̿Ĺ̯▀̿ ̿)" + i, testImageUrls[i]));
            }
        }
    }

    /**
     * Class to hold our test data
     */
    public class TestDataModel {

        private String heading;
        private String detail;
        private String imageUrl;

        public TestDataModel(String title, String artist, String path) {
            this.heading = title;
            this.detail = artist;
            this.imageUrl = path;
        }

        public String getHeading() {
            return heading;
        }

        public String getDetail() {
            return detail;
        }

        public String getImageUrl() {
            return imageUrl;
        }
    }

}
