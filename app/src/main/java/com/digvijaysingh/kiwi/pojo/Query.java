
package com.digvijaysingh.kiwi.pojo;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private List<Redirect> redirects = new ArrayList<>();
    private List<Page> pages = new ArrayList<>();

    public List<Redirect> getRedirects() {
        return redirects;
    }

    public void setRedirects(List<Redirect> redirects) {
        this.redirects = redirects;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

}
