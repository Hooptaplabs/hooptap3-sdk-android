package com.hooptap.sdkbrandclub.Api;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.format.Time;
import android.util.Log;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.Signer;
import com.amazonaws.mobileconnectors.apigateway.ApiClientFactory;
import com.amazonaws.regions.Region;
import com.amazonaws.services.apigateway.AmazonApiGateway;
import com.amazonaws.services.apigateway.model.*;
import com.hooptap.a.RequestInterceptor;
import com.hooptap.a.RestAdapter;
import com.hooptap.a.client.OkClient;
import com.hooptap.b.OkHttpClient;
import com.hooptap.sdkbrandclub.AWS.MobileAPIClient;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import static com.amazonaws.util.BinaryUtils.toHex;

/**
 * Clase principal que crea e inicializa el objeto Hooptap, el cual será el encargado de realizar las peticiones a nuestras APIs.
 *
 * El objeto Hooptap necesita tener definido un api_key y un token para poder operar correctamente
 *
 * @author Hooptap Team
 */

public class HooptapAWS {
    private static ApiInterfaceAWS sClientService;
    private static RestAdapter restAdapter;
    private static OkHttpClient client;
    private static SharedPreferences settings;
    private static RestAdapter.LogLevel debugVariable;
    private static ApiInterfaceAWS hoptapClient;
    private static Context context;
    private static SharedPreferences.Editor editor;

    private HooptapAWS(ApiInterfaceAWS sClientService) {
        this.hoptapClient = sClientService;
    }

    /**
     *
     * @return El objeto Hooptap para poder utilizarlo posteriormente
     */
    public static ApiInterfaceAWS getClient(){
        return hoptapClient;
    }

    /**
     * Metodo que asigna un token al objeto Hooptap para permitir el acceso a la API
     * @param token necesario para poder realizar las peticiones e identificar al usuario
     */
    public static void setToken(String token){
        editor.putString("ht_token", token);
        editor.apply();
    }

    public static void setApiKey(String apiKey) {
        editor.putString("ht_api_key", apiKey);
        editor.apply();
    }


    /**
     * Constructor generico en el cual podremos configurar ciertos parametros
     */
    public static class Builder {

        public static Boolean htEnableDebug;
        private String CanonicalRequest;
        private String HTTPRequestMethod="POST";
        private String CanonicalURI="/";
        private String CanonicalQueryString="";
        private String CanonicalHeaders;
        private String SignedHeaders;
        private String RequestPayload;

        public Builder(Context cont) {
            context = cont;
            settings = context.getSharedPreferences("preferences", 0);
            editor = settings.edit();
        }

        /**
         * Metodo para activar o desacticar el modo debug de nuestro objeto Hooptap
         * @param enable
         */
        public HooptapAWS.Builder enableDebug(Boolean enable){
            htEnableDebug = enable;
            return this;
        }

        /**
         * Metodo para añadir el ApiKey de forma programada
         * @param apiKey
         */
        public HooptapAWS.Builder setApiKey(String apiKey) {
            editor.putString("ht_api_key", apiKey);
            editor.apply();
            return this;
        }

