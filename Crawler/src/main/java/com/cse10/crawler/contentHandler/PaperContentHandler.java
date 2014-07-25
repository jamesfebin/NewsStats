package com.cse10.crawler.contentHandler;

import com.cse10.article.Article;
import com.cse10.filter.KeywordsFilter;
import edu.uci.ics.crawler4j.crawler.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TharinduWijewardane on 10.07.2014.
 */
public abstract class PaperContentHandler {

    protected List<Article> articles;

    public PaperContentHandler() {
        articles = new ArrayList<Article>();
    }

    /**
     * to be overridden
     */
    public abstract List extractArticles(Page page);

    boolean filterArticles(String content){
        return KeywordsFilter.filterContent(content);
    }

}
