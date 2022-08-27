package reverb.rdscrudservice.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.DecryptionFailureException;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.google.gson.Gson;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reverb.rdscrudservice.Modal.AWSSecrets;

import javax.sql.DataSource;

@Configuration
public class ApplicationConfig {
    private Gson gson =new Gson();
    @Bean
    public DataSource dataSource(){
        AWSSecrets secrets=getSecret();
        return DataSourceBuilder
                .create()
                .driverClassName("org.postgresql.Driver")
                .url("jdbc:"+secrets.getEngine()+"ql://"+secrets.getHost()+":"+secrets.getPort()+"/reverb")
                .username(secrets.getUsername())
                .password(secrets.getPassword())
                .build();

    }
    private AWSSecrets getSecret() {

        String secretName = "reverb-db-credentials";
        String region = "ap-south-1";

        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withRegion(region)
                .build();
        String secret;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }
        if (getSecretValueResult.getSecretString() != null) {

            secret = getSecretValueResult.getSecretString();
            return gson.fromJson(secret,AWSSecrets.class);
        }
        return null;
    }
}
