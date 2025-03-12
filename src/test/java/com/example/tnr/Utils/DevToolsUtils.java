package com.example.tnr.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v133.network.Network;
import org.openqa.selenium.devtools.v133.network.model.Headers;
import org.openqa.selenium.devtools.v133.network.model.ResponseReceived;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DevToolsUtils {
    public static void NetworkCheckReceived (WebDriver driver){
        org.openqa.selenium.devtools.DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

        // Add listener for network responses
        devTools.addListener(Network.responseReceived(), response -> {
            ResponseReceived networkResponse = response;
            System.out.println("network responses");
           // System.out.println("Response URL: " + networkResponse.getResponse().getUrl());
            System.out.println("Status: " + networkResponse.getResponse().getStatus());
           // System.out.println("Headers: " + networkResponse.getResponse().getHeaders());
        });
    }
    public static void NetworkCheckSent (WebDriver driver){
        org.openqa.selenium.devtools.DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();

        // Enable network tracking
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(), request -> {
            System.out.println("request details");
           // System.out.println("Request URL: " + request.getRequest().getUrl());
           // System.out.println("Request headers: " + request.getRequest().getHeaders());
            System.out.println("Request Method: " + request.getRequest().getMethod());
        });
    }
    public static void HttpAuth (WebDriver driver){
        org.openqa.selenium.devtools.DevTools devTools = ((ChromeDriver)driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        String auth = "Basic " + Base64.getEncoder().encodeToString(
                ("storefront:Monceau69").getBytes());
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", auth);
        Headers extraHeaders = new Headers(headers);
        devTools.send(Network.setExtraHTTPHeaders(extraHeaders));
    }
    public static void HttpAuthEdge (WebDriver driver){
        org.openqa.selenium.devtools.DevTools devTools = ((EdgeDriver)driver).getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        String auth = "Basic " + Base64.getEncoder().encodeToString(
                ("storefront:Monceau69").getBytes());
        Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", auth);
        Headers extraHeaders = new Headers(headers);
        devTools.send(Network.setExtraHTTPHeaders(extraHeaders));
    }
}
