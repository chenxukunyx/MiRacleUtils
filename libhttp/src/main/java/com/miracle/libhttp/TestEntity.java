package com.miracle.libhttp;

import java.util.List;

/**
 * Create with Android studio
 *
 * @fuction:
 * @author: chenxukun
 * @date: 2017-09-21
 * @time: 16:39
 * @age: 24
 */
public class TestEntity {

    /**
     * error : false
     * results : [{"_id":"597e016c421aa90ca209c523","createdAt":"2017-07-30T23:55:24.154Z","desc":"Android终端","publishedAt":"2017-09-21T13:27:15.675Z","source":"chrome","type":"Android","url":"https://github.com/termux/termux-app","used":true,"who":"Jason"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 597e016c421aa90ca209c523
         * createdAt : 2017-07-30T23:55:24.154Z
         * desc : Android终端
         * publishedAt : 2017-09-21T13:27:15.675Z
         * source : chrome
         * type : Android
         * url : https://github.com/termux/termux-app
         * used : true
         * who : Jason
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }
    }
}
