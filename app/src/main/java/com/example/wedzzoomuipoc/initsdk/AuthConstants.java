package com.example.wedzzoomuipoc.initsdk;

public interface AuthConstants {
    /**
     *    ========== Disclaimer ==========
     *
     *    Please be aware that all hard-coded variables and constants
     *    shown in the documentation and in the demo, such as Zoom Token,
     *    Zoom Access, Token, etc., are ONLY FOR DEMO AND TESTING PURPOSES.
     *    We STRONGLY DISCOURAGE the way of HARDCODING any Zoom Credentials
     *    (username, password, API Keys & secrets, SDK keys & secrets, etc.)
     *    or any Personal Identifiable Information (PII) inside your application.
     *    WE DON’T MAKE ANY COMMITMENTS ABOUT ANY LOSS CAUSED BY HARD-CODING CREDENTIALS
     *    OR SENSITIVE INFORMATION INSIDE YOUR APP WHEN DEVELOPING WITH OUR SDK.
     *
     */
    // TODO Change it to your web domain
    public final static String WEB_DOMAIN = "zoom.us";

    // TODO Change it to your APP Key
    public final static String APP_KEY = "UZ2ebpOw4UNKklkCQSYmaNDs95prYfOBcBky";//"WJXy9ECSRFSPJjpiJ83ksQ";

    // TODO Change it to your APP Secret
    public final static String APP_SECRET = "tn0G3pvYMb6cUBEtkr4eJtCVuacwv1sjL8pB";//"Co4NHqRYQP8gQJ5e0KeaAMaLHwM1ZEfcTynn";

    /**
     *    ========== Disclaimer ==========
     *
     *    Please be aware that all hard-coded variables and constants
     *    shown in the documentation and in the demo, such as Zoom Token,
     *    Zoom Access, Token, etc., are ONLY FOR DEMO AND TESTING PURPOSES.
     *    We STRONGLY DISCOURAGE the way of HARDCODING any Zoom Credentials
     *    (username, password, API Keys & secrets, SDK keys & secrets, etc.)
     *    or any Personal Identifiable Information (PII) inside your application.
     *    WE DON’T MAKE ANY COMMITMENTS ABOUT ANY LOSS CAUSED BY HARD-CODING CREDENTIALS
     *    OR SENSITIVE INFORMATION INSIDE YOUR APP WHEN DEVELOPING WITH OUR SDK.
     *
     */
    // TODO Change it to your web API Key
    public final static String API_KEY = "afGWHJAdRmCem0J7WUngHA";

    // TODO Change it to your web API Secret
    public final static String API_SECRET = "7KqIt1fglzV716pAaTgw5QJIdp3twP74schI";

    // TODO change it to your user ID, do not need for login user
    // public final static String USER_ID = "Your user ID from REST API:https://api.zoom.us/v2/users/{userId} / usually your Zoom username(email)";
    public final static String USER_ID = "miitbh1@gmail.com";

    // TODO change it to your Zoom access token expired time
    public final static long EXPIRED_TIME= 3600 * 24 * 7; //A week
    /**
     * We recommend that, you can generate jwttoken on your own server instead of hardcore in the code.
     * We hardcore it here, just to run the demo.
     *
     * You can generate a jwttoken on the https://jwt.io/
     * with this payload:
     * {
     *     "appKey": "string", // app key
     *     "iat": long, // access token issue timestamp
     *     "exp": long, // access token expire time
     *     "tokenExp": long // token expire time
     * }
     */
    public final static String SDK_JWTTOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOm51bGwsImlzcyI6IldKWHk5RUNTUkZTUEpqcGlKODNrc1EiLCJleHAiOjE1OTkxMTIxNzMsImlhdCI6MTU5ODUwNzM3NX0.60wk32udDfAPcQEl3uJ_OC9sCT33WlVKFznFVHV4JD8";

}
