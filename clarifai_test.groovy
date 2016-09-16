/**
 * Created by carlo on 8/13/16.
 */
import static groovyx.net.http.ContentType.JSON
import com.apipulse.afconnector.AFConnectorWrapper
import groovy.json.JsonOutput
import groovyx.net.http.*
import static groovyx.net.http.Method.POST

public class ClarifaiTestModel extends AFConnectorWrapper{

    private static POST_URL = 'https://api2-prod.clarifai.com/v2/models/'//{id}/outputs
    public void run(){

        final String accessToken  = params['accessToken']
        final String modelId      = params['modelId']
        final String pic       = params['pic']
        def result = '?'

        def fullUrl = "https://api2-prod.clarifai.com/v2/models/$modelId/outputs"

        def payload = [
                'inputs':[
                   [
                    'data':[
                            'image':['url': "$pic"]
                    ]
                ]
            ]

        ]
        payload = JsonOutput.toJson(payload)
        println payload

        final HTTPBuilder httpp = new HTTPBuilder(fullUrl)
        httpp.headers['Authorization'] = "Bearer $accessToken "
        httpp.headers.'Content-Type' = 'application/json'
        httpp.request(POST,JSON) {
            body = payload
            response.success = { resp, reader ->
                println "Success! ${resp.status}"
                System.out << reader
                //result = reader['value']
                //println "this is the result $result"


            }
            response.failure = { resp, reader ->
                println "Request failed with status ${resp.status}"
                System.out << reader
            }
        }
    }

}