package com.example.elhanen;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static final String TAG = "Utils";

    public static ArrayList<Song> getSongs(Context context) {
        ArrayList<Song> songs = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("songs_metadata.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                String title = obj.getString("title");
                String filename = obj.getString("filename");
                String content = readWordFile(context, filename);
                Log.d(TAG, "Title: " + title + ", Content: " + content);
                songs.add(new Song(title, filename, content));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    private static String readWordFile(Context context, String filename) {
        StringBuilder content = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(filename);
            if (filename.endsWith(".docx")) {
                XWPFDocument document = new XWPFDocument(is);
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                for (XWPFParagraph paragraph : paragraphs) {
                    content.append(paragraph.getText()).append("\n");
                }
            } else if (filename.endsWith(".doc")) {
                HWPFDocument document = new HWPFDocument(is);
                WordExtractor extractor = new WordExtractor(document);
                String[] paragraphs = extractor.getParagraphText();
                for (String paragraph : paragraphs) {
                    content.append(paragraph).append("\n");
                }
            } else {
                Log.e(TAG, "Unsupported file format: " + filename);
            }
            is.close();
        } catch (OLE2NotOfficeXmlFileException e) {
            Log.e(TAG, "The file " + filename + " is not a DOCX file.", e);
        } catch (Exception e) {
            Log.e(TAG, "Error reading Word file: " + filename, e);
        }
        return content.toString();
    }
}




class Song {
    String title;
    String filename;
    String content;

    public Song(String title, String filename, String content) {
        this.title = title;
        this.filename = filename;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getFilename() {
        return filename;
    }

    public String getContent() {
        return content;
    }
}
