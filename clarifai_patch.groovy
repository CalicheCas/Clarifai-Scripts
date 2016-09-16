/**
 * Created by carlo on 8/13/16.
 */
import static groovyx.net.http.ContentType.JSON
import com.apipulse.afconnector.AFConnectorWrapper
import groovy.json.JsonOutput
import groovyx.net.http.*
import static groovyx.net.http.Method.PATCH

public class ClarifaiPatch extends AFConnectorWrapper{

    private static POST_URL = 'https://api2-prod.clarifai.com/v2/inputs/'//{id}
    public void run(){

        final String accessToken  = params['accessToken']
        final String id = params['id']

        def fullUrl = "https://api2-prod.clarifai.com/v2/inputs/$id"

        def payload = [
                'input':[
                        'id':"",
                            'data':[
                                    'tags':[
                                            ['concept':['id':'robot'],'value':true]
                                ]
                        ]
                ]
        ]
        payload = JsonOutput.toJson(payload)
        println payload

        final HTTPBuilder httpp = new HTTPBuilder(fullUrl)
        httpp.headers['Authorization'] = "Bearer $accessToken "
        httpp.headers.'Content-Type' = 'application/json'
        httpp.request(PATCH,JSON) {
            body = payload
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