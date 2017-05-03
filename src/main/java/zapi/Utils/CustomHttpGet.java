package zapi.Utils;

/**
 * Created by Chaitanya on 11-Jun-15.
 */
public class CustomHttpGet {
    String url;
    public static final String authHeader = "Authorization";
    public static final String contentType = "Content-Type";
    public static final String contentTypeJson = "application/json";

    private CustomHttpGet() {
    }

    public CustomHttpGet newBuilder() {
        return new CustomHttpGet();
    }


    public static final  class Builder{
        public static final String nestedAuthHeader = "Authorization";
        public static final String nestedContentType = "Content-Type";
        public static final String nestedContentTypeJson = "application/json";

    }


}
