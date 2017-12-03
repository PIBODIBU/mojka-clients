package com.mojka.poisk.data.model;

import com.google.gson.annotations.SerializedName;
import com.mojka.poisk.data.api.APIGenerator;

public class Image {
    @SerializedName("url")
    private String url;

    public Image(String url) {
        this.url = url;
    }

    public String getUrl() {
        if (url != null && url != "")
            if (!url.startsWith("http://") && !url.startsWith("https://"))
                url = APIGenerator.API_HOST.concat(url);

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}