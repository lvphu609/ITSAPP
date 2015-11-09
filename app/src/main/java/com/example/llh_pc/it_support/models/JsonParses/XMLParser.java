package com.example.llh_pc.it_support.models.JsonParses;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ZBOOK 15 on 11/5/2015.
 */
public class XMLParser {
    // names of the XML tags
    protected static final String MARKERS = "markers";
    protected static final String MARKER = "marker";

    protected URL feedUrl;

    protected XMLParser(final String feedUrl) {
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            // Log.e(e.getMessage(), "XML parser - " + feedUrl);
        }
    }

    protected InputStream getInputStream() {
        try {
            return feedUrl.openConnection().getInputStream();
        } catch (IOException e) {
            // Log.e(e.getMessage(), "XML parser - " + feedUrl);
            return null;
        }
    }
}