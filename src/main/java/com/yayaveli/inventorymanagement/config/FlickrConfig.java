package com.yayaveli.inventorymanagement.config;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.RequestContext;
import com.flickr4java.flickr.auth.Auth;
import com.flickr4java.flickr.auth.Permission;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlickrConfig {

    @Value("${flickr.apiKey}")
    private String apiKey;

    @Value("${flickr.apiSecret}")
    private String apiSecret;

    @Value("${flickr.appKey}")
    private String appKey;

    @Value("${flickr.appSecret}")
    private String appSecret;

    /*
     * @Bean
     * public Flickr getFlickr() throws IOException, InterruptedException,
     * ExecutionException, FlickrException {
     * Flickr flickr = new Flickr(apiKey, apiSecret, new REST());
     * 
     * OAuth10aService service = new ServiceBuilder(apiKey)
     * .apiSecret(apiSecret)
     * .build(FlickrApi.instance(FlickrPerm.DELETE));
     * final Scanner scanner = new Scanner(System.in);
     * 
     * final OAuth1RequestToken requestToken = service.getRequestToken();
     * 
     * final String authUrl = service.getAuthorizationUrl(requestToken);
     * 
     * System.out.println(authUrl);
     * System.out.println("Paste it here");
     * 
     * final String oauthVerifier = scanner.nextLine();
     * 
     * OAuth1AccessToken accessToken = service.getAccessToken(requestToken,
     * oauthVerifier);
     * 
     * System.out.println(accessToken.getToken());
     * System.out.println(accessToken.getTokenSecret());
     * 
     * Auth auth = flickr.getAuthInterface().checkToken(accessToken);
     * 
     * System.out.println("--------------------");
     * System.out.println(auth.getToken());
     * System.out.println(auth.getTokenSecret());
     * 
     * return flickr;
     * }
     */

    @Bean
    public Flickr getFlickr() {
        Flickr flickr = new Flickr(apiKey, apiSecret, new REST());

        Auth auth = new Auth();

        auth.setPermission(Permission.DELETE);

        auth.setToken(appKey);
        auth.setTokenSecret(appSecret);

        RequestContext requestContext = RequestContext.getRequestContext();

        flickr.setAuth(auth);

        return flickr;
    }

}
