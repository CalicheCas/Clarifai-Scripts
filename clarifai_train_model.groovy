/**
 * Created by carlo on 8/13/16.
 */
import com.apipulse.afconnector.AFConnectorWrapper
import groovyx.net.http.*
import static groovyx.net.http.Method.POST

public class ClarifaiTrainModel extends AFConnectorWrapper{

    private static POST_URL = 'https://api2-prod.clarifai.com/v2/models/'//{id}/versions'
    public void run(){

        final String accessToken  = params['accessToken']
        final String modelId      = params['modelId']
        def fullUrl = "https://api2-prod.clarifai.com/v2/models/$modelId/versions"

        final HTTPBuilder httpp = new HTTPBuilder(fullUrl)
        httpp.headers['Authorization'] = "Bearer $accessToken "
        httpp.headers.'Content-Type' = 'application/json'
        httpp.request(POST) {
            response.success = { resp, reader ->
                println "Success! ${resp.status}"
                System.out << reader

            }
            response.failure = { resp, reader ->
                println "Request failed with status ${resp.status}"
                System.out << reader
            }
        }
    }

}