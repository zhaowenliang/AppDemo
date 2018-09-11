package cc.buddies.app.appdemo.retrofit;

import java.util.Collections;

import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import retrofit2.Retrofit;

public class AppRetrofit {

    private static Retrofit retrofit = null;

    public static Retrofit retrofit() {
        if (retrofit == null) {
            // Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS)
            ConnectionSpec connectionSpec = getConnectionSpec();


            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new LoggingInterceptor())
                    .connectionSpecs(Collections.singletonList(connectionSpec))
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(ServerConfig.BASE_URL)
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }

    private static ConnectionSpec getConnectionSpec() {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();
        return spec;
    }

}