        /**
         * Constructor que crea el objeto con los parametros asignados por los metodos anteriores
         * @return El objeto Hooptap
         */
        public HooptapAWS build() {
            if (sClientService == null) {

                client = new OkHttpClient();
                //client.interceptors().add(new HooptapInterceptor(context));
                client.setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

                /*try {
                    ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    Bundle bundle = ai.metaData;
                    editor.putString("ht_api_key", bundle.getString("com.hooptap.Apikey"));
                    editor.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }*/


                if (htEnableDebug != null) {
                    if (htEnableDebug) {

                        debugVariable = RestAdapter.LogLevel.FULL;
                    } else {
                        debugVariable = RestAdapter.LogLevel.NONE;
                    }
                }else{
                    debugVariable = RestAdapter.LogLevel.NONE;
                }
                /*Gson gson = new GsonBuilder()
                        .registerTypeAdapter(HooptapItem.class, new ConverterJSON<HooptapItem>())
                        .registerTypeAdapter(HooptapItem.class, new ConverterJSON<ArrayList<HooptapItem>>())
                        .registerTypeAdapter(HooptapBadge.class, new ConverterJSON<HooptapBadge>())
                        .registerTypeAdapter(HooptapFolder.class, new ConverterJSON<HooptapFolder>())
                        .registerTypeAdapter(HooptapFriend.class, new ConverterJSON<HooptapFriend>())
                        .registerTypeAdapter(HooptapGame.class, new ConverterJSON<HooptapGame>())
                        .registerTypeAdapter(HooptapGameStatus.class, new ConverterJSON<HooptapGameStatus>())
                        .registerTypeAdapter(HooptapGood.class, new ConverterJSON<HooptapGood>())
                        .registerTypeAdapter(HooptapLevel.class, new ConverterJSON<HooptapLevel>())
                        .registerTypeAdapter(HooptapLink.class, new ConverterJSON<HooptapLink>())
                        .registerTypeAdapter(HooptapPoint.class, new ConverterJSON<HooptapPoint>())
                        .registerTypeAdapter(HooptapQuest.class, new ConverterJSON<HooptapQuest>())
                        .registerTypeAdapter(HooptapQuestStep.class, new ConverterJSON<HooptapQuestStep>())
                        .registerTypeAdapter(HooptapQuestUserStatus.class, new ConverterJSON<HooptapQuestUserStatus>())
                        .registerTypeAdapter(HooptapReward.class, new ConverterJSON<HooptapReward>())
                        .registerTypeAdapter(HooptapText.class, new ConverterJSON<HooptapText>())
                        .registerTypeAdapter(HooptapWeb.class, new ConverterJSON<HooptapWeb>())

                        .create();*/
                Signer signer=getSigner("https://25unt9h64h.execute-api.us-west-2.amazonaws.com/dev");
                String method = "POST";
                String service = "execute-api";
                String host = "afr4ap60wc.execute-api.eu-west-1.amazonaws.com";
                String region = "eu-west-1";
                String endpoint = "https://afr4ap60wc.execute-api.eu-west-1.amazonaws.com/deva";
                String content_type = "application/json";
                String canonical_url="/deva";

                String request_parameters =  "<root \"attr\"=\"bar\">";
                request_parameters +=  "foo";
                request_parameters +=  "</root>";

                String amz_date=new Time().format2445();
                String canonical_headers = "content-type:" + content_type + "\n" + "host:" + host + "\n" + "x-amz-date:" + amz_date + "\n" + "x-api-key:" + "TUl6mx5GOZ4k3qwHArymg9c41n8e3J9X6VccvwQR" + "\n";
                String signed_headers = "content-type;host;x-amz-date;x-api-key";
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA-256");
                    md.update(request_parameters.getBytes("UTF-8")); // Change this to "UTF-16" if needed
                    byte[]  payload_hash  = md.digest();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String text = "This is some text";

                String algorithm = "AWS4-HMAC-SHA256";

                ////////////////////////////////////////
                try {
                    RequestPayload=
                    CanonicalRequest = HTTPRequestMethod + "\n" + CanonicalURI + "\n" + CanonicalQueryString + "\n" + CanonicalHeaders + '\n' + SignedHeaders + '\n' + getDigest("SHA-256",RequestPayload,true);
                } catch (SignatureException e) {
                    e.printStackTrace();
                }

                restAdapter = new RestAdapter.Builder()
                        .setRequestInterceptor(new RequestInterceptor() {
                            @Override
                            public void intercept(RequestFacade request) {
                                //request.addHeader("access_token", settings.getString("ht_token", ""));
                                request.addHeader("disp_platform", "android");
                                request.addHeader("authorization", "Bearer " + settings.getString("ht_token", ""));
                                request.addHeader("x-api-key", settings.getString("ht_api_key", ""));
                            }
                        })
                                //.setConverter(new GsonConverter(gson))
                        .setLogLevel(debugVariable)
                        .setEndpoint("https:/")
                        .setClient(new OkClient(client))
                        .build();

                sClientService = restAdapter.create(ApiInterfaceAWS.class);
            }

            return new HooptapAWS(sClientService);
        }
    }
    static Signer getSigner(String region) {
        AWS4Signer signer = new AWS4Signer();
        signer.setServiceName("execute-api");
        signer.setRegionName(region);
        return signer;
    }
    static byte[] HmacSHA256(String data, byte[] key) throws Exception  {
        String algorithm="HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data.getBytes("UTF8"));
    }

    static byte[] getSignatureKey(String key, String dateStamp, String regionName, String serviceName) throws Exception  {
        byte[] kSecret = ("AWS4" + key).getBytes("UTF8");
        byte[] kDate    = HmacSHA256(dateStamp, kSecret);
        byte[] kRegion  = HmacSHA256(regionName, kDate);
        byte[] kService = HmacSHA256(serviceName, kRegion);
        byte[] kSigning = HmacSHA256("aws4_request", kService);
        return kSigning;
    }
    private static String getDigest(String algorithm, String data, boolean toLower)
            throws SignatureException {
        try {
            MessageDigest mac = MessageDigest.getInstance(algorithm);
            mac.update(data.getBytes("UTF-8"));
            return toLower ?
                    new String(toHex(mac.digest())).toLowerCase() : new String(toHex(mac.digest()));
        } catch (Exception e) {
            throw new SignatureException(e);
        }

    }
}