package ru.yandex.yamblz;

/**
 * Created by dan on 10.08.16.
 */
import android.util.Log;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import ru.yandex.yamblz.Model.Artist;
import ru.yandex.yamblz.Model.Cover;

/**
 * Created by dan on 27.04.16.
 */
public class MyJsonParser {


    public MyJsonParser() {

    }

    public List<Artist> parse() throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException {
        List<Artist> list = new ArrayList<>();
        String text = loadJsonString("https://download.cdn.yandex.net/mobilization-2016/artists.json");
        list.addAll(getAllArtists(text));
        return list;
    }



    public static String loadJsonString(String url) throws IOException {
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(new URL(url).openStream(), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = "";
        StringBuilder concatination = new StringBuilder();
        while ((s = input.readLine()) != null)
            concatination.append(s);
        return concatination.toString();
    }

    public static List<Artist> getAllArtists(String text) throws ParseException, org.json.simple.parser.ParseException {
        List<Artist> listOfValues = new ArrayList<Artist>();
        JSONArray array = (JSONArray) new JSONParser().parse(text);
        for (int i = 0; i < array.size(); i++) {
            JSONObject obj = (JSONObject) array.get(i);
            Artist artist = new Artist();
            artist.setId((Long) obj.get("id"));
            artist.setName((String) obj.get("name"));
            artist.setTracks((Long) obj.get("tracks"));
            artist.setAlbums((Long) obj.get("albums"));
            artist.setLink((String) obj.get("link"));
            artist.setDescription((String) obj.get("description"));

            JSONArray genres = (JSONArray) obj.get("genres");
            for (int j = 0; j < genres.size(); j++) {
                artist.addGenre((String)genres.get(j));
            }

            JSONObject cover = (JSONObject) obj.get("cover");
            Cover cv = new Cover();
            cv.setSmallCoverImage((String) cover.get("small"));
            cv.setBigCoverImage((String) cover.get("big"));
            artist.setCover(cv);
            listOfValues.add(artist);
            Log.i("ppc", String.valueOf(i));
        }
        return listOfValues;

    }
}
