package com.josea.mymangalist.utils;

import com.josea.mymangalist.model.DetailsManga;
import com.josea.mymangalist.model.TopManga;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class JikanService {
        static String BaseUrl = "https://api.jikan.moe/v3";
        static Integer sucessResp[] = {200,201,202,203,205};
        static OkHttpClient client;
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        public static final String ISO_8601_24H_FULL_FORMAT = "yyyy-MM-dd";

        public JikanService() {
            this.client = new OkHttpClient();
        }

        public ArrayList<TopManga> getTopMangaList() {
            ArrayList<TopManga> topMangaList = new ArrayList<TopManga>();
            String url = BaseUrl + "/top/manga/1/manga";
//            RequestBody body = RequestBody.create(payload, JSON);
            Request request = new Request.Builder().url(url).build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                topMangaList = this.processJsonToMangaList(response.body().string());
            } catch (Exception e){
                return null;
            }
            return topMangaList;
        }

        public DetailsManga getDetailsManga(int id, String title) {
            DetailsManga detailsManga;
            String url = BaseUrl + "/search/manga?q=" + title + "&page=1";
//            RequestBody body = RequestBody.create(payload, JSON);
            Request request = new Request.Builder().url(url).build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                detailsManga = this.processJsonToMangaDetails(response.body().string(), id);
            } catch (Exception e){
                return null;
            }

            return detailsManga;
        }

        public ArrayList<TopManga> processJsonToMangaList(String response){
            ArrayList<TopManga> topMangaList = new ArrayList<TopManga>();
            TopManga newTopManga;
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("top");
                for (int i = 0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    newTopManga = new TopManga(object.getInt("mal_id"),
                            object.isNull("rank") ? 0 : object.getInt("rank"),
                            object.isNull("score") ? 0.0f : BigDecimal.valueOf(object.getDouble("score")).floatValue(),
                            object.isNull("title") ? "No title" : object.getString("title"),
                            object.isNull("image_url") ? "No image" : object.getString("image_url"),
                            object.isNull("start_date") ? "No date" : object.getString("start_date"),
                            object.isNull("end_date") ? "No date" : object.getString("end_date"),
                            object.isNull("volumes") ? 0 : object.getInt("volumes")
                    );

                    topMangaList.add(newTopManga);
                }
            }catch (Exception e){
                return null;
            }
            return topMangaList;
        }

        public DetailsManga processJsonToMangaDetails(String response, int id){
            DetailsManga detailsManga;
            String regex = "T.*";
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                JSONObject object = new JSONObject();
                for (int i = 0;i<jsonArray.length();i++){
                    object = jsonArray.getJSONObject(i);
                    if (object.getInt("mal_id") == id ){
                        break;
                    }

                }
//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss+ss:SSS");
                final SimpleDateFormat formatter = new SimpleDateFormat(ISO_8601_24H_FULL_FORMAT);

                String start_date_str = object.getString("start_date");
                String end_date_str = object.getString("end_date");

                Date start_date = formatter.parse(start_date_str.replaceAll(regex, ""));
                Date end_date = object.isNull("end_date") ? null :formatter.parse(end_date_str.replaceAll(regex, ""));

                detailsManga = new DetailsManga(object.getInt("mal_id"),
                        object.isNull("image_url") ? "https://fistiktekstil.com/blog/wp-content/uploads/2019/12/img-300x200.jpg" : object.getString("image_url"),
                        object.isNull("title") ? "Title not found" : object.getString("title"),
                        !object.isNull("publishing") && object.getBoolean("publishing"),
                        object.isNull("synopsis") ? "Synopsis not found" : object.getString("synopsis"),
                        object.isNull("chapters") ? 0 : object.getInt("chapters"),
                        object.isNull("volumes") ? 0 : object.getInt("volumes"),
                        object.isNull("score") ? 0.0f : BigDecimal.valueOf(object.getDouble("score")).floatValue(),
                        start_date,
                        end_date
                        );

            }catch (Exception e){
                return null;
            }
            return detailsManga;
        }
}
