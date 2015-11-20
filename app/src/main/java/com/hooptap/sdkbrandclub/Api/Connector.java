package com.hooptap.sdkbrandclub.Api;

import com.amazonaws.Request;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.Signer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 13/11/15.
 */
public class Connector {
    private static final Pattern ENDPOINT_PATTERN =Pattern.compile("^https?://\\w+.execute-api.([a-z0-9-]+).amazonaws.com/.*"); ;

    Signer signer=getSigner(getRegion("https://25unt9h64h.execute-api.us-west-2.amazonaws.com/dev"));
    Signer getSigner(String region) {
        AWS4Signer signer = new AWS4Signer();
        signer.setServiceName("execute-api");
        signer.setRegionName(region);
        return signer;
    }
    String getRegion(String endpoint) {

            Matcher m = ENDPOINT_PATTERN.matcher(endpoint);
            if(m.matches()) {
                return m.group(1);
            } else {
                throw new IllegalArgumentException("Region isn\'t specified and can\'t be deduced from endpoint.");
            }

    }

}
