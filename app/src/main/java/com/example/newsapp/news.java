package com.example.newsapp;

public  class news {

    private String title;
    private String author;
    private String url;
    private String urlToImage;

    public news(String title, String author, String url, String urlToImage) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }



    public String getAuthor() {
        return author;
    }


    public String getUrl() {
        return url;
    }


    public String getUrlToImage() {
        return urlToImage;
    }

}

