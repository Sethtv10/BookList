package com.example.booklist.booklist;


import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Book  implements Serializable {
    private String openLibraryId;
    private String author;
    private String title;
    private String id;

    public String getOpenLibraryId() {
        return openLibraryId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    // Get medium sized book cover from covers API
    public String getCoverUrl() {
        return "https://image.tmdb.org/t/p/w500/" + openLibraryId;
    }

    // Get large sized book cover from covers API
    public String getLargeCoverUrl() {
        return "https://image.tmdb.org/t/p/w500/" + openLibraryId ;
    }
    // Returns a Book given the expected JSON

    public static Book fromJson(JSONObject jsonObject) {
        Book book = new Book();
        try {
            book.id = jsonObject.getString("id");
            // Deserialize json into object fields
            // Check if a cover edition is available
            if (jsonObject.has("poster_path"))  {
                book.openLibraryId = jsonObject.getString("poster_path");
            } else if(jsonObject.has("backdrop_path")) {
                final JSONArray ids = jsonObject.getJSONArray("backdrop_path");
                book.openLibraryId = ids.getString(0);
            }
            book.title = jsonObject.has("original_title") ? jsonObject.getString("original_title") : "";
            book.author = getAuthor(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return book;
    }

    // Return comma separated author list when there is more than one author
    private static String getAuthor(final JSONObject jsonObject) {
        try {
            final JSONArray authors = jsonObject.getJSONArray("release_date");
            int numAuthors = authors.length();
            final String[] authorStrings = new String[numAuthors];
            for (int i = 0; i < numAuthors; ++i) {
                authorStrings[i] = authors.getString(i);
            }
            return TextUtils.join(", ", authorStrings);
        } catch (JSONException e) {
            return "";
        }
    }
    public static ArrayList<Book> fromJson(JSONArray jsonArray) {
        ArrayList<Book> books = new ArrayList<Book>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject bookJson = null;
            try {
                bookJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Book book = Book.fromJson(bookJson);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }
}
